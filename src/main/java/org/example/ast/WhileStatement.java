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
            try {
                result.append(statement.execute());
            } catch (BreakStatement breakStatement) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
        return result.toString();
    }

    @Override
    public String toString(){
        return "Пока { условие " + condition + ", выполнить " + statement + '}';
    }

}
