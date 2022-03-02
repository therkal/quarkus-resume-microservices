package org.kennethmartens.scraper;

import io.smallrye.mutiny.Uni;
import org.kennethmartens.models.Person;

public abstract class Scraper {
    public abstract Uni<Person> scrape(String profileName);
}
