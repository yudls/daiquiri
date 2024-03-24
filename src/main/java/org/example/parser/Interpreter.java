package org.example.parser;
import org.example.ast.Statement;
import java.util.List;

public final class Interpreter {
    private final String program_text;

    public Interpreter(String program_text) {
        this.program_text = program_text;
    }

    // Run program
    public String execute() {
        String result;
        try {
            // Lexer
            final List<Token> tokens = new Lexer(program_text).tokenize();
            System.out.println("Lexer:");
            for (Token token : tokens)
                System.out.println(token.toString());

            // Parser
            final Statement program_statement = new Parser(tokens).parse();
            System.out.println("\nParser");
            System.out.println(program_statement.toString());

            result = program_statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Программа была прервана из-за ошибки\n" + e);
        }
        return result;
    }
}
