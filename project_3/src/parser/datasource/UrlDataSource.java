package parser.datasource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UrlDataSource implements DataSource{
    private final String urlString;

    public UrlDataSource(String urlString){
        this.urlString = urlString;
    }

    /**
     * Opens a connection to the specified URL and retrieves an InputStream reader.
     *
     * @return A Reader for the content of the URL response.
     */
    @Override
    public Reader getReader() throws IOException {
        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (Exception e) {
            throw new IOException("Failed to get reader from URL: " + urlString, e);
        }
    }
}