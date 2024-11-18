package parser.datasource;

import java.io.IOException;
import java.io.Reader;

public interface DataSource {
    Reader getReader() throws IOException;
}