package org.kenneth.services;

import io.smallrye.mutiny.Uni;
import org.kenneth.models.DataSourceType;
import org.kenneth.models.Person;
import org.kenneth.scraper.LinkedInScraper;

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
