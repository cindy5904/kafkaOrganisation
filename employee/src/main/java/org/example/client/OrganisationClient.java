package org.example.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.dto.OrganisationDto;

@Path("/organisations")
@RegisterRestClient
public interface OrganisationClient {
    @GET
    @Path("/{id}")
    OrganisationDto getOrganisationById(@PathParam("id") Long id);

}
