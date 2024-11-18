package parser;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    /**
     * Sets up a logger to write log messages to a file.
     *
     * @param filename The name of the log file.
     * @return A configured Logger instance.
     */
    public static Logger setupLogger(String filename) {
        Logger logger = Logger.getLogger("ArticleParserLogger");
        try {
            FileHandler fileHandler = new FileHandler(filename);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logger;
    }
}
