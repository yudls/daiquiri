package org.example.parser;

public enum TokenType {
    // Types
    NUMBER,
    WORD,
    TEXT,

    // Keywords
    PRINT,
    IF,
    ELSE,
    WHILE,
    FOR,

    // Operations
    PLUS,
    MINUS,
    STAR,
    SLASH,

    // Assignment
    EQ,

    // Logical
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

    // Symbols
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    SEMICOLON, // Точка с запятой

    EOF
}
