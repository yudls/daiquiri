package org.example.parser;

import org.example.ast.*;

import java.util.List;

public final class Parser {

    private static final Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> tokens;
    private final int size;

    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public Statement parse() {
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            Statement res = statement();
            System.out.println(res);
            result.add(res);
        }
        return result;
    }
    private Statement block() {
        final BlockStatement block = new BlockStatement();
        consume(TokenType.LBRACE);
        while (!match(TokenType.RBRACE)) {
            block.add(statement());
        }
        return block;
    }
    private Statement statementOrBlock() {
    if (get(0).getType() == TokenType.LBRACE) return block();
    return statement();
    }


    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        if (match(TokenType.IF)) {
            return ifElse();
        }
        if (match(TokenType.WHILE)) {
            return whileStatement();
        }
        if (match(TokenType.DO)) {
            return doWhileStatement();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }
        if (match(TokenType.BREAK)) {
            return new BreakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            return new ContinueStatement();
        }
        if (get(0).getType() == TokenType.LBRACE) {
            return block();
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAREN) {
            return new FunctionStatement(function());
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.EQ) {
            return assignmentStatement();
        }
        throw new RuntimeException("Неизвестная инструкция ");
    }


    private Statement assignmentStatement() {
        // WORD EQ

        final String variable = get(0).getText();
        consume(TokenType.WORD);
        consume(TokenType.EQ);
        return new AssignmentStatement(variable, expression());
    }

    private Statement ifElse() {
        final Expression condition = expression();
        final Statement ifStatement = statementOrBlock();
        final Statement elseStatement;
        if (match(TokenType.ELSE)) {
            elseStatement = statementOrBlock();
        } else {
            elseStatement = null;
        }
        return new ifStatement(condition, ifStatement, elseStatement);
    }

    private Statement whileStatement() {
        final Expression condition = expression();
        final Statement statement = statementOrBlock();
        return new WhileStatement(condition, statement);
    }

    private Statement  doWhileStatement() {
        final Statement statement = statementOrBlock();
        consume(TokenType.WHILE);
        final Expression condition = expression();
        return new DoWhileStatement(condition, statement);
    }

    private Statement forStatement() {
        consume(TokenType.LPAREN);
        final Statement initialization = assignmentStatement();
        consume(TokenType.SEMICOLON);
        final Expression termination = expression();
        consume(TokenType.SEMICOLON);
        final Statement increment = assignmentStatement();
        consume(TokenType.RPAREN);
        final Statement statement = statementOrBlock();
        return new ForStatement(initialization, termination, increment, statement);
    }

    private Expression textBrackets(Expression expression) {
        Expression begin = null;
        Expression end = null;
        boolean colon = false;


        if (get(0).getType() != TokenType.COLON) {
            begin = expression();
        }
        if (match(TokenType.COLON)) {
            colon = true;
            if (get(0).getType() != TokenType.RBRACKET) {
                end = expression();
            }
        }

        consume(TokenType.RBRACKET);
        if (!colon && begin != null && end != null) throw new RuntimeException("Отсутствует \":\" - разделитель индексов в квадратных скобках");
        if (!colon && begin == null && end == null) throw new RuntimeException("Отсутствует индекс в квадратных скобках");
        return new BracketsExpression(expression, begin, end, colon);
    }

    private FunctionalExpression function() {
        final String name = consume(TokenType.WORD).getText();
        consume(TokenType.LPAREN);
        final FunctionalExpression function = new FunctionalExpression(name);
        while (!match(TokenType.RPAREN)) {
            function.addArgument(expression());
            match(TokenType.COMMA);
        }
        return function;
    }

    private Expression expression() {
        return logicalNot();
    }

    private Expression logicalNot() {

        if (match(TokenType.NOT)) {
            return new UnaryExpression(UnaryExpression.Operation.NOT, logicalOr());
        }
        return logicalOr();
    }


    private Expression logicalOr() {
        Expression result = logicalAnd();

        while (true) {
            if (match(TokenType.BARBAR)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.OR, result, logicalAnd());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression logicalAnd() {
        Expression result = equality();

        while (true) {
            if (match(TokenType.AMPAMP)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.AND, result, equality());
                continue;
            }
            break;
        }

        return result;
    }


    private Expression equality() {
        Expression result = conditional();

        if (match(TokenType.EQEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditional());
        }
        if (match(TokenType.EXCLEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditional());
        }

        return result;
    }

    private Expression conditional() {
        Expression result = additive();

        while (true) {
            if (match(TokenType.LT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, additive());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, additive());
                continue;
            }
            if (match(TokenType.GT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, additive());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, additive());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression additive() {
        Expression result = multiplicative();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression multiplicative() {
        Expression result = unary();

        while (true) {
            // 2 * 6 / 3
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression(UnaryExpression.Operation.MINUS, primary());
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        return primary();
    }

    private Expression primary() {
        final Token current = get(0);
        if (match(TokenType.NUMBER)) {
            return new ValueExpression(Double.parseDouble(current.getText()));
        }
        if (get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.LPAREN) {
            return function();
        }
        if (match(TokenType.WORD)) {
            if (match(TokenType.LBRACKET)) {
                return textBrackets(new VariableExpression(current.getText()));
            }
            return new VariableExpression(current.getText());
        }
        if (match(TokenType.TEXT)) {
            if (match(TokenType.LBRACKET)) {
                return textBrackets(new ValueExpression(current.getText()));
            }
            return new ValueExpression(current.getText());
        }
        if (match(TokenType.LPAREN)) {
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }

        throw new RuntimeException("Неизвестное выражение");
    }



    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) throw new RuntimeException("Токен " + current + " не соответствует " + type);
        pos++;
        return current;
    }

    private boolean match(TokenType type) {
        final Token current = get(0);
        if (type != current.getType()) return false;
        pos++;
        return true;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}