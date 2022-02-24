package org.kenneth.scraper;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kenneth.clients.LinkedInClient;
import org.kenneth.models.Person;
import org.kenneth.models.Position;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
public class LinkedInScraper extends Scraper {

    @Inject
    LinkedInClient client;

    @Override
    public Uni<Person> scrape(String profileName) {
        return client.getLinkedInProfile(profileName)
                .onItem()
                .transform(this::parseExperience)
                .onItem()
                .transform(positions -> {
                    return Person.builder()
                            .experience(positions)
                            .build();
                });
    }

    private List<Position> parseExperience(Document html) {
        List<Position> positions = new ArrayList<>();

        Elements experienceTags = html.select(".experience-item");
        log.info("Parsing experience items from html; we have {} items", experienceTags.size());
        for(Element element : experienceTags) {
            if(element.hasClass("experience-group")) {
                Elements experienceGroupPositions = element.getElementsByClass("experience-group-position");
                log.info("Experience group with {} elements", experienceGroupPositions.size());
                // Split further
                for(Element experienceGroupPosition : experienceGroupPositions) {
                    positions.add(parsePositionElement(experienceGroupPosition));
                }
            } else {
                // Normal experience item.
                positions.add(parsePositionElement(element));
            }
        }

        log.info("Parsed all positions; size {}", positions.size());

        return positions;
    }

    private Position parsePositionElement(Element element) {
        String role = element.getElementsByClass("profile-section-card__title").first().childNode(0).toString().trim();
        String company = element.getElementsByClass("profile-section-card__subtitle-link").first().childNode(0).toString().trim();
        log.info(
                "Parsing experience for role {} at company {}",
                role,
                company
        );

        return Position.builder()
                .role(role)
                .company(company)
                .build();
    }

}
