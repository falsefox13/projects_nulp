class Ship:
    capacity, speed, length, width = 0, 0, 0, 0

    def __init__(self, length=0, width=0, capacity=0, speed=0):
        self.length = length
        self.width = width
        self.capacity = capacity
        self.speed = speed

    def to_string(self):
        return "Length: " + str(self.length) + \
               ", width: " + str(self.width) + ", capacity: " + \
               str(self.capacity) + ", speed: " + str(self.speed)
