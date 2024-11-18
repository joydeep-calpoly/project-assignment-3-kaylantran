package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.datasource.DataSource;
import parser.datasource.FileDataSource;
import parser.datasource.UrlDataSource;
import parser.visitor.ParserVisitor;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleParserTest {
    private NewsApiParser newsApiParser;
    private SimpleJsonParser simpleJsonParser;

    /**
     * Initializes the mock logger and parser instances before each test.
     */
    @BeforeEach
    public void setup() {
        Logger logger = Logger.getLogger("test-log");
        newsApiParser = new NewsApiParser(logger);
        simpleJsonParser = new SimpleJsonParser(logger);
    }

    /**
     * Tests parsing valid NewsAPI JSON input file.
     */
    @Test
    public void testValidNewsApiInput(){
        try (Reader reader = new FileReader(Paths.get("C:\\Users\\kayla\\Desktop\\Classes\\305\\project-assignment-3-kaylantran\\project_3\\inputs\\newsapi.txt").toFile())) {
            List<Article> articles = newsApiParser.parseArticles(reader);
            assertEquals(11, articles.size(), "Expected 11 valid articles from example.json");
            articles.forEach(System.out::println);
        } catch (Exception e) {
            fail("Exception occurred while reading example.json: " + e.getMessage());
        }
    }

    /**
     * Tests parsing a valid Simple JSON input file `simple.txt`.
     */
    @Test
    public void testValidSimpleJsonInput() {
        try (Reader reader = new FileReader(Paths.get("C:\\Users\\kayla\\Desktop\\Classes\\305\\project-assignment-3-kaylantran\\project_3\\inputs\\simple.txt").toFile())) {
            List<Article> articles = simpleJsonParser.parseArticles(reader);
            assertEquals(1, articles.size(), "Expected 1 article from simple JSON");
        } catch (Exception e) {
            fail("Exception occurred while reading simple.txt: " + e.getMessage());
        }
    }

    /**
     * Tests parsing an empty JSON array for both parsers.
     */
    @Test
    public void testParseEmptyJson() {
        String json = "{\"articles\": []}";
        Reader reader = new StringReader(json);

        List<Article> newsApiArticles = newsApiParser.parseArticles(reader);
        assertTrue(newsApiArticles.isEmpty(), "Expected no articles from empty NewsAPI JSON array");

        List<Article> simpleArticles = simpleJsonParser.parseArticles(reader);
        assertTrue(simpleArticles.isEmpty(), "Expected no articles from empty Simple JSON array");
    }

    /**
     * Tests parsing a JSON string with a single valid news article to ensure all fields are parsed correctly.
     */
    @Test
    public void testValidNewsArticleFields() {
        String json = """
        {
            "articles": [
                {
                    "source": {"id": "test-source-id", "name": "Test Source Name"},
                    "title": "Test Title",
                    "description": "Test Description",
                    "publishedAt": "2024-11-17T12:34:56Z",
                    "url": "http://example.com",
                    "content": "This is the content of the test article."
                }
            ]
        }
    """;

        Reader reader = new StringReader(json);
        List<Article> articles = newsApiParser.parseArticles(reader);
        assertEquals(1, articles.size(), "Expected 1 article from the JSON input");
        Article article = articles.getFirst();
        LocalDateTime expectedDateTime = LocalDateTime.parse("2024-11-17T12:34:56", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertEquals("Test Title", article.title(), "The title does not match");
        assertEquals("Test Description", article.description(), "The description does not match");
        assertEquals(expectedDateTime, article.publishedAt(), "The published date does not match");
        assertEquals("http://example.com", article.url(), "The URL does not match");
        assertEquals("Test Source Name", article.source().name(), "The source name does not match");
        assertEquals("test-source-id", article.source().id(), "The source ID does not match");
    }

    /**
     * Tests parsing a JSON string with a single valid simple article to ensure all fields are parsed correctly.
     */
    @Test
    public void testParseValidSimpleJsonArticle() {
        String json = """
        {
            "title": "Test Title",
            "description": "Test Description",
            "publishedAt": "2024-11-17T12:34:56",
            "url": "http://example.com"
        }
    """;

        Reader reader = new StringReader(json);
        List<Article> articles = simpleJsonParser.parseArticles(reader);
        assertEquals(1, articles.size(), "Expected 1 article from the Simple JSON input");
        Article article = articles.getFirst();
        LocalDateTime expectedDateTime = LocalDateTime.parse("2024-11-17T12:34:56", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        assertEquals("Test Title", article.title(), "The title does not match");
        assertEquals("Test Description", article.description(), "The description does not match");
        assertEquals(expectedDateTime, article.publishedAt(), "The published date does not match");
        assertEquals("http://example.com", article.url(), "The URL does not match");
    }


    @Test
    public void testVisitorNewsApiFile() {
        Logger logger = Logger.getLogger("test-log");
        ParserVisitor visitor = new ParserVisitor(logger);
        UserSource format = new UserSource("File", "newsapi");
        FileDataSource source = new FileDataSource("C:\\Users\\kayla\\Desktop\\Classes\\305\\project-assignment-3-kaylantran\\project_3\\inputs\\newsapi.txt");
        visitor.visit(format, source);

        assertInstanceOf(NewsApiParser.class, visitor.getLastParser(), "Expected NewsApiParser for 'newsapi' format, 'File' type");
    }

    @Test
    public void testVisitorSimpleFile() {
        Logger logger = Logger.getLogger("test-log");
        ParserVisitor visitor = new ParserVisitor(logger);
        UserSource format = new UserSource("File", "simple");
        FileDataSource source = new FileDataSource("C:\\Users\\kayla\\Desktop\\Classes\\305\\project-assignment-3-kaylantran\\project_3\\inputs\\simple.txt");
        visitor.visit(format, source);

        assertInstanceOf(SimpleJsonParser.class, visitor.getLastParser(), "Expected SimpleJsonParser for 'simple' format, 'File' type");
    }

    @Test
    public void testVisitorUrl() {
        Logger logger = Logger.getLogger("test-log");
        String API_KEY = "2679a656c844440fb881a6f7c66c3208";
        ParserVisitor visitor = new ParserVisitor(logger);
        UserSource format = new UserSource("URL", "newsapi");
        DataSource source = new UrlDataSource("http://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY);
        visitor.visit(format, source);

        assertInstanceOf(NewsApiParser.class, visitor.getLastParser(), "Expected NewsApiParser for 'newsapi' format, 'URL' type");
    }
}