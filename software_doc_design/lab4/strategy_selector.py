from config import config_reader

from event_hub_strategy import EventHubStrategy
from terminal_strategy import TerminalStrategy


class StrategySelector(object):
    def __init__(self, url, filename):
        self.dataset_filename = filename.strip()
        self.dataset_url = url.strip()

        self.strategies = {
            'event_hub': EventHubStrategy(url=self.dataset_url, filename=self.dataset_filename),
            'terminal': TerminalStrategy(url=self.dataset_url, filename=self.dataset_filename)
        }

    def execute(self):
        strategy_name = config_reader.cfg.get('LAB', 'strategy_name')
        self.strategies[strategy_name].execute()
