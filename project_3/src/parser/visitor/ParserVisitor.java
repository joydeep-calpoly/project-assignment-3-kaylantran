package parser.visitor;

import parser.*;
import parser.datasource.DataSource;

import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

public class ParserVisitor implements SourceVisitor{
    private final Logger logger;

    public ParserVisitor(Logger logger) {
        this.logger = logger;
    }

    public void processArticles(ArticleParser parser, DataSource source) {
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

    @Override
    public void visit(UserSource userSource, DataSource source) {
        String format = userSource.getFormat();
        ArticleParser parser = null;

        if ("newsapi".equalsIgnoreCase(format)) {
            parser = new NewsApiParser(logger);
        } else if ("simple".equalsIgnoreCase(format)) {
            parser = new SimpleJsonParser(logger);
        }

        if (parser == null) {
            logger.warning("Unsupported format: " + format);
            return;
        }
        processArticles(parser, source);
    }
}
