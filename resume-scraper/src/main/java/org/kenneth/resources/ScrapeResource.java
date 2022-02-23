package org.kenneth.resources;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.kenneth.models.DataSourceType;
import org.kenneth.models.Person;
import org.kenneth.services.ScrapeService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/scrape")
@Tag(name = "scraper")
@Produces(APPLICATION_JSON)
public class ScrapeResource {

    @Inject
    ScrapeService service;

    @GET
    @Operation(summary = "Returns scraped data for a given identifier")
    @APIResponse(
            responseCode = "200",
            description = "Gets a scraped data for given data-source and profile name",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Person.class))
    )
    @APIResponse(
            responseCode = "404",
            description = "The profile is not found for a given identifier"
    )
    public Uni<Response> scrape(@QueryParam("datasourceType") DataSourceType datasourceType, @QueryParam("profile-path") String profilePath) {
        return service.scrape(datasourceType, profilePath)
                .onItem().transform(item -> Response.ok(item).build());
    }
}