from TransportationManager import TransportationManager
from Port import Port
from Ship import Ship
from SeaTransportation import SeaTransportation

if __name__ == "__main__":
    sender = Port("Chornomorsk", 0.8, 0.5)
    dest = Port("Europort", 0.9, 0.07)

    tr_manager = TransportationManager()

    tr_manager.add_ship(Ship(10000, 4000, 30, 400))
    tr_manager.add_ship(Ship(10000, 4000, 20, 550))

    print("Selecting the cheapest proposition:")
    print(tr_manager.select_cheapest(sender, dest, 15, 50000))

    print("Selecting the fastest proposition:")
    print(tr_manager.select_fastest(sender, dest, 25))
    tr_manager.do_transportation(sender, dest, 25)

    example = (set("this", "is", "example"), {"1":"for", "2":"good", "3":"mark"})