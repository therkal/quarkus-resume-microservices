package io.kennethmartens.scraper;

import io.smallrye.mutiny.Uni;
import io.kennethmartens.models.Person;

public abstract class Scraper {
    public abstract Uni<Person> scrape(String profileName);
}
