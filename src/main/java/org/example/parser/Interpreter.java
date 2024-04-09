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
            System.out.println("Lexer:");
            final List<Token> tokens = new Lexer(program_text).tokenize();
            for (Token token : tokens)
                System.out.println(token.toString());

            // Parser
            System.out.println("\nParser");
            final Statement program_statement = new Parser(tokens).parse();
            System.out.println(program_statement);

            result = program_statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Программа была прервана из-за ошибки\n" + e);
        }
        return result;
    }
}
