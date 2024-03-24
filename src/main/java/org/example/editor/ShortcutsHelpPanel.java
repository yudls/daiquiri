package org.example.editor;

import javax.swing.*;
import java.awt.*;

public class ShortcutsHelpPanel extends JDialog{
    public ShortcutsHelpPanel(JFrame parent) {
        super(parent, "Подсказки по шорткатам", true);

        // Создаем текстовую область с подсказками
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(
                "Шорткаты:\n" +
                        "Ctrl + X - Вырезать\n" +
                        "Ctrl + C - Копировать\n" +
                        "Ctrl + V - Вставить\n" +
                        // Добавьте здесь другие шорткаты, если необходимо
                        "\nИ другие...");

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
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
