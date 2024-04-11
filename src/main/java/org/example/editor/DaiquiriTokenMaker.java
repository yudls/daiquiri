package org.example.editor;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;

import javax.swing.text.Segment;

public class DaiquiriTokenMaker extends AbstractTokenMaker {

    private static final String[] KEYWORDS = {
            "Вывод", "вывод",
            "Если", "если",
            "Иначе", "иначе",
            "Пока", "пока",
            "Делать", "делать",
            "Для", "для",
            "Остановить", "остановить",
            "Продолжить", "продолжить",
            "не"
    };

    @Override
    public Token getTokenList(Segment text, int startTokenType, int startOffset) {
        resetTokenList();

        System.out.println("getTokenList");

        int currentTokenType = startTokenType;
        int currentTokenStart = startOffset;
        int end = text.offset + text.count;

        while (currentTokenStart < end) {
            char currentChar = text.array[currentTokenStart - text.offset];
            if (Character.isLetter(currentChar) || currentChar == '_') {
                int endOffset = currentTokenStart + 1;
                while (endOffset < end && (Character.isLetterOrDigit(text.array[endOffset - text.offset]) || text.array[endOffset - text.offset] == '_')) {
                    endOffset++;
                }
                String word = new String(text.array, currentTokenStart - text.offset, endOffset - currentTokenStart);
                if (isKeyword(word)) {
                    addToken(text, currentTokenStart, endOffset - 1, Token.RESERVED_WORD, startOffset);
                } else {
                    addToken(text, currentTokenStart, endOffset - 1, Token.IDENTIFIER, startOffset);
                }
                currentTokenStart = endOffset;
            } else {
                currentTokenStart++;
            }
        }
        return firstToken;
    }

    private boolean isKeyword(String word) {
        for (String keyword : KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TokenMap getWordsToHighlight() {
        TokenMap tokenMap = new TokenMap();
        for (String keyword : KEYWORDS) {
            tokenMap.put(keyword, Token.RESERVED_WORD);
        }
        return tokenMap;
    }
}
