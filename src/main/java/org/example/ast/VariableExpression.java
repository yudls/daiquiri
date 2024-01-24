package org.example.ast;

import org.example.lib.Value;
import org.example.lib.Variables;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variables.isExists(name))
            throw new RuntimeException("Constant doesn't exist");
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s %f", name, Variables.get(name).asNumber());
    }
}
