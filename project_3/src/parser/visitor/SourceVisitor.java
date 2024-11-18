package parser.visitor;

import parser.UserSource;
import parser.datasource.DataSource;

public interface SourceVisitor {
    void visit(UserSource format, DataSource source);
}
