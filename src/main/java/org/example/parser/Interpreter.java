package org.example.parser;

import org.example.ast.BlockStatement;
import org.example.ast.Statement;
import org.example.lib.StringValue;
import org.example.lib.Value;
import org.example.lib.Variables;

import java.util.ArrayList;
import java.util.List;

public final class Interpreter {
    private final Statement program;

    public Interpreter(Statement program) {
        this.program = program;
    }
    public String execute() {
        String result;
        try {
            result = program.execute();
        } catch (Exception e) {
            throw new RuntimeException("The program was aborted due to an error");
        }
        return result;
    }
}
