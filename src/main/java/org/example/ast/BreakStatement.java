package org.example.ast;

public class BreakStatement extends RuntimeException implements Statement{
    @Override
    public String execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "Остановить";
    }
}
