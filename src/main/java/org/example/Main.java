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
        Editor e = new Editor();
    }
}
