package io.kennethmartens.services;

import io.kennethmartens.models.DataSourceType;
import io.kennethmartens.models.Person;
import io.kennethmartens.scraper.LinkedInScraper;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class ScrapeService {

    @Inject
    LinkedInScraper scraper;

    public Uni<Person> scrape(DataSourceType dataSourceType, String profileName) {
        switch (dataSourceType) {
            case LINKED_IN:
                return scraper.scrape(profileName);
        }

        return Uni.createFrom().failure(NotFoundException::new);
    }
}
