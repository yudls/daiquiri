package org.example;// Java Program to create a text editor using java

import org.example.ast.Statement;
import org.example.parser.Interpreter;
import org.example.parser.Lexer;
import org.example.parser.Parser;
import org.example.parser.Token;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.plaf.metal.*;
import javax.swing.undo.UndoManager;
import javax.swing.text.*;

class Editor extends JFrame implements ActionListener {
    // Frame
    JFrame f;
    // Text component
    JTextArea t;
    UndoManager undoManager;

    // Constructor
    Editor() throws BadLocationException {
        // Create a frame
        f = new JFrame("DaiquiriEditor");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        // Text component
        t = new JTextArea();
        undoManager = new UndoManager();
        t.getDocument().addUndoableEditListener(undoManager);

        // Create a menubar
        JMenuBar mb = new JMenuBar();

        // Create amenu for menu
        JMenu m1 = new JMenu("Файл");

        // Create menu items
        JMenuItem mi1 = new JMenuItem("Новый");
        JMenuItem mi2 = new JMenuItem("Открыть");
        JMenuItem mi3 = new JMenuItem("Сохранить");
        JMenuItem mi9 = new JMenuItem("Печать");

        // Add action listener
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);


        JMenuItem mr = new JMenuItem("Запустить");
        mr.addActionListener(this);

        mb.add(m1);
        mb.add(mr);

        // Настройка сочетаний клавиш
        InputMap inputMap = t.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = t.getActionMap();

        // Ctrl + X для вырезания
        KeyStroke cutKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(cutKeyStroke, DefaultEditorKit.cutAction);

        // Ctrl + C для копирования
        KeyStroke copyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(copyKeyStroke, DefaultEditorKit.copyAction);

        // Ctrl + V для вставки
        KeyStroke pasteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(pasteKeyStroke, DefaultEditorKit.pasteAction);

        // Ctrl + Backspace для быстрого удаления текста
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(deleteKeyStroke, "deleteAction");

        // Добавление действия в ActionMap для быстрого удаления текста
        actionMap.put("deleteAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String text = t.getText();
                int caretPosition = t.getCaretPosition();
                int start = caretPosition;
                int end = caretPosition;

                // Находим начало строки
                while (start > 0 && text.charAt(start - 1) != '\n') {
                    start--;
                }

                if (caretPosition == start) {
                    t.replaceRange("", caretPosition - 1, caretPosition);
                    t.setCaretPosition(start - 1);
                }

                // Находим конец строки
                while (end < text.length() && text.charAt(end) != '\n') {
                    end++;
                }

                // Удаляем строку
                t.replaceRange("", start, end);
            }
        });

        // Ctrl + Z для отмены действия
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(undoKeyStroke, "undoAction");

        // Добавление действия в ActionMap для отмены действия
        actionMap.put("undoAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        // Ctrl + R - запуск кода
        KeyStroke runKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(runKeyStroke, "runAction");

        // Добавление действия в ActionMap для выполнения функции "Run"
        actionMap.put("runAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                runFunction();
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setJMenuBar(mb);
        f.add(t);
        f.setSize(800, 600);
        f.show();

    }

    // If a button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();


        if (s.equals("Сохранить")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsSaveDialog function to show the save dialog
            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // Create a file writer
                    FileWriter wr = new FileWriter(fi, false);

                    // Create buffered writer to write
                    BufferedWriter w = new BufferedWriter(wr);

                    // Write
                    w.write(t.getText());

                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                JOptionPane.showMessageDialog(f, "Отмена операции");
        } else if (s.equals("Печать")) {
            try {
                // print the file
                t.print();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        } else if (s.equals("Открыть")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // If the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // String
                    String s1 = "", sl = "";

                    // File reader
                    FileReader fr = new FileReader(fi);

                    // Buffered reader
                    BufferedReader br = new BufferedReader(fr);

                    // Initialize sl
                    sl = br.readLine();

                    // Take the input from the file
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }

                    // Set the text
                    t.setText(sl);
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                JOptionPane.showMessageDialog(f, "Отмена операции");
        } else if (s.equals("Новый")) {
            t.setText("");

        } else if (s.equals("Запустить")) {
            runFunction();
        }
    }

    private void runFunction() {
        String programOuput = executeProgram();
        createOutputPanel(programOuput);
    }

    public String executeProgram() {
        String output;
        try {
            String input = t.getText();
            // Run program
            final List<Token> tokens = new Lexer(input).tokenize();
            System.out.println("Lexer:");
            for (Token token : tokens)
                System.out.println(token.toString());
            final Statement program = new Parser(tokens).parse();
            System.out.println("\nParser");
            System.out.println(program.toString());
            Interpreter interpreter = new Interpreter(program);
            output = interpreter.execute();
        } catch (Exception exception) {
            output = exception.toString();
        }
        return output;
    }

    public void addTextToTextArea(JTextArea textArea, String text) {
        textArea.setText(text);
    }

    public void createOutputPanel(String text) {
        JDialog dialog = new JDialog();
        JTextArea textArea = new JTextArea();
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.pack();
        dialog.setSize(500, 300);
        dialog.setTitle("Output");
        dialog.setVisible(true);

        addTextToTextArea(textArea, text);
    }

}


