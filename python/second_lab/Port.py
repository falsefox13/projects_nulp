class Port:
    name = "Odessa"
    longitude, latitude = 0, 0

    def __init__(self, name="Odessa", longitude=0, latitude=0):
        self.name = name
        self.longitude = longitude
        self.latitude = latitude

    def to_string(self):
        return "Name: " + self.name + ", longitude: " + str(self.longitude) + \
               ", latitude: " + str(self.latitude)
