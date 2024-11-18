package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;
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
    void setup() {
        Logger logger = Logger.getLogger("test-log");
        newsApiParser = new NewsApiParser(logger);
        simpleJsonParser = new SimpleJsonParser(logger);
    }

    /**
     * Tests parsing valid NewsAPI JSON input file.
     */
    @Test
    void testValidNewsApiInput(){
        try (Reader reader = new FileReader(Paths.get("project_2/inputs/newsapi.txt").toFile())) {
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
    void testValidSimpleJsonInput() {
        try (Reader reader = new FileReader(Paths.get("project_2/inputs/simple.txt").toFile())) {
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
     * Tests parsing an invalid JSON file 'bad.json'.
     */
    @Test
    void testBadInput() {
        try (Reader reader = new FileReader(Paths.get("project_2/inputs/bad.json").toFile())) {
            List<Article> articles = newsApiParser.parseArticles(reader);
            assertEquals(9, articles.size(), "Expected 9 valid articles from bad.json");
        } catch (Exception e) {
            fail("Exception occurred while reading bad.json: " + e.getMessage());
        }
    }
}