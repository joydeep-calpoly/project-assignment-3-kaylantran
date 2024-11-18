package parser;
import parser.datasource.DataSource;
import parser.datasource.FileDataSource;
import parser.datasource.UrlDataSource;

import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("main-logs");
    private static final String API_KEY = "2679a656c844440fb881a6f7c66c3208";

    private static void processArticle(ArticleParser parser, DataSource source){
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
     *
     * This method loads articles from local files and a live API endpoint, parses them, and
     * displays their details in the specified format.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        NewsApiParser newsApi = new NewsApiParser(logger);
        SimpleJsonParser simple = new SimpleJsonParser(logger);

        processArticle(newsApi, new FileDataSource("project_2/inputs/newsapi.txt"));
        processArticle(simple, new FileDataSource("project_2/inputs/simple.txt"));
        processArticle(newsApi, new UrlDataSource("http://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY));
    }
}
