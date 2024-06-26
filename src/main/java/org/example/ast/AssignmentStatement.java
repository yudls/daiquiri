package org.example.ast;

import org.example.lib.Value;
import org.example.lib.Variables;

public final class AssignmentStatement implements Statement {

    private final String variable;
    private final Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        System.out.println("AssignmentStatement: Конструктор");
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public String execute() {
        final Value result = expression.eval();
        Variables.set(variable, result);
        System.out.println("AssignmentStatement: Установлена переменная:\t{" + variable + "}");
        return "";
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }
}