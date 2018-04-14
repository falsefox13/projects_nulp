class Ship:
    name = "Titanic"
    length, width, capacity, speed = 0, 0, 0, 0.0
    total_capacity = 0

    def __init__(self, name="no name", length=0, width=0, capacity=0, speed=0.0):
        self.name = name
        self.length = length
        self.width = width
        self.capacity = capacity
        self.speed = speed
        Ship.total_capacity += self.capacity

    def to_string(self):
        print("Name: " + self.name + ", length: " + str(self.length)
              + ", width: " + str(self.width) + ", capacity: "
              + str(self.capacity) + ", speed: " + str(self.speed))

    def print_sum(self):
        print("A ship called " + self.name + " has capacity " + str(self.capacity))

    @staticmethod
    def print_static_sum():
        print("Total capacity of all ships = " + str(Ship.total_capacity))


if __name__ == "__main__":
    titanic = Ship()
    bismark = Ship("Bismark", 251, 36, 50, 55.56)
    santa_maria = Ship("Santa Maria", 150, 25, 30)
    titanic.to_string()
    bismark.to_string()
    santa_maria.to_string()
    Ship.print_static_sum()
    bismark.print_sum()
    santa_maria.print_sum()
