import pandas as pd
from config import config_reader
from data_states import DataInsertionStates as States
from sodapy import Socrata

from base import BaseStrategy

NUMBER_OF_MESSAGES = int(config_reader.cfg.get('LAB', 'number_of_messages'))
ENCODING = config_reader.cfg.get('LAB', 'encoding')
MESSAGES_PER_FETCH = int(config_reader.cfg.get('LAB', 'messages_per_fetch'))


class TerminalStrategy(BaseStrategy):
    def __init__(self, url, filename):
        super(TerminalStrategy, self).__init__(url, filename)

    def execute(self):
        dataset_id = '{}_{}'.format(self.dataset_url, self.dataset_filename)
        latest_status = self.redis_client.get(dataset_id)
        if latest_status is not None:
            latest_status = latest_status.decode(ENCODING)

        if latest_status == States.COMPLETED.value or latest_status == States.REFILL_ATTEMPT.value:
            self.redis_client.set(dataset_id, States.REFILL_ATTEMPT.value)
            print(States.REFILL_ATTEMPT.value)
            return False

        client = Socrata(self.dataset_url, None)
        self.redis_client.set(dataset_id, States.STARTED.value)

        for i in range(int(NUMBER_OF_MESSAGES / MESSAGES_PER_FETCH)):
            results = client.get(self.dataset_filename, limit=MESSAGES_PER_FETCH, offset=MESSAGES_PER_FETCH * i)

            results_df = pd.DataFrame.from_records(results)

            current_progress = '{} - {}'.format(str(i * MESSAGES_PER_FETCH + 1), str((i + 1) * MESSAGES_PER_FETCH))
            self.redis_client.set(dataset_id, current_progress)

            print('Progress {}'.format(current_progress))
            print(results_df)

        self.redis_client.set(self.dataset_url + "_" + self.dataset_filename, States.COMPLETED.value)
        print(States.COMPLETED.value)
