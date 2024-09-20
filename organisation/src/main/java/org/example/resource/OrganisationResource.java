package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Organisation;
import org.example.service.OrganisationService;

import java.util.List;

@Path("/organisations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganisationResource {
    @Inject
    OrganisationService organisationService;


    @GET
    public List<Organisation> getAllOrganisations() {
        return organisationService.getAllOrganisations();
    }


    @GET
    @Path("/{id}")
    public Response getOrganisationById(@PathParam("id") Long id) {
        Organisation organisation = organisationService.getOrganisationById(id);
        if (organisation != null) {
            return Response.ok(organisation).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Transactional
    public Response createOrganisation(Organisation organisation) {
        Organisation createdOrganisation = organisationService.createOrganisation(organisation);
        return Response.status(Response.Status.CREATED).entity(createdOrganisation).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteOrganisation(@PathParam("id") Long id) {
        boolean deleted = organisationService.deleteOrganisation(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateOrganisation(@PathParam("id") Long id, Organisation organisation) {
        Organisation updatedOrganisation = organisationService.updateOrganisation(id, organisation);
        if (updatedOrganisation != null) {
            return Response.ok(updatedOrganisation).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
