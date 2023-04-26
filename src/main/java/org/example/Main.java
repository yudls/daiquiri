package org.example;

import org.example.parser.Lexer;
import org.example.parser.Parser;
import org.example.parser.Token;
import org.example.parser.ast.Expression;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        // Press Shift+F9 to start debugging your code. We have set one breakpoint
        // for you, but you can always add more by pressing Ctrl+F8.
        final String input1 = "2 + 5";
        final String input2 = "2 + 2 * 2";
        final List<Token> tokens = new Lexer(input2).tokenize();
        for (Token token : tokens)
            System.out.println(token.toString());
        final List<Expression> expressions = new Parser(tokens).parse();
        for (Expression expr : expressions)
            System.out.println(expr + " = " + expr.eval());
    }
}