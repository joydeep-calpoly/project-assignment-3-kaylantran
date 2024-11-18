package parser;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ArticleFactory {
    private static final DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter simpleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    /**
     * Creates an Article object from a JSON node.
     *
     * @param node The JSON node representing an article.
     * @return The created Article object.
     */
    public Article create(JsonNode node) {
        String title = node.get("title").asText();
        String description = node.get("description").asText();
        String url = node.get("url").asText();
        String publishedAtString = node.get("publishedAt").asText();
        LocalDateTime publishedAt;
        try {
            publishedAt = LocalDateTime.parse(publishedAtString, isoFormatter);
        } catch (DateTimeParseException e) {
            publishedAt = LocalDateTime.parse(publishedAtString, simpleFormatter);
        }
        JsonNode sourceNode = node.get("source");
        String sourceId = null;
        String sourceName = "Unknown Source";
        if (sourceNode != null) {
            sourceId = sourceNode.has("id") ? sourceNode.get("id").asText(null) : null;
            sourceName = sourceNode.has("name") ? sourceNode.get("name").asText() : sourceName;
        }
        return new Article(title, description, url, publishedAt, new Article.Source(sourceId, sourceName));
    }
}
