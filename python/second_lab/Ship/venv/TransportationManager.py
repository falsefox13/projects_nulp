from SeaTransportation import SeaTransportation


class TransportationManager:
    available_ships = []
    previous_transportations = []

    def __init__(self, ships=[], transportations=[]):
        self.available_ships = ships
        self.previous_transportations = transportations

    def add_ship(self, ship):
        self.available_ships.append(ship)

    def get_required_ship(self, cargo):
        for index, ship in enumerate(self.available_ships):
            if ship.capacity > cargo:
                return index

    def select_cheapest(self, sender, dest, cargo, max_price):
        possible = SeaTransportation(sender, dest, cargo, max_price)
        index_of_ship = self.get_required_ship(cargo)
        if index_of_ship == None:
            return "No available ships"

        if possible.calc_price() < max_price:
            possible.calc_time(self.available_ships[index_of_ship].speed)
            return "The cheapest: " + possible.to_string()
        else:
            return "There is no such cheap transportation"

    def select_fastest(self, sender, dest, cargo):
        possible = SeaTransportation(sender, dest, cargo)
        index_of_ship = self.get_required_ship(cargo)
        if index_of_ship == None:
            return "No available ships"

        fastest = max(ship.speed for ship in self.available_ships)
        possible.calc_time(fastest);
        return "The fastest: " + possible.to_string();

    def do_transportation(self, sender, dest, cargo):
        transp = SeaTransportation(sender, dest, cargo)
        self.previous_transportations.append(transp)
        self.available_ships.pop(self.get_required_ship(cargo))
