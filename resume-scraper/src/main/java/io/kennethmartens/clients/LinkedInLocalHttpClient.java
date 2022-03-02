package io.kennethmartens.clients;

import io.quarkus.arc.DefaultBean;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@Slf4j
@DefaultBean
@ApplicationScoped
public class LinkedInLocalHttpClient implements ILinkedInDataClient {

    @Inject
    Vertx vertx;

    public Uni<String> findLinkedInProfile(String profileName) {
        return vertx.fileSystem().readFile("local_files/linkedin_profile.html")
                .onItem().transform(content -> content.toString(StandardCharsets.UTF_8));
    }

}
