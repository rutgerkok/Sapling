package nl.rutgerkok.sapling.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Contains one big text field for output.
 *
 */
final class OutputPanel extends JPanel {

    private static final long serialVersionUID = -3273989401381261064L;
    private final JTextPane textArea;

    OutputPanel() {
        // Let content fill whole area
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));

        textArea = new JTextPane();
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
    }

    /**
     * Appends a line of text to the output panel.
     * @param color
     *            Color of the text.
     * @param line
     *            The line of text.
     */
    void appendLine(Color color, String line) {
        try {
            Document document = textArea.getDocument();
            int endOffset = document.getEndPosition().getOffset();
            document.insertString(endOffset - 1, line + "\n", getColoredText(color));
        } catch (BadLocationException e) {
            // Failed to insert string, should never happen
            throw new AssertionError(e);
        }
    }

    private AttributeSet getColoredText(Color color) {
        StyleContext defaultStyles = StyleContext.getDefaultStyleContext();
        return defaultStyles.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
    }

}
