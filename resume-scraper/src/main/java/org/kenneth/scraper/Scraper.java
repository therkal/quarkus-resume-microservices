package org.kenneth.scraper;

import io.smallrye.mutiny.Uni;
import org.kenneth.models.Person;

public abstract class Scraper {
    public abstract Uni<Person> scrape(String profileName);
}
