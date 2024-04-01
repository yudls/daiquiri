package org.example.parser;
import org.example.ast.Statement;
import org.example.editor.OutputPanel;

import java.util.List;

public final class Interpreter {
    private final String program_text;
    private final OutputPanel outputPanel;

    public Interpreter(String program_text, OutputPanel outputPanel) {
        this.program_text = program_text;
        this.outputPanel = outputPanel;
    }


    // Run program
    public void execute() {

        String result;
        try {
            // Lexer
            final List<Token> tokens = new Lexer(program_text).tokenize();

            // Parser
            final List<Statement> program_statements = new Parser(tokens).parse();

            for (Statement statement: program_statements) {
                result = statement.execute();
                System.out.println("result");
                System.out.println(result);
                outputPanel.appendOutput(result);
            }

        } catch (Exception e) {
            outputPanel.appendOutput("Программа была прервана из-за ошибки\n" + e.toString());
        }

    }
}
