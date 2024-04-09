package org.example.ast;

public final class ForStatement implements Statement{
    private final Statement initialization;
    private final Expression termination;
    private final Statement increment;
    private final Statement block;

    public  ForStatement(Statement initialization, Expression termination, Statement increment, Statement block) {
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;
        this.block = block;

    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        for (initialization.execute(); termination.eval().asNumber() != 0; increment.execute()) {
            try {
                result.append(block.execute());
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement cs) {
                // continue;
            }

        }
        return result.toString();
    }

    @Override
    public String toString(){
        return "Для " + initialization + " " + termination + " " + increment + " выполнить " + block;
    }

}

