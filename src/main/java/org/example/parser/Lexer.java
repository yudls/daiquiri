package org.example.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Lexer {
    private final String input;
    private final int length;
    private final List<Token> tokens;
    private int pos;
    private static final String OPERATOR_CHARS = "+-*/=<>(){}!&|";
    private static final Map<String, TokenType> OPERATORS;
    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);

        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
        OPERATORS.put("{", TokenType.LBRACE);
        OPERATORS.put("}", TokenType.RBRACE);
        OPERATORS.put("=", TokenType.EQ);
        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);

        OPERATORS.put("!", TokenType.EXCL);
        OPERATORS.put("&", TokenType.AMP);
        OPERATORS.put("|", TokenType.BAR);

        OPERATORS.put("==", TokenType.EQEQ);
        OPERATORS.put("!=", TokenType.EXCLEQ);
        OPERATORS.put("<=", TokenType.LTEQ);
        OPERATORS.put(">=", TokenType.GTEQ);

        OPERATORS.put("&&", TokenType.AMPAMP);
        OPERATORS.put("||", TokenType.BARBAR);

    }

    public Lexer(String input) {
        this.input = input;
        length = input.length();
        tokens = new ArrayList<>();
    }
    public List<Token> tokenize() {
        while (pos < length) {
            final char current = peek(0);
            if (Character.isDigit(current)) tokenizeNumber();
            else if (current == '"') tokenizeText();
            else if (Character.isLetter(current)) tokenizeWord();
            else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                // whitespaces
                next();
            }

        }
        return tokens;
    }
    private void tokenizeNumber() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '.') {
                if (buffer.indexOf(".") != -1)
                    throw new RuntimeException("Некорректное число");
            } else if (!Character.isDigit(current)) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        addToken(TokenType.NUMBER, buffer.toString());
    }
    private void tokenizeOperator() {
        char current = peek(0);
        if (current == '/') {
            if (peek(1) == '/') {
                next();
                next();
                tokenizeComment();
                return;
            } else if (peek(1) == '*') {
                next();
                next();
                tokenizeMultilineComment();
                return;
            }
        }
        final StringBuilder buffer = new StringBuilder();
        while (true) {
            final String text = buffer.toString();
            if (!OPERATORS.containsKey(text + current) && !text.isEmpty()) {
                addToken(OPERATORS.get(text));
                return;
            }
            buffer.append(current);
            current = next();
        }
    }
    private void tokenizeWord() {
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (!Character.isLetterOrDigit(current) && (current != '_') && (current != '$')) {
                break;
            }
            buffer.append(current);
            current = next();
        }
        String word = buffer.toString();
        switch (word) {
            case "Вывод":
            case "вывод": {
                addToken(TokenType.PRINT);
                break;
            }
            case "Если" :
            case "если" : {
                addToken(TokenType.IF);
                break;
            }
            case "Иначе" :
            case "иначе": {
                addToken(TokenType.ELSE);
                break;
            }
            default: {
                addToken(TokenType.WORD, word);
                break;
            }
        }
    }
    private void tokenizeText() {
        next(); // skip "
        final StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true) {
            if (current == '\\') {
                current = next();
                switch (current) {
                    case '"': {
                        current = next();
                        buffer.append('"');
                        continue;
                    }
                    case 'n':
                    case 'н':{
                        current = next();
                        buffer.append('\n');
                        continue;
                    }
                    case 't':
                    case 'т': {
                        current = next();
                        buffer.append('\t');
                        continue;
                    }
                }
                buffer.append('\\');
                continue;
            }
            if (current == '"') break;
            buffer.append(current);
            current = next();
        }
        next(); // skip "

        addToken(TokenType.TEXT, buffer.toString());
    }
    private void tokenizeComment() {
        char current = peek(0);
        while ("\r\n\0".indexOf(current) == -1) {
            current = next();
        }
    }
    private void tokenizeMultilineComment() {
        char current = peek(0);
        while (true) {
            if (current == '\0') throw new RuntimeException("Пропуск закрывающего тэга");
            if (current == '*' && peek(1) == '/')
                break;
            current = next();
        }
        next(); // *
        next(); // /
    }
    private char next() {
        pos++;
        return peek(0);
    }
    private char peek(int relativePosition) { // peek(0) - текущий символ
        final int position = pos + relativePosition;
        if (position >= length) return '\0';
        return input.charAt(position);
    }
    private void addToken(TokenType type) {
        addToken(type, "");
    }
    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text));
    }

}
