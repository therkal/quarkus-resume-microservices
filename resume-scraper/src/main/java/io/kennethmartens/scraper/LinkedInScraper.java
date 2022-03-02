package io.kennethmartens.scraper;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import io.kennethmartens.clients.LinkedInClient;
import io.kennethmartens.models.Person;
import io.kennethmartens.parser.LinkedInExperienceParser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
public class LinkedInScraper extends Scraper {

    @Inject LinkedInClient client;
    @Inject LinkedInExperienceParser experienceParser;

    @Override
    public Uni<Person> scrape(String profileName) {
        return client.getLinkedInProfile(profileName)
                .onItem()
                .transform(experienceParser::parse)
                .onItem()
                .transform(positions -> Person.builder()
                        .experience(positions)
                        .build());
    }

}
