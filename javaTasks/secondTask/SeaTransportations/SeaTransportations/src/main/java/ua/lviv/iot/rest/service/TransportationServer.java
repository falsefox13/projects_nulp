package ua.lviv.iot.rest.service;

import ua.lviv.iot.Manager.TransportationManager;
import ua.lviv.iot.Models.SeaTransportation;
import ua.lviv.iot.persistence.dao.SeaTransportationDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Path("/seeTransportations")
public class TransportationServer implements Serializable {

    @Inject
    private SeaTransportationDao dao;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SeaTransportation getTransportation(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransportation(SeaTransportation transportation) {
        dao.add(transportation);
        return Response.status(200).entity("Good").build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTransportation(@PathParam("id") Integer id) {
        dao.delete(id);
        return Response.status(200).entity("Good").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTransportation(SeaTransportation transportation) {
        dao.update(transportation);
        return Response.status(200).entity("Good").build();
    }


}

