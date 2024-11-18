package parser.visitor;

import parser.UserSource;
import parser.datasource.DataSource;

public interface SourceVisitor {
    /**
     * Visits the specified user source and processes articles from the corresponding data source
     * using the appropriate parser based on the user source's format.
     *
     * @param format The user-defined source specifying the type and format.
     * @param source The actual data source containing the articles to be parsed.
     */
    void visit(UserSource format, DataSource source);
}
