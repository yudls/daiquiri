package org.example.ast;

public class ifStatement implements Statement{
    private final Expression expression;
    private final Statement ifStatement;
    private final Statement elseStatement;

    public ifStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String execute() {
        final double expression_result = expression.eval().asNumber();
        String result = "";
        if (expression_result != 0) {
            result = ifStatement.execute();
        } else if (elseStatement != null) {
             result = elseStatement.execute();
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("Если ").append(expression).append(' ').append(ifStatement);
        if (elseStatement != null) {
            result.append("\nИначе ").append(elseStatement);
        }
        return result.toString();
    }
}
