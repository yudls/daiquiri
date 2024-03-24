package org.example.ast;

public final class WhileStatement implements Statement{
    private final Expression condition;
    private final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        while (condition.eval().asNumber() != 0) {
            result.append(statement.execute());
        }
        return result.toString();
    }

    @Override
    public String toString(){
        return "Пока { условие " + condition + ", выполнить " + statement + '}';
    }

}
