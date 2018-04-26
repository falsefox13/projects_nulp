package ua.lviv.iot.persistence.dao;

import ua.lviv.iot.Models.SeaTransportation;
import ua.lviv.iot.persistence.dao.AbstractDao;
import ua.lviv.iot.persistence.dao.SeaTransportationDao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Named
@Dependent
public class SeaTransportationDaoImpl extends AbstractDao<SeaTransportation> implements SeaTransportationDao, Serializable {


    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Class<SeaTransportation> getEntityClass() {
        return SeaTransportation.class;
    }

    //@Resource
    //private UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        System.out.println("ready");
    }
}