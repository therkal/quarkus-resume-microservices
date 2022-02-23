package org.kenneth.scraper;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.kenneth.clients.LinkedInClient;
import org.kenneth.models.Person;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LinkedInScraper extends Scraper {

    private final LinkedInClient client;
    public LinkedInScraper(@RestClient LinkedInClient client) {
        this.client = client;
    }

    @Override
    public Uni<Person> scrape(String profileName) {
        return client.findLinkedInProfile(profileName)
                .onItem()
                    .transform(document -> Person.builder().firstname("test").build())
                .onFailure()
                    .retry().atMost(2);
    }
}
