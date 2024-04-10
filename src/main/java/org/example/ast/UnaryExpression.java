package org.example.ast;

import org.example.lib.NumberValue;
import org.example.lib.Value;

public final class UnaryExpression implements Expression {
    public static enum Operation {
        PLUS, MINUS,
        PLUSPLUS, MINUSMINUS,

        NOT

    }
    private final Expression expr1;
    private final Operation operation;

    public UnaryExpression(Operation operation, Expression expr1) {
        this.expr1 = expr1;
        this.operation = operation;
    }
    @Override
    public Value eval() {

        switch (operation) {
            case MINUS: return new NumberValue(-expr1.eval().asNumber());
            case NOT: {
                boolean result = (expr1.eval().asNumber() == 0);
                return new NumberValue(result);
            }
            default:
                return new NumberValue(expr1.eval().asNumber());
        }

    }


}
