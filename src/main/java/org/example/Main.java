package org.example;


import org.example.ast.Statement;
import org.example.parser.Interpreter;
import org.example.parser.Lexer;
import org.example.parser.Parser;
import org.example.parser.Token;

import javax.swing.text.BadLocationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, BadLocationException {
//        String path = "/Users/grisha/IdeaProjects/daiquiri/src/main/java/org/example/program.txt";
//        String input = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
//        final List<Token> tokens = new Lexer(input).tokenize();
//            for (Token token : tokens)
//                System.out.println(token.toString());
//        final Statement program = new Parser(tokens).parse();
//            System.out.println(program.toString());
//        Interpreter interpreter = new Interpreter(program);
//        interpreter.execute();
        Editor e = new Editor();
    }
}