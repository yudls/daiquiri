package org.example.ast;

import org.example.lib.NumberValue;
import org.example.lib.StringValue;
import org.example.lib.Value;

public class ConditionalExpression implements Expression {
   public static enum Operator {
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/"),

        EQUALS("=="),
        NOT_EQUALS("!="),

        LT("<"),
        LTEQ("<="),
        GT(">"),
        GTEQ(">="),

        AND("&&"),
        OR("||");

        private final String name;
        private Operator(String name) {
            this.name = name;
        }

       public String getName() {
           return name;
       }
   }
    private final Expression exp1, exp2;
    private final Operator operation;

    public ConditionalExpression(Operator operation, Expression exp1, Expression exp2) {
        this.operation = operation;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval() {
        final Value value1 = exp1.eval();
        final Value value2 = exp2.eval();

        double number1, number2;
        if (value1 instanceof StringValue) {
            number1 = value1.asString().compareTo(value2.asString());
            number2 = 0;
        } else {
            number1 = value1.asNumber();
            number2 = value2.asNumber();
        }
        boolean result;
        switch (operation) {
            case LT: result = number1 < number2; break;
            case LTEQ: result = number1 <= number2; break;
            case GT: result =  number1 > number2; break;
            case GTEQ: result =  number1 >= number2; break;
            case NOT_EQUALS: result =  number1 != number2; break;
            case AND: result = (number1 != 0) && (number2 != 0);
            case OR: result = (number1 != 0) || (number2 != 0);
            case EQUALS:
            default:
                result = number1 == number2; break;
        }
        return new NumberValue(result);
    }

    @Override
    public String toString() {
        return String.format("[%s %s %s]", exp1, operation.getName(), exp2);
    }
}
