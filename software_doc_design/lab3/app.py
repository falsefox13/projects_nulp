from db_conf import session, conn_str
from flask import Flask, jsonify
from routes import base_bp

from models import Sensor, Location, ConnectionType

app = Flask(__name__)
app.config["SQLALCHEMY_DATABASE_URI"] = conn_str
app.register_blueprint(base_bp)


@app.route("/sensors", methods=["GET"])
def get_sensors():
    sensors = session.query(Sensor).all()
    sensors_transformed = []
    for s in sensors:
        conn_type = session.query(ConnectionType).filter_by(id=s.connection_type_id).first()
        location = session.query(Location).filter_by(id=s.location_id).first()

        sensor = {
            'id': s.id,
            'serial_number': s.serial_number,
            'connection_type': conn_type.connection_type,
            'country': location.country,
            'city': location.city,
            'address': location.address,
            'room': location.room_name
        }
        sensors_transformed.append(sensor)
    return jsonify({'data': sensors_transformed})


if __name__ == "__main__":
    app.run(debug=True)
