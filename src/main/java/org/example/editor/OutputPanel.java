package org.example.editor;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;


public class OutputPanel extends JDialog {
    private JTextArea outputTextArea;
    public OutputPanel(JFrame parent) {
        super(parent, "Вывод программы", true);

        // Создаем панель вывода
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        // Создаем действие копирования и добавляем его к текстовой области
        InputMap inputMap = outputTextArea.getInputMap(JComponent.WHEN_FOCUSED);
        KeyStroke copyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(copyKeyStroke, DefaultEditorKit.copyAction);


        // Создаем панель с текстовой областью и добавляем ее на диалоговое окно
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        getContentPane().add(scrollPane);

        // Создаем кнопку "Закрыть"
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose()); // Закрываем окно при нажатии на кнопку

        // Создаем панель с кнопкой и добавляем ее на диалоговое окно
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Настройка размеров окна и его отображение
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void appendOutput(String output) {
        outputTextArea.append(output);
    }


}







