package org.example.parser;

public final class Token {
    private TokenType type;
    private String text;

    public Token(TokenType type, String text) {
        this.text = text;
        this.type = type;
    }
    public Token() {
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " " + text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
