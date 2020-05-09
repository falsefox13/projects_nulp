import abc
import redis
from config import config_reader


class BaseStrategy:
    __metaclass__ = abc.ABCMeta

    def __init__(self, url=None, filename=None):
        self.dataset_url = url
        self.dataset_filename = filename

        self.redis_client = redis.StrictRedis(host=config_reader.cfg.get('LAB', 'redis_host'), port=6380,
                                              password=config_reader.cfg.get('LAB', 'redis_passwd'), ssl=True)

    @abc.abstractmethod
    def execute(self):
        pass
