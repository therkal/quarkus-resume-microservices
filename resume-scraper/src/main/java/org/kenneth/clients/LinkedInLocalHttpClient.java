package org.kenneth.clients;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class LinkedInLocalHttpClient {

    @Inject
    Vertx vertx;

    Uni<String> findLinkedInProfile(String profileName) {
        return vertx.fileSystem().readFile("/Users/kennethmartens/linkedin_profile.html")
                .onItem().transform(content -> content.toString(StandardCharsets.UTF_8));
    }

}
