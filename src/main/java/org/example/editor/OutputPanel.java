package org.example.editor;

import javax.swing.*;
import java.awt.*;


public class OutputPanel extends JDialog {
    public OutputPanel(JFrame parent, String output) {
        super(parent, "Вывод программы", true);

        // Создаем панель вывода
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(output);

        // Создаем панель с текстовой областью и добавляем ее на диалоговое окно
        JScrollPane scrollPane = new JScrollPane(textArea);
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
}

