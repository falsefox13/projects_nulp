import random

from sqlalchemy import Column, String, Integer, ForeignKey, DateTime
from sqlalchemy.orm import relationship

from db_conf import session, Base


def check_required_fields_in_dict(fields, dictionary):
    error = []
    for field in fields:
        if field not in dictionary:
            error.append("Field: {field} not in input data".format(field=field))
        else:
            continue

    if len(error) <= 0:
        return True, []
    else:
        return False, error


class Password(Base):
    __tablename__ = 'Password'
    id = Column(Integer, primary_key=True)
    password = Column(String)

    def __repr__(self):
        return "<Password: {}>".format(self.password)


class ConnectionType(Base):
    __tablename__ = 'ConnectionType'

    id = Column(Integer, primary_key=True)
    connection_type = Column(String)

    def __repr__(self):
        return "<Connection type: {}>".format(self.connection_type)


class User(Base):
    __tablename__ = 'User'
    id = Column(Integer, primary_key=True)
    name = Column(String)
    password_id = Column(Integer, ForeignKey(Password.id))
    password = relationship("Password")

    def __repr__(self):
        return "<Name: {}>".format(self.name)


class Hub(Base):
    __tablename__ = 'Hub'

    id = Column(Integer, primary_key=True)
    serial_number = Column(String)
    connection_type_id = Column(Integer, ForeignKey(ConnectionType.id))
    connection_type = relationship(ConnectionType)
    current_connected_devices = Column(Integer)

    def __repr__(self):
        return "<Hub serial: {}, current connected devices: {}>".format(self.serial_number,
                                                                        self.current_connected_devices)


class Scenario(Base):
    __tablename__ = 'Scenario'

    id = Column(Integer, primary_key=True)
    name = Column(String)
    description = Column(String)
    created_by = Column(Integer, ForeignKey(User.id))
    user = relationship(User)
    initiated_by = Column(Integer, ForeignKey(Hub.id))
    hub = relationship(Hub)

    def __init__(self, id, name, description, created_by, initiated_by):
        self.id = id
        self.name = name
        self.description = description
        self.created_by = created_by
        self.initiated_by = initiated_by

    def __repr__(self):
        return "<Scenario name: {}; description: {}>".format(self.name, self.description)


class Location(Base):
    __tablename__ = 'Location'

    id = Column(Integer, primary_key=True)
    room_name = Column(String)
    city = Column(String)
    country = Column(String)
    address = Column(String)

    def __repr__(self):
        return "<Country: {}; city: {}; address: {}; room_name: {}>".format(self.country, self.city,
                                                                            self.address, self.room_name)


class Sensor(Base):
    __tablename__ = 'Sensor'

    id = Column(Integer, primary_key=True)
    serial_number = Column(String)
    connection_type_id = Column(Integer, ForeignKey(ConnectionType.id))
    connection_type = relationship(ConnectionType)
    hub_id = Column(Integer, ForeignKey(Hub.id))
    hub = relationship(Hub)
    location_id = Column(Integer, ForeignKey(Location.id))
    location = relationship(Location)

    @staticmethod
    def required_fields_list():
        return ["serial_number", "connection_type", "country", "city", "address", "room"]

    @staticmethod
    def load_from_dict(data):
        check, errors = check_required_fields_in_dict(Sensor.required_fields_list(), data)

        if check:
            conn = ConnectionType(connection_type=data["connection_type"])
            session.add(conn)
            loc = Location(country=data["country"], city=data["city"], address=data["address"],
                           room_name=data["room"])
            session.add(loc)
            session.commit()
            hub_id = random.randint(1, 200)
            session.add(Sensor(serial_number=data["serial_number"], connection_type_id=conn.id, hub_id=hub_id,
                               location_id=loc.id))
            session.commit()
        else:
            raise Exception(errors)

    def edit(self, info):
        self.serial_number = info["serial_number"]
        loc = session.query(Location).get(self.location_id)
        loc.country = info["country"]
        loc.city = info["city"]
        loc.address = info["address"]
        loc.room_name = info["room"]
        conn = session.query(ConnectionType).get(self.connection_type_id)
        conn.connection_type = info["connection_type"]
        session.commit()

    def remove(self):
        messages = session.query(Message).filter_by(source=self.id).all()
        for message in messages:
            session.delete(message)
            session.commit()
        session.delete(self)
        session.commit()

    def serialize(self, includes=[]):
        loc = session.query(Location).get(self.location_id)
        conn = session.query(ConnectionType).get(self.connection_type_id)

        return {
            'id': self.id,
            'serial_number': self.serial_number,
            'country':  loc.country,
            'city': loc.city,
            'address': loc.address,
            'room_name': loc.room_name,
            'connection_type': conn.connection_type
        }


class Message(Base):
    __tablename__ = 'Message'

    id = Column(Integer, primary_key=True)
    type = Column(String)
    timestamp = Column(DateTime)
    message = Column(String)
    source = Column(Integer, ForeignKey(Sensor.id))
    sensor = relationship(Sensor)

    def __repr__(self):
        return "<Type: {}; message: {}>".format(self.type, self.message)
