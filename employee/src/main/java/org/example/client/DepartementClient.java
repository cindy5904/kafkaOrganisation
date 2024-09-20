package org.example.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.dto.DepartementDto;

@Path("/departments")
@RegisterRestClient
public interface DepartementClient {
    @GET
    @Path("/{id}")
    DepartementDto getDepartementById(@PathParam("id") Long id);

}
