package parser.datasource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileDataSource implements DataSource {
    private final String filePath;

    public FileDataSource(String filePath){
        this.filePath = filePath;
    }

    /**
     * Opens a file and returns a FileReader for reading its content.
     *
     * @return A FileReader for reading the content of the file.
     */
    @Override
    public Reader getReader() throws IOException {
        return new FileReader(filePath);
    }
}