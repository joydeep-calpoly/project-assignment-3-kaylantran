package parser.datasource;

import java.io.IOException;
import java.io.Reader;

public interface DataSource {
    /**
     * Gets the reader of the data source.
     *
     * @return the reader.
     */
    Reader getReader() throws IOException;
}