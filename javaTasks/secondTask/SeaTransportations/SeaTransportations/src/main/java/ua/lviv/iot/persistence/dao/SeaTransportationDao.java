package ua.lviv.iot.persistence.dao;


import ua.lviv.iot.Manager.TransportationManager;
import ua.lviv.iot.Models.SeaTransportation;

import javax.inject.Inject;

public interface SeaTransportationDao extends IDao<SeaTransportation> {
    @Inject
    TransportationManager manager = new TransportationManager();

}