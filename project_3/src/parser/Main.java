package parser;
import parser.datasource.FileDataSource;
import parser.datasource.UrlDataSource;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("main-logs");
    private static final String API_KEY = "2679a656c844440fb881a6f7c66c3208";

    /**
     *
     * This method loads articles from local files and a live API endpoint, parses them, and
     * displays their details in the specified format.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        ArticleProcessor newsApiProcessor = new ArticleProcessor(
                new NewsApiParser(logger), logger);
        ArticleProcessor simpleProcessor = new ArticleProcessor(new SimpleJsonParser(logger), logger);

        newsApiProcessor.process(new FileDataSource("project_2/inputs/newsapi.txt"));
        simpleProcessor.process(new FileDataSource("project_2/inputs/simple.txt"));
        newsApiProcessor.process(new UrlDataSource("http://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY));
    }
}
