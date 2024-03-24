package org.example.editor;

import org.example.parser.Interpreter;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.undo.UndoManager;
import javax.swing.text.*;

public class Editor extends JFrame implements ActionListener {
    // Frame
    JFrame f;
    // Text component
    JTextArea t;
    UndoManager undoManager;

    // Constructor
    public Editor() throws BadLocationException {
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

        // Create buttons for menu
        JMenu menu_file = new JMenu("Файл");
        JMenu menu_help = new JMenu("Помощь");

        // Create menu items
        JMenuItem new_menu_file_item = new JMenuItem("Новый");
        JMenuItem open_menu_file_item = new JMenuItem("Открыть");
        JMenuItem save_menu_file_item = new JMenuItem("Сохранить");
        JMenuItem print_menu_file_item = new JMenuItem("Печать");

        JMenuItem shortcuts_menu_help_item = new JMenuItem("Сочетания клавиш");
        JMenuItem documentation_menu_help_item = new JMenuItem("Документация");
        JMenuItem item_run = new JMenuItem("Запустить");


        // Add action listener
        new_menu_file_item.addActionListener(this);
        open_menu_file_item.addActionListener(this);
        save_menu_file_item.addActionListener(this);
        print_menu_file_item.addActionListener(this);

        shortcuts_menu_help_item.addActionListener(this);
        documentation_menu_help_item.addActionListener(this);

        item_run.addActionListener(this);

        menu_file.add(new_menu_file_item);
        menu_file.add(open_menu_file_item);
        menu_file.add(save_menu_file_item);
        menu_file.add(print_menu_file_item);

        menu_help.add(shortcuts_menu_help_item);
        menu_help.add(documentation_menu_help_item);

        mb.add(menu_file);
        mb.add(menu_help);
        mb.add(item_run);

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
                deleteLineFunction();
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

        // Ctrl + S - сохранение файла
        KeyStroke saveKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(saveKeyStroke, "saveAction");
        // Добавление действия в ActionMap для выполнения функции "Save"
        actionMap.put("saveAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                saveFunction();
            }
        });

        // Ctrl + N - Новый файл
        KeyStroke newKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(newKeyStroke, "newAction");
        // Добавление действия в ActionMap для выполнения функции "New"
        actionMap.put("newAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                newFunction();
            }
        });

        // Ctrl + O - Открыть файл
        KeyStroke openKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(openKeyStroke, "openAction");
        // Добавление действия в ActionMap для выполнения функции "Open"
        actionMap.put("openAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                openFunction();
            }
        });

        // Ctrl + P - Печать файла
        KeyStroke printKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(printKeyStroke, "printAction");
        // Добавление действия в ActionMap для выполнения функции "Print"
        actionMap.put("printAction", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                printFunction();
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
            saveFunction();
        } else if (s.equals("Печать")) {
            printFunction();
        } else if (s.equals("Открыть")) {
            openFunction();
        } else if (s.equals("Новый")) {
            newFunction();
        } else if (s.equals("Запустить")) {
            runFunction();
        } else if (s.equals("Сочетания клавиш")) {
            shortcutsFunction();
        } else if (s.equals("Документация")) {
            documentationFunction();
        }
    }

    private void newFunction() {
        // Создаем диалоговое окно с сообщением и кнопками "Сохранить файл" и "Не сохранять"
        int result = JOptionPane.showConfirmDialog(f, "Хотите сохранить изменения?", "Подтверждение сохранения", JOptionPane.YES_NO_OPTION);

        // Проверяем, какую кнопку нажал пользователь
        if (result == JOptionPane.YES_OPTION) {
            saveFunction();
        }
        t.setText("");
    }

    private void openFunction() {
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
    }

    private void saveFunction() {
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
    }

    private void printFunction() {
        try {
            // print the file
            t.print();
        } catch (Exception evt) {
            JOptionPane.showMessageDialog(f, evt.getMessage());
        }
    }

    private void shortcutsFunction() {
        ShortcutsHelpPanel dialog = new ShortcutsHelpPanel(f);
        dialog.setVisible(true);
    }

    private void documentationFunction() {
        DocumentationPanel documentationPanel = new DocumentationPanel(f);
        documentationPanel.setVisible(true);
    }

    private void deleteLineFunction() {
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

    private void runFunction() {
        String program_output;
        try {
            String program_text = t.getText();
            Interpreter interpreter = new Interpreter(program_text);
            program_output = interpreter.execute();
        } catch (Exception exception) {
            program_output = exception.toString();
        }
        OutputPanel outputPanel = new OutputPanel(f, program_output);
        outputPanel.setVisible(true);
    }

}


