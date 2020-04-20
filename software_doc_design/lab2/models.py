from sqlalchemy import Column, String, Integer, ForeignKey, DateTime
from sqlalchemy.orm import relationship

from db_conf import Base


class Password(Base):
    __tablename__ = 'Password'

    id = Column(Integer, primary_key=True)
    password = Column(String)

    def __init__(self, id, password):
        self.id = id
        self.password = password


class ConnectionType(Base):
    __tablename__ = 'ConnectionType'

    id = Column(Integer, primary_key=True)
    connection_type = Column(String)

    def __init__(self, id, connection_type):
        self.id = id
        self.connection_type = connection_type


class User(Base):
    __tablename__ = 'User'

    id = Column(Integer, primary_key=True)
    name = Column(String)
    password_id = Column(Integer, ForeignKey(Password.id))
    password = relationship("Password")

    def __init__(self, id, name, password_id):
        self.id = id
        self.name = name
        self.password_id = password_id


class Hub(Base):
    __tablename__ = 'Hub'

    id = Column(Integer, primary_key=True)
    serial_number = Column(String)
    connection_type_id = Column(Integer, ForeignKey(ConnectionType.id))
    connection_type = relationship(ConnectionType)
    current_connected_devices = Column(Integer)

    def __init__(self, id, serial_number, connection_type_id, current_connected_devices):
        self.id = id
        self.serial_number = serial_number
        self.connection_type_id = connection_type_id
        self.current_connected_devices = current_connected_devices


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


class Location(Base):
    __tablename__ = 'Location'

    id = Column(Integer, primary_key=True)
    room_name = Column(String)
    city = Column(String)
    country = Column(String)
    address = Column(String)

    def __init__(self, id, room_name, address, city, country):
        self.id = id
        self.room_name = room_name
        self.city = city
        self.address = address
        self.country = country


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

    def __init__(self, id, serial_number, connection_type_id, hub_id, location_id):
        self.id = id
        self.serial_number = serial_number
        self.connection_type_id = connection_type_id
        self.hub_id = hub_id
        self.location_id = location_id


class Message(Base):
    __tablename__ = 'Message'

    id = Column(Integer, primary_key=True)
    type = Column(String)
    timestamp = Column(DateTime)
    message = Column(String)
    source = Column(Integer, ForeignKey(Sensor.id))
    sensor = relationship(Sensor)

    def __init__(self, id, type, timestamp, message, source):
        self.id = id
        self.type = type
        self.timestamp = timestamp
        self.message = message
        self.source = source
