package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleJsonParser implements ArticleParser{
    private final Logger logger;
    private final ArticleFactory factory;
    private final ArticleValidator validator;

    public SimpleJsonParser(Logger logger){
        this.logger = logger;
        this.validator = new ArticleValidator(logger);
        this.factory = new ArticleFactory();
    }

    /**
     * Parses an article in a simplified JSON format. Reads JSON data from a Reader
     * and constructs an article.
     *
     * @param reader The reader for the JSON input.
     * @return A list of successfully parsed Article.
     */
    @Override
    public List<Article> parseArticles(Reader reader) {
        List<Article> articles = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(reader);
            if (validator.isValid(root)) {
                try {
                    Article article = factory.create(root);
                    articles.add(article);
                } catch (Exception e) {
                    logger.warning("Error creating article: " + e.getMessage());
                }
            } else {
                logger.warning("Article skipped due to validation failure.");
            }
        } catch (Exception e) {
            logger.warning("Error processing JSON input: " + e.getMessage());
        }
        return articles;
    }
}