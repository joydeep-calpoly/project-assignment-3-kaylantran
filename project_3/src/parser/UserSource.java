package parser;

public class UserSource {
    private final String sourceType;
    private final String format;

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
