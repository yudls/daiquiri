package org.example.ast;

public final class DoWhileStatement implements Statement{
    private final Expression condition;
    private final Statement statement;

    public DoWhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        do {
            try {
                result.append(statement.execute());
            } catch (BreakStatement breakStatement) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }
        }
        while (condition.eval().asNumber() != 0);
        return result.toString();
    }

    @Override
    public String toString(){
        return "Делать { выполнить " + statement +  "пока , условие" + condition +  '}';
    }

}

