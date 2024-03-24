package org.example.ast;

public class PrintStatement implements Statement {
    private final Expression expression;
    private String result;
    public PrintStatement(Expression expression) {
        this.expression = expression;
    }
    public String getResult() {
        return result;
    }
    @Override
    public String execute() {
        return  expression.eval().asString();
//        System.out.println(result);
    }

    @Override
    public String toString() {
        return "Вывод " + expression;
    }
}
