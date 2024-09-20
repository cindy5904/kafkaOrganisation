package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Departement;
import org.example.service.DepartementService;

import java.util.List;

@Path("/departements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartementResource {
    @Inject
    DepartementService departementService;

    @GET
    public List<Departement> getAllDepartments() {
        return departementService.getAllDepartments();
    }

    @GET
    @Path("/by-organisation/{organisationId}")
    public List<Departement> getDepartmentsByOrganisation(@PathParam("organisationId") Long organisationId) {
        return departementService.getDepartmentsByOrganisation(organisationId);
    }

    @GET
    @Path("/organisationless")
    public List<Departement> getOrganisationlessDepartments() {
        return departementService.getOrganisationlessDepartments();
    }

    @POST
    public Response createDepartment(Departement departement) {
        Departement createdDepartment = departementService.createDepartment(departement);
        return Response.status(Response.Status.CREATED).entity(createdDepartment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDepartment(@PathParam("id") Long id) {
        boolean deleted = departementService.deleteDepartment(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
