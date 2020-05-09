from enum import Enum


class DataInsertionStates(Enum):
    REFILL_ATTEMPT = 'ATTEMPT TO REFILL'
    COMPLETED = 'COMPLETED'
    STARTED = 'STARTED'
