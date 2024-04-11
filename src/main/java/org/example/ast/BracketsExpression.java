package org.example.ast;

import org.example.lib.StringValue;
import org.example.lib.Value;

public class BracketsExpression implements Expression {
    private final Expression string_expression;
    private final Expression begin_index_expression;
    private final Expression end_index_expression;
    private final boolean colon;

    /**
     * @param string_expression      Строка или переменная ValueExpression или VariableExpression
     * @param begin_index_expression Идекс начала подстроки
     * @param end_index_expression   Идекс конца подстроки
     * @param colon                  Флаг того, что было указано двоеточие
     */
    public BracketsExpression(Expression string_expression, Expression begin_index_expression,
                              Expression end_index_expression, boolean colon) {
        this.string_expression = string_expression;
        this.colon = colon;
        this.begin_index_expression = begin_index_expression;
        this.end_index_expression = end_index_expression;
    }


    @Override
    public Value eval() {
        final String string = string_expression.eval().asString();
        final String result;
        final int begin;
        final int end;

        if (colon) {
            if (begin_index_expression == null && end_index_expression == null) {
                result = string;
            } else if (end_index_expression == null) {
                begin = (int) begin_index_expression.eval().asNumber();
                result = string.substring(begin);
            } else if (begin_index_expression == null) {
                end = (int) end_index_expression.eval().asNumber();
                result = string.substring(0, end);
            } else {
                begin = (int) begin_index_expression.eval().asNumber();
                end = (int) end_index_expression.eval().asNumber();
                result = string.substring(begin, end);
            }
        } else {
            System.out.println("begin_index_expression.eval().asNumber())");
            begin = (int) begin_index_expression.eval().asNumber();
            System.out.println("begin_index_expression.eval().asNumber(): " + begin);
            result = string.substring(begin, begin + 1);
        }
        return new StringValue(result);
    }


    @Override
    public String toString() {
        return String.format(string_expression.toString() + "[" +
                (begin_index_expression == null ? " " : begin_index_expression.toString()) +
                (colon ? " : " : "") +
                (end_index_expression == null ? " " : end_index_expression.toString()) + "]");
    }
}
