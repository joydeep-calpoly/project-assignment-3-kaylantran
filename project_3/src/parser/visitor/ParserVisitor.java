package parser.visitor;

import parser.*;
import parser.datasource.DataSource;

import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

public class ParserVisitor implements SourceVisitor{
    private final Logger logger;
    private ArticleParser lastParser;

    /**
     * Constructor for ParserVisitor class.
     * Structure for a ParserVisitor object.
     *
     * @param logger The logger for ParserVisitor object.
     */
    public ParserVisitor(Logger logger) {
        this.logger = logger;
    }

    /**
     * Visits the file source and processes articles from the corresponding data source
     * using the appropriate parser based on the user source's format.
     *
     * @param userSource The user-defined source specifying the type and format.
     * @param source The actual data source containing the articles to be parsed.
     */
    @Override
    public void visitFileSource(UserSource userSource, DataSource source) {
        String format = userSource.getFormat();
        ArticleParser parser;

        if ("newsapi".equalsIgnoreCase(format)) {
            parser = new NewsApiParser(logger);
        } else if ("simple".equalsIgnoreCase(format)) {
            parser = new SimpleJsonParser(logger);
        } else {
            logger.warning("Unsupported format for file source: " + format);
            return;
        }

        try(Reader reader = source.getReader()){
            List<Article> articles = parser.parseArticles(reader);
            articles.forEach(article -> {
                System.out.println(article.title());
                System.out.println(article.description());
                System.out.println(article.publishedAt());
                System.out.println(article.url());
                System.out.println();
            });
        }catch (Exception e){
            logger.warning("Error processing articles: " + e.getMessage());
        }
    }

    /**
     * Visits the URL source and processes articles from the corresponding data source
     * using the appropriate parser based on the user source's format.
     *
     * @param userSource The user-defined source specifying the type and format.
     * @param source The actual data source containing the articles to be parsed.
     */
    @Override
    public void vistUrlSource(UserSource userSource, DataSource source) {
        String format = userSource.getFormat();
        ArticleParser parser;

        if ("newsapi".equalsIgnoreCase(format)) {
            parser = new NewsApiParser(logger);
        } else {
            logger.warning("Unsupported format for URL source: " + format);
            return;
        }

        try(Reader reader = source.getReader()){
            List<Article> articles = parser.parseArticles(reader);
            articles.forEach(article -> {
                System.out.println(article.title());
                System.out.println(article.description());
                System.out.println(article.publishedAt());
                System.out.println(article.url());
                System.out.println();
            });
        }catch (Exception e){
            logger.warning("Error processing articles: " + e.getMessage());
        }
    }

    /**
     * Gets the last used parser.
     *
     * @return last used parser.
     */
    public ArticleParser getParser(){
        return lastParser;
    }
}
