package parser;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.logging.Logger;

public class ArticleValidator {
    private final Logger logger;

    public ArticleValidator(Logger logger) {
        this.logger = logger;
    }

    /**
     * Checks if the article has all required fields.
     *
     * @param node The JSON node representing an article.
     * @return true if the article is valid; false otherwise.
     */
    public boolean isValid(JsonNode node) {
        String[] requiredFields = {"title", "description", "url", "publishedAt"};
        boolean[] isValid = {true};
        Arrays.stream(requiredFields).forEach(field -> {
            if (node.get(field) == null || node.get(field).isNull()) {
                logger.warning("Article skipped due to missing or null field: " + field);
                isValid[0] = false;
            }
        });
        if (!isValid[0]) {
            return false;
        }
        JsonNode sourceNode = node.get("source");
        if (sourceNode != null) {
            if (sourceNode.get("name") == null || sourceNode.get("name").isNull() ||
                    sourceNode.get("id") == null || sourceNode.get("id").isNull()) {
                logger.warning("Article skipped due to missing or null source name or id.");
                return false;
            }
        }
        return true;
    }
}
