import math

from Port import Port


class SeaTransportation:
    max_price, cargo, duration_in_days, actual_price = 0, 0, 0, 0
    sender = Port()
    destination = Port()

    def __init__(self, sender=None, destination=None, cargo=0, max_price=0, actual_price=0):
        self.sender = sender
        self.destination = destination
        self.max_price = max_price
        self.cargo = cargo
        self.calc_time(300)
        self.calc_price()

    def calc_distance(self):
        diff_of_latitude = self.destination.latitude - self.sender.latitude
        diff_of_longitude = self.destination.longitude - self.destination.longitude
        average_latitude = (self.destination.latitude - self.sender.latitude) / 2
        CONST_RADIUS_OF_EARTH = 6371.009
        distance = CONST_RADIUS_OF_EARTH * (math.sqrt(math.pow(diff_of_latitude, 2) +
                                                      math.pow((math.acos(average_latitude) * diff_of_longitude), 2)))
        return math.floor(distance)

    def calc_price(self):
        if self.cargo <= 20:
            price_of_container = 1000
        elif self.cargo > 20 & self.cargo < 40:
            price_of_container = 1500
        else:
            price_of_container = 50 * self.cargo
        self.actual_price = price_of_container + self.calc_distance()
        return math.floor(self.actual_price)

    def calc_time(self, average_speed):
        self.duration_in_days = math.floor(self.calc_distance() / average_speed)
        return self.duration_in_days

    def to_string(self):
        return "Transportation from " + self.sender.name + \
               " to " + self.destination.name + \
               " with cargo " + str(self.cargo) + \
               " tons \nwill cost " + str(self.actual_price) + \
               "$ and last for " + str(self.duration_in_days) + " days"
