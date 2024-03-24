package org.example.editor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class DocumentationPanel extends JFrame {
    private JPanel pagesPanel;

    public DocumentationPanel(JFrame parent) {
        setTitle("Документация");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);

        pagesPanel = new JPanel();
        pagesPanel.setLayout(new BoxLayout(pagesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pagesPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        try {
            // Получаем поток ресурса из файла внутри директории resources
            InputStream inputStream = DocumentationPanel.class.getResourceAsStream("/Documentation Daiquiri Language.pdf");
            PDDocument document = PDDocument.load(inputStream);
            PDFRenderer renderer = new PDFRenderer(document);

            // Рендерим и добавляем каждую страницу в панель
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                Image img = renderer.renderImageWithDPI(pageIndex, 100);
                JLabel pageLabel = new JLabel(new ImageIcon(img));
                pagesPanel.add(pageLabel);
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}