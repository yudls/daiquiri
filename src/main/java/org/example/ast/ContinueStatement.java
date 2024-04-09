package org.example.ast;

public class ContinueStatement extends RuntimeException implements Statement{
    @Override
    public String execute() {
        throw this;
    }

    @Override
    public String toString() {
        return "Продожить";
    }
}