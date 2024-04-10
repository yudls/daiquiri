package org.example.ast;

public class FunctionStatement implements Statement{
    private final FunctionalExpression function;

    public FunctionStatement(FunctionalExpression function) {
        this.function = function;
    }

    @Override
    public String execute() {
        function.eval();
        return "";
    }

    @Override
    public String toString() {
        return function.toString();
    }
}
