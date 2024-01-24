package org.example.ast;

import org.example.lib.NumberValue;
import org.example.lib.Value;

public final class UnaryExpression implements Expression {
    private final Expression expr1;
    private final char operation;
    @Override
    public Value eval() {
        switch (operation) {
            case '-': return new NumberValue(-expr1.eval().asNumber());
            case '+':
            default:
                return new NumberValue(expr1.eval().asNumber());
        }

    }

    public UnaryExpression( char operation, Expression expr1) {
        this.operation = operation;
        this.expr1 = expr1;
    }
}
