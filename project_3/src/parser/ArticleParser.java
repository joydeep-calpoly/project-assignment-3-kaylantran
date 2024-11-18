package parser;

import java.io.Reader;
import java.util.List;

public interface ArticleParser {
    /**
     * Parses articles from a given Reader input and returns a list of Articles.
     *
     * @param reader The reader for the input source.
     * @return A list of successfully parsed Articles.
     */
    List<Article> parseArticles(Reader reader);
}