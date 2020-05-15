import pandas as pd
from config import config_reader
from data_states import DataInsertionStates as States
from sodapy import Socrata

from base import BaseStrategy
import base64
import datetime
import json

from google.api_core import retry
import jwt
import requests

NUMBER_OF_MESSAGES = int(config_reader.cfg.get('LAB', 'number_of_messages'))
ENCODING = config_reader.cfg.get('LAB', 'encoding')
MESSAGES_PER_FETCH = int(config_reader.cfg.get('LAB', 'messages_per_fetch'))

_BASE_URL = 'https://cloudiotdevice.googleapis.com/v1'
_BACKOFF_DURATION = 60


def create_jwt(project_id, private_key_file, algorithm):
    token = {
        'iat': datetime.datetime.utcnow(),
        'exp': datetime.datetime.utcnow() + datetime.timedelta(minutes=60),
        'aud': project_id
    }
    with open(private_key_file, 'r') as f:
        private_key = f.read()
    print('Creating JWT using {} from private key file {}'.format(algorithm, private_key_file))
    return jwt.encode(token, private_key, algorithm=algorithm).decode('ascii')


@retry.Retry(predicate=retry.if_exception_type(AssertionError), deadline=_BACKOFF_DURATION)
def publish_message(message, message_type, base_url, project_id, cloud_region, registry_id,
                    device_id, jwt_token):
    headers = {
        'authorization': 'Bearer {}'.format(jwt_token),
        'content-type': 'application/json',
        'cache-control': 'no-cache'
    }
    url_suffix = 'publishEvent' if message_type == 'event' else 'setState'
    publish_url = (
        '{}/projects/{}/locations/{}/registries/{}/devices/{}:{}').format(
        base_url, project_id, cloud_region, registry_id, device_id,
        url_suffix)
    body = None
    msg_bytes = base64.urlsafe_b64encode(message.encode('utf-8'))
    if message_type == 'event':
        body = {'binary_data': msg_bytes.decode('ascii')}
    else:
        body = {
            'state': {'binary_data': msg_bytes.decode('ascii')}
        }
    resp = requests.post(
        publish_url, data=json.dumps(body), headers=headers)
    if resp.status_code != 200:
        print('Response came back {}, retrying'.format(resp.status_code))
        raise AssertionError('Not OK response: {}'.format(resp.status_code))

    return resp


@retry.Retry(predicate=retry.if_exception_type(AssertionError), deadline=_BACKOFF_DURATION)
def get_config(version, message_type, base_url, project_id, cloud_region, registry_id, device_id, jwt_token):
    headers = {
        'authorization': 'Bearer {}'.format(jwt_token),
        'content-type': 'application/json',
        'cache-control': 'no-cache'
    }
    basepath = '{}/projects/{}/locations/{}/registries/{}/devices/{}/'
    template = basepath + 'config?local_version={}'
    config_url = template.format(base_url, project_id, cloud_region, registry_id, device_id, version)
    resp = requests.get(config_url, headers=headers)
    if resp.status_code != 200:
        print('Error getting config: {}, retrying'.format(resp.status_code))
        raise AssertionError('Not OK response: {}'.format(resp.status_code))
    return resp


class PubSubStrategy(BaseStrategy):
    def __init__(self, url, filename):
        super(PubSubStrategy, self).__init__(url, filename)

    def execute(self):
        jwt_token = create_jwt(config_reader.cfg.get('LAB', 'project_id'),
                               config_reader.cfg.get('LAB', 'private_key_file'),
                               config_reader.cfg.get('LAB', 'algorithm'))
        jwt_iat = datetime.datetime.utcnow()
        jwt_exp_mins = config_reader.cfg.get('LAB', 'jwt_expires_minutes')

        client = Socrata(self.dataset_url, None)

        seconds_since_issue = (datetime.datetime.utcnow() - jwt_iat).seconds
        if seconds_since_issue > 60 * jwt_exp_mins:
            print 'Refreshing token after {}s'.format(seconds_since_issue)
            jwt_token = create_jwt(config_reader.cfg.get('LAB', 'project_id'),
                                   config_reader.cfg.get('LAB', 'private_key_file'),
                                   config_reader.cfg.get('LAB', 'algorithm'))
            jwt_iat = datetime.datetime.utcnow()
        for i in range(NUMBER_OF_MESSAGES):
            results = client.get(self.dataset_filename, limit=1, offset=i)
            results_df = pd.DataFrame.from_records(results)
            message = results_df.to_json(orient='records')
            resp = publish_message(message, config_reader.cfg.get('LAB', 'message_type'),
                                   config_reader.cfg.get('LAB', 'base_url'),
                                   config_reader.cfg.get('LAB', 'project_id'),
                                   config_reader.cfg.get('LAB', 'cloud_region'),
                                   config_reader.cfg.get('LAB', 'registry_id'),
                                   config_reader.cfg.get('LAB', 'device_id'),
                                   jwt_token)

            print('HTTP response: ', resp)
            print('Message sent: ', message)
