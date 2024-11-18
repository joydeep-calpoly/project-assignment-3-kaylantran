package parser.visitor;

public interface SourceVisitor {
    void visitFileSource(String format);
    void visitUrlSource(String format);
}
