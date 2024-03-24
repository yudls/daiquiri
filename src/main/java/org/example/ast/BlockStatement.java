package org.example.ast;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement{
    private final List<Statement> statements;
    private StringBuilder result;
    public BlockStatement() {
        statements = new ArrayList<>();
    }


    public void add(Statement statement) {
        statements.add(statement);
    }
    @Override
    public String execute() {
        result = new StringBuilder();
        for (Statement statement : statements) {
            result.append(statement.execute());
        }
        return result.toString();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        for (Statement statement : statements) {
            result.append(statement.toString()).append(System.lineSeparator());
        }
        return result.toString();
    }
}
