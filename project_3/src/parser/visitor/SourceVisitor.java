package parser.visitor;

import parser.UserSource;
import parser.datasource.DataSource;

public interface SourceVisitor {
    /**
     * Visit a File source.
     *
     * @param format The user-defined source specifying the type and format.
     * @param source The actual file data source containing the articles to be parsed.
     */
    void visitFileSource(UserSource format, DataSource source);
    /**
     * Visit a URL source.
     *
     * @param format The user-defined source specifying the type and format.
     * @param source The actual URL data source containing the articles to be parsed.
     */
    void vistUrlSource(UserSource format, DataSource source);
}
