package org.example.parser;

public enum TokenType {
    NUMBER,
    WORD,
    TEXT,

    // Keywords
    PRINT,
    IF,
    ELSE,
    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQ,
    EQEQ,
    EXCL,
    EXCLEQ,
    LT,
    LTEQ,
    GT,
    GTEQ,

    BAR,
    BARBAR,
    AMP,
    AMPAMP,

    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,

    EOF
}
