package parser;

import parser.visitor.SourceVisitor;

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

    public void accept(SourceVisitor visitor){
        if ("File".equalsIgnoreCase(sourceType)) {
            visitor.visitFileSource(this, visitor);
        } else if ("URL".equalsIgnoreCase(sourceType)) {
            visitor.visitUrlSource(this, dataSource);
        } else {
            throw new IllegalArgumentException("Unsupported source type: " + sourceType);
        }
    }
}
