package org.kennethmartens.services;

import io.smallrye.mutiny.Uni;
import org.kennethmartens.models.DataSourceType;
import org.kennethmartens.models.Person;
import org.kennethmartens.scraper.LinkedInScraper;

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
