package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NewsApiParser implements ArticleParser{
    private final Logger logger;
    private final ArticleValidator validator;
    private final ArticleFactory factory;

    /**
     * Constructor for NewsApiParser class.
     * Structure for a NewsApiParser object.
     *
     * @param logger The logger for the news api parser object.
     */
    public NewsApiParser(Logger logger) {
        this.logger = logger;
        this.validator = new ArticleValidator(logger);
        this.factory = new ArticleFactory();
    }

    /**
     * Parses articles from a JSON input in NewsAPI format and returns a list of Articles.
     *
     * @param reader The reader for the JSON input.
     * @return A list of successfully parsed Article objects.
     */
    @Override
    public List<Article> parseArticles(Reader reader) {
        List<Article> articles = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(reader);
            ArrayNode articlesNode = (ArrayNode) root.get("articles");
            for (JsonNode articleNode : articlesNode) {
                if(validator.isValid(articleNode)) {
                    try{
                        Article article = factory.create(articleNode);
                        articles.add(article);
                    }catch (Exception e){
                        logger.warning("Error creating article: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.warning("Error processing JSON input: " + e.getMessage());
        }
        return articles;
    }
}