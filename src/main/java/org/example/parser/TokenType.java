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
    DO,
    BREAK,
    CONTINUE,
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
    LBRACKET,
    RBRACKET,
    LBRACE,
    RBRACE,
    COLON, // Двоеточие
    SEMICOLON, // Точка с запятой
    COMMA, // Запятая

    EOF
}
