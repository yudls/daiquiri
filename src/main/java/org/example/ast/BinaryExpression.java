package org.example.ast;

import org.example.lib.NumberValue;
import org.example.lib.StringValue;
import org.example.lib.Value;

public class BinaryExpression implements Expression {
    private final Expression exp1, exp2;
    private final char operation;

    public BinaryExpression(char operation, Expression exp1, Expression exp2) {
        this.operation = operation;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval() {
        final Value value1 = exp1.eval();
        final Value value2 = exp2.eval();

        if (value1 instanceof StringValue && value2 instanceof NumberValue)
            throw new RuntimeException("Ошибка конкатенации, можно конкатенировать только строку с другой строкой," +
                    " а не с числом");
        if (value1 instanceof NumberValue && value2 instanceof StringValue)
            throw new RuntimeException("Ошибка сложения, можно складывать только число с другим числом," +
                    " а не со строкой");


        if (value1 instanceof StringValue) {
            final String string1 = value1.asString();
            switch (operation) {
                case '*': {
                    final int iterations = (int) value2.asNumber();
                    final StringBuilder buffer = new StringBuilder();
                    for (int i = 0; i < 10; i++) {
                        buffer.append(string1);
                    }
                    return new StringValue(buffer.toString());
                }
                case '+':
                default:
                    return new StringValue(string1 + value2.asString());
            }
        }

        final double number1 = value1.asNumber();
        final double number2 = value2.asNumber();
        switch (operation) {
            case '-': return new NumberValue(number1 - number2);
            case '*': return new NumberValue(number1 * number2);
            case '/': return new NumberValue(number1 / number2);
            case '+':
            default:
                return new NumberValue(number1 + number2);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s %c %s]", exp1, operation, exp2);
    }
}
