from flask import jsonify, render_template, request, abort, Blueprint, redirect, url_for, send_from_directory
import api
from models import Sensor

base_bp = Blueprint('base', __name__)

sensor_object = api.SensorModelView()


@base_bp.route("/", methods=["GET"])
def redirect():
    return render_template("sensor/get.html")


@base_bp.route("/sensor", methods=["GET"])
def redirect_to():
    return render_template("sensor/get.html")


@base_bp.route("/sensor/create", methods=["GET", "POST"])
def create():
    if request.method == "GET":
        return render_template('create.html')
    elif request.method == "POST":
        Sensor.load_from_dict(request.values)
        return redirect()
    else:
        return abort(404)


@base_bp.route("/sensor/edit/<obj_id>", methods=["GET", "PUT"])
def sensor_edit(obj_id):
    if request.method == "GET":
        return render_template('edit.html', obj=sensor_object.retrieve(obj_id))
    elif request.method == "PUT":
        sensor_object.update(obj_id, request)
        return redirect()
    else:
        return abort(404)


@base_bp.route("/sensor/delete/<obj_id>", methods=["DELETE"])
def sensor_delete(obj_id):
    return sensor_object.delete(obj_id)
