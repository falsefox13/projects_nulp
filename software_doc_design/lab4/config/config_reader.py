import os

from configparser import ConfigParser

module_dir = os.path.abspath(os.path.dirname(__file__))
CONFIG_FILE = os.path.join("config/config.ini")

cfg = ConfigParser()
cfg.read(CONFIG_FILE)
