package parser;
import parser.datasource.DataSource;
import parser.datasource.FileDataSource;
import parser.datasource.UrlDataSource;
import parser.visitor.ParserVisitor;
import parser.visitor.SourceVisitor;

import java.io.Reader;
import java.util.List;
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
        UserSource newsApi = new UserSource("File", "newsapi");
        UserSource simple  = new UserSource("File", "simple");
        UserSource url = new UserSource("URL", "newsapi");
        DataSource file1 = new FileDataSource("C:\\Users\\kayla\\Desktop\\Classes\\305\\project-assignment-3-kaylantran\\project_3\\inputs\\newsapi.txt");
        DataSource file2 = new FileDataSource("C:\\Users\\kayla\\Desktop\\Classes\\305\\project-assignment-3-kaylantran\\project_3\\inputs\\simple.txt");
        DataSource urlSource = new UrlDataSource("http://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY);
        SourceVisitor visitor = new ParserVisitor(logger);

        System.out.println("News file");
        visitor.visit(newsApi, file1);
        System.out.println("Simple file");
        visitor.visit(simple, file2);
        System.out.println("Url");
        visitor.visit(url, urlSource);
    }
}
