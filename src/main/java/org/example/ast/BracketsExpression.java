package org.example.ast;

import org.example.lib.StringValue;
import org.example.lib.Value;

public class BracketsExpression implements Expression{
    private final Expression string_expression;
    private final Expression index_expression;

    public BracketsExpression(Expression string_expression, Expression index_expression) {
        this.string_expression = string_expression;
        this.index_expression = index_expression;
    }

    @Override
    public Value eval() {
        int index = (int) index_expression.eval().asNumber();
        String string = string_expression.eval().asString();
        String result = String.valueOf(string.charAt(index));
        return new StringValue(result);
    }

    @Override
    public String toString() {
        return String.format(string_expression.eval().asString() + "[" + index_expression.eval().asNumber() + "]");
    }
}
