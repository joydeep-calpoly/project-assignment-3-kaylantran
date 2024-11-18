package parser;
import java.time.LocalDateTime;

/**
 * Creates an Article object.
 *
 * @param title Title of the article.
 * @param description Description of the article.
 * @param url Url of the article.
 * @param publishedAt Date the article was published.
 * @param source Source of the article.
 */
public record Article(String title, String description, String url, LocalDateTime publishedAt,
                      parser.Article.Source source) {

    /**
     * Creates a Source object.
     *
     * @param id Id of the source.
     * @param name Name of the source.
     */
    public record Source(String id, String name) {
    }
}
