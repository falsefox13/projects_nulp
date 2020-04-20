from faker import Faker
import random


class FileGenerator:
    file_name = 'generated.csv'

    connection_types = ['Wired', 'Wireless', 'Radio', 'GSM', 'LoraWAN']

    cities = ["Lviv", "Kyiv", "Rivne", "New York", "Paris", "London", "Berlin"]
    countries = ["Ukraine", "France", "Germany", "USA"]
    address = ["First Str. 4/333", "Second Str. 3/123", "Third Str. 5/33"]
    rooms = ["kitchen", "bathroom", "living room", "garage", "bedroom", "balcony"]

    message_types = ['Notification', 'Status', 'Alarm']
    messages = ["Broken window in the kitchen", "Temperature is 21 celsius", "Kate is at home",
                "Water flood is detected", "Movement detected"]

    scenario_names = ['Vacation', 'Burglary', 'Daily', 'Monthly', 'Urgency']
    scenario_descriptions = ['Fire alarms and light all lamps', 'Launch music', 'Simulate home presence',
                             'Call the police', 'Clean the house', 'Open windows']

    user_names = ['Anton', 'Dima', 'Vova', 'Bogdan', 'Bob', 'Jek', 'John']
    user_passwords = ['12345', '11111111', 'IlovePizza', '0000000', 'password', 'HelloThere']

    records_count = 200

    def populate_passwords(self, file):
        file.write('\nPasswords\n')
        for i in range(1, self.records_count + 1):
            user_password = self.user_passwords[random.randint(0, len(self.user_passwords) - 1)]
            file.write('{password}\n'.format(password=user_password))

    def populate_users(self, file):
        file.write('\nUsers\n')
        for i in range(1, self.records_count + 1):
            user_name = self.user_names[random.randint(0, len(self.user_names) - 1)]
            file.write('{name},{password}\n'.format(name=user_name, password=i))

    def populate_conn_types(self, file):
        file.write('\nConnection types\n')
        for i in range(1, len(self.connection_types) + 1):
            connect_type = self.connection_types[random.randint(0, len(self.connection_types) - 1)]
            file.write('{connect_type}\n'.format(connect_type=connect_type))

    def populate_hubs(self, file):
        file.write('\nHubs\n')
        for i in range(1, self.records_count + 1):
            serial_number = random.randint(100000000, 999999999)
            connect_type = random.randint(1, len(self.connection_types))
            current_connected_devices = random.randint(1, self.records_count)
            file.write('{serial_number},{connect_type},{connected_devices}\n'.
                       format(serial_number=serial_number,
                              connect_type=connect_type,
                              connected_devices=current_connected_devices))

    def populate_scenarios(self, file):
        file.write('\nScenarios\n')
        for i in range(1, self.records_count + 1):
            scenario_name = self.scenario_names[random.randint(0, len(self.scenario_names) - 1)]
            description = self.scenario_descriptions[random.randint(0, len(self.scenario_descriptions) - 1)]
            hub_id = random.randint(1, self.records_count)
            user_id = random.randint(1, self.records_count)
            file.write('{name},{description},{hub_id},{user_id}\n'.
                       format(name=scenario_name,
                              description=description,
                              hub_id=hub_id, user_id=user_id))

    def populate_locations(self, file):
        file.write('\nLocations\n')
        for i in range(1, self.records_count + 1):
            room_name = self.rooms[random.randint(0, len(self.rooms) - 1)]
            address = self.address[random.randint(0, len(self.address) - 1)]
            city = self.cities[random.randint(0, len(self.cities) - 1)]
            country = self.countries[random.randint(0, len(self.countries) - 1)]
            file.write('{room_name},{address},{city},{country}\n'.format(room_name=room_name,
                                                                         address=address,
                                                                         city=city,
                                                                         country=country))

    def populate_sensors(self, file):
        file.write('\nSensors\n')
        for i in range(1, self.records_count + 1):
            serial_number = random.randint(100000000, 999999999)
            connection_type_id = random.randint(1, len(self.connection_types))
            hub_id = random.randint(1, self.records_count)
            file.write(
                '{serial_number},{connection_type_id},{hub_id},{location_id}\n'.format(serial_number=serial_number,
                                                                                       connection_type_id=connection_type_id,
                                                                                       hub_id=hub_id,
                                                                                       location_id=i))

    def populate_messages(self, file):
        file.write('\nMessages\n')
        for i in range(1, self.records_count + 1):
            type = self.message_types[random.randint(0, len(self.message_types) - 1)]
            fake = Faker()
            timestamp = fake.date_between(start_date='today', end_date='+30y')
            message = self.messages[random.randint(0, len(self.messages) - 1)]
            sensor_id = random.randint(1, self.records_count)
            file.write('{type},{description},{timestamp},{sensor_id}\n'.
                       format(type=type,
                              description=message,
                              timestamp=timestamp,
                              sensor_id=sensor_id))

    def generate_csv(self):
        with open(self.file_name, 'w') as file:
            self.populate_passwords(file)
            self.populate_users(file)
            self.populate_conn_types(file)
            self.populate_hubs(file)
            self.populate_scenarios(file)
            self.populate_locations(file)
            self.populate_sensors(file)
            self.populate_messages(file)


if __name__ == '__main__':
    try:
        csv_generator = FileGenerator()
        csv_generator.generate_csv()
        print("File successfully generated")
    except:
        print("Error")
