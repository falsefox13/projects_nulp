import os
import re

from db_conf import Session, engine, Base
from models import Password, User, ConnectionType, Location, Sensor, Hub, Scenario, Message

# 2 - generate database schema
Base.metadata.create_all(engine)

# 3 - create a new session
session = Session()


def get_or_create(session, model, **kwargs):
    instance = session.query(model).filter_by(**kwargs).first()
    if instance:
        return instance
    else:
        instance = model(**kwargs)
        session.add(instance)
        session.commit()
        return instance


def read_csv_file():
    module_dir = os.path.dirname(__file__)
    file_path = os.path.join(module_dir, 'generated.csv')
    with open(file_path, 'r+') as file:
        buffer = file.read()
        first = re.split(r'Passwords\n', buffer)[1]
        sec = re.split(r'.*\nUsers\n', first)[0]
        passwords = sec.split('\n')
        for i in range(0, len(passwords)):
            if passwords[i] != '':
                get_or_create(session, Password, id=i+1, password=passwords[i])

    first = re.split(r'Users\n', buffer)[1]
    sec = re.split(r'.*\nConnection types\n', first)[0]
    users = sec.split('\n')
    for i in range(0, len(users)):
        values = users[i].split(',')
        if users[i] != '':
            get_or_create(session, User, id=i+1, name=values[0], password_id=values[1])

    first = re.split(r'Connection types\n', buffer)[1]
    sec = re.split(r'.*\nHubs\n', first)[0]
    conn_types = sec.split('\n')
    for i in range(0, len(conn_types)):
        if conn_types[i] != '':
            get_or_create(session, ConnectionType, id=i + 1, connection_type=conn_types[i])

    first = re.split(r'Hubs\n', buffer)[1]
    sec = re.split(r'.*\nScenarios\n', first)[0]
    hubs = sec.split('\n')
    for i in range(0, len(hubs)):
        values = hubs[i].split(',')
        if hubs[i] != '':
            get_or_create(session, Hub, id=i+1, serial_number=values[0], connection_type_id=values[1],
                          current_connected_devices=values[2])

    first = re.split(r'Scenarios\n', buffer)[1]
    sec = re.split(r'.*\nLocations\n', first)[0]
    scenarios = sec.split('\n')
    for i in range(0, len(scenarios)):
        values = scenarios[i].split(',')
        if scenarios[i] != '':
            get_or_create(session, Scenario, id=i+1, name=values[0], description=values[1],
                          created_by=values[3], initiated_by=values[2])

    first = re.split(r'Locations\n', buffer)[1]
    sec = re.split(r'.*\nSensors\n', first)[0]
    locations = sec.split('\n')
    for i in range(0, len(locations)):
        values = locations[i].split(',')
        if locations[i] != '':
            get_or_create(session, Location, id=i+1, room_name=values[0], address=values[1],
                          city=values[2], country=values[3])

    first = re.split(r'Sensors\n', buffer)[1]
    sec = re.split(r'.*\nMessages\n', first)[0]
    sensors = sec.split('\n')
    for i in range(0, len(sensors)):
        values = sensors[i].split(',')
        if sensors[i] != '':
            get_or_create(session, Sensor, id=i+1, serial_number=values[0], connection_type_id=values[1],
                          hub_id=values[2], location_id=values[3])

    first = re.split(r'Messages\n', buffer)[1]
    messages = first.split('\n')
    for i in range(0, len(messages)):
        values = messages[i].split(',')
        if messages[i] != '':
            get_or_create(session, Message, id=i+1, type=values[0], message=values[1],
                          timestamp=values[2], source=values[3])


if __name__ == "__main__":
    read_csv_file()
