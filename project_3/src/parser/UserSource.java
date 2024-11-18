package parser;

public class UserSource {
    private final String sourceType;
    private final String format;

    /**
     * Constructor for UserSource class.
     * Structure for a UserSource object.
     *
     * @param sourceType The source type (e.g., newsapi or simple)
     * @param format The format of the source (e.g., File or URL)
     */
    public UserSource(String sourceType, String format){
        this.sourceType = sourceType;
        this.format = format;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getFormat() {
        return format;
    }
}
