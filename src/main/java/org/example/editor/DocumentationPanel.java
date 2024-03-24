package org.example.editor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class DocumentationPanel extends JFrame{
    private JTextPane textPane;

    public DocumentationPanel() {
        setTitle("Документация");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        textPane = new JTextPane();
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        try {
            // Получаем поток ресурса из файла внутри директории resources
            InputStream inputStream = DocumentationPanel.class.getResourceAsStream("/Documentation Daiquiri Language.pdf");
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);
            textPane.setText(pdfText);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
