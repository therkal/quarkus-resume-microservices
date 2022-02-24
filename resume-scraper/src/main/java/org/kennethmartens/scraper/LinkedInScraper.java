package org.kennethmartens.scraper;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kennethmartens.clients.LinkedInClient;
import org.kennethmartens.models.Person;
import org.kennethmartens.models.Position;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                .transform(positions -> Person.builder()
                        .experience(positions)
                        .build());
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
                    positions.add(parsePositionElement(experienceGroupPosition, true));
                }
            } else {
                // Normal experience item.
                positions.add(parsePositionElement(element, false));
            }
        }

        log.info("Parsed all positions; size {}", positions.size());

        return positions;
    }

    private Position parsePositionElement(Element element, Boolean isExperienceGroup) {
        String role = element.getElementsByClass("profile-section-card__title").first().childNode(0).toString().trim();
        String company = element.getElementsByClass("profile-section-card__subtitle-link").first().childNode(0).toString().trim();
        String durationString = element.getElementsByClass("date-range__duration").first().childNode(0).toString().trim();

        Element descriptionElement = element.getElementsByClass("show-more-less-text__text--more").first();
        if (descriptionElement == null) {
            descriptionElement = element.getElementsByClass("show-more-less-text__text--less").first();
        }
        String description = descriptionElement != null ? descriptionElement.childNode(0).toString().trim() : "";

        Element locationElement;
        if(isExperienceGroup) {
            locationElement = element.getElementsByClass("experience-group-position__location").first();
        } else {
            locationElement = element.getElementsByClass("experience-item__location").first();
        }
        String location = locationElement != null ? locationElement.childNode(0).toString().trim() : "";

        List<YearMonth> times = this.parsePositionYearMonth(element);

        log.info("Parsing experience for role {} at company {}", role, company);

        return Position.builder()
                .role(role)
                .company(company)
                .duration(durationString)
                .startDate(times.size() != 0 ? times.get(0) : null)
                .endDate(times.size() > 1 ? times.get(1) : null)
                .description(description)
                .location(location)
                .build();
    }

    /**
     * Method that locates the time tag for a position, and parses it accordingly.
     * @param element the Position
     * @return a List of YearMonths containing 1 or 2 elements; if element 2 = null the position is currently active
     */
    private List<YearMonth> parsePositionYearMonth(Element element) {
        Element startEndElements = element.getElementsByClass("date-range").first();
        if (startEndElements == null) {
            log.error("Date Range element could not be found for element {}", element);
            return Collections.emptyList();
        }

        return startEndElements.children().stream().filter(node -> node.tagName().equals("time"))
                .map(timeElement -> {
                    // create a formatter
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
                    return YearMonth.parse(timeElement.text().trim(), formatter);
                })
                .collect(Collectors.toList());
    }

}
