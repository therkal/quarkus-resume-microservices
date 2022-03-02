package org.kennethmartens.parser;

import org.jsoup.nodes.Document;
import org.kennethmartens.models.Position;

import java.util.List;

public interface IExperienceParser {
    List<Position> parse(Document html);
}
