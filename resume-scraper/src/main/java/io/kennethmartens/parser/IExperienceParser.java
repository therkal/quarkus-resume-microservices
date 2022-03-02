package io.kennethmartens.parser;

import io.kennethmartens.models.Position;
import org.jsoup.nodes.Document;

import java.util.List;

public interface IExperienceParser {
    List<Position> parse(Document html);
}
