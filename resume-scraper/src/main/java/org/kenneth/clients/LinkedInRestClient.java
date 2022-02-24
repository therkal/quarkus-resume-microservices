package org.kenneth.clients;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jsoup.nodes.Document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "linked-in-client")
public interface LinkedInRestClient {

    @GET
    @Path("/{profile_name}")
    Uni<String> findLinkedInProfile(@PathParam("profile_name") String profileName);
}
