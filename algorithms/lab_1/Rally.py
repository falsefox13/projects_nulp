class Rally:
    def __init__(self, country, duration, price):
        self.country = country
        self.duration = duration
        self.price = price

    def __str__(self):
        return "Country: " + self.country + ", " + \
               "duration: " + str(self.duration) + ", " + \
               "price: " + str(self.price)
