package parser;
import java.time.LocalDateTime;

public record Article(String title, String description, String url, LocalDateTime publishedAt,
                      parser.Article.Source source) {

    public record Source(String id, String name) {
    }
}
