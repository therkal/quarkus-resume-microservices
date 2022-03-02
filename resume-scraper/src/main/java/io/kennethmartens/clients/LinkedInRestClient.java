package io.kennethmartens.clients;

import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@IfBuildProfile("prod")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "linked-in-client")
public interface LinkedInRestClient extends ILinkedInDataClient {

    @GET
    @Path("/{profile_name}")
    Uni<String> findLinkedInProfile(@PathParam("profile_name") String profileName);
}
