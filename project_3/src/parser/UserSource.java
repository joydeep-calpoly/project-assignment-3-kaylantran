package parser;

import parser.visitor.SourceVisitor;

public class UserSource {
    private final String source;
    private final String format;

    public UserSource(String source, String format){
        this.source = source;
        this.format = format;
    }

    public void accept(SourceVisitor visitor){
        if("File".equals(source)){
            visitor.visitFileSource(format);
        } else if ("URL".equals(source)) {
            visitor.visitUrlSource(format);
        }
    }
}
