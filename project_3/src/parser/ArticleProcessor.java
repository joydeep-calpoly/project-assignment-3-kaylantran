package parser;

import parser.datasource.DataSource;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

public class ArticleProcessor {
    private final ArticleParser parser;
    private final Logger logger;

    public ArticleProcessor(ArticleParser parser, Logger logger){
        this.parser = parser;
        this.logger = logger;
    }

    /**
     * Parses and prints articles from the specified data source.
     *
     * @param source The data source containing the articles.
     */
    public void process(DataSource source){
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

}
