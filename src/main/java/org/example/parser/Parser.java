package org.example.parser;

import org.example.parser.ast.BinaryExpression;
import org.example.parser.ast.Expression;
import org.example.parser.ast.NumberExpression;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final Token EOF = new Token(TokenType.EOF, "");
    private final List<Token> tokens;
    private final int size;
    private int pos;


    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }
    public List<Expression> parse() {
        final List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(expression());
        }
        return result;
    }
    private Expression expression() {
        return additive();
    }
    private Expression additive() {
        Expression result = multiplicative();
        while (true) {
            if (match(TokenType.PLUS)) {
                result =  new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result =  new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }
    private Expression multiplicative() {
        Expression result = unary();
        while (true) {
            if (match(TokenType.STAR)) {
                result =  new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result =  new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }
        return result;
    }
    private Expression unary() {
    //        if (match(TokenType.MINUS)) {
    //            return new NumberExpression(Double.parseDouble(current.getText()));
    //        }
        return primary();
    }
    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        }
        throw new RuntimeException("Unknown expression");
    }
    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }
    private Token get(int relativePosition) { // peek(0) - текущий символ
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}
