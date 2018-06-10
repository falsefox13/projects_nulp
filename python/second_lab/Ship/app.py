from flask import Flask, jsonify, abort, make_response, request
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)

app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:011111@localhost:3306/sakila'
db = SQLAlchemy(app)


class SeaTransportaion(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    cargo = db.Column(db.Integer)
    duration_in_days = db.Column(db.Integer)
    actual_price = db.Column(db.Integer)


@app.route('/transportations', methods=['GET'])
def get_all_transportations():
    transportations = []
    all_transportations = SeaTransportaion.query.all()
    for tr in all_transportations:
        transp = {
            'cargo': tr.cargo,
            'duration_in_days': tr.duration_in_days,
            'actual_price': tr.actual_price
        }
        transportations.append(transp)
    db.session.commit()
    return jsonify({'transportations': transportations})


@app.route('/transportations/<int:transp_id>', methods=['GET'])
def get_transp(transp_id):
    tr = SeaTransportaion.query.filter_by(id=transp_id).first()
    transp = {
        'cargo': tr.cargo,
        'duration_in_days': tr.duration_in_days,
        'actual_price': tr.actual_price
    }
    db.session.commit()
    return jsonify({'transportation': transp})


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


@app.route('/transportations', methods=['POST'])
def add_transp():
    if not request.json or not 'cargo' in request.json:
        abort(400)
    new_transp = SeaTransportaion()
    print(request.json)
    new_transp.id = request.json['id']
    new_transp.cargo = request.json.get('cargo', 0)
    new_transp.duration_in_days = request.json.get('duration_in_days', 0)
    new_transp.actual_price = request.json.get('actual_price', 0)

    db.session.add(new_transp)
    db.session.commit()
    return jsonify('Successful')


@app.route('/transportations/<int:transp_id>', methods=['PUT'])
def update_transp(transp_id):
    transp = SeaTransportaion.query.get(transp_id)

    transp.cargo = request.json.get('cargo', transp.cargo)
    transp.duration_in_days = request.json.get('duration_in_days', transp.duration_in_days)
    transp.actual_price = request.json.get('actual_price', transp.actual_price)
    db.session.commit()
    return jsonify('Successful')


@app.route('/transportations/<int:transp_id>', methods=['DELETE'])
def delete_transp(transp_id):
    tr = SeaTransportaion.query.filter_by(id=transp_id).first()
    db.session.delete(tr)
    db.session.commit()
    return jsonify({'result': True})


if __name__ == '__main__':
    app.run(debug=True)