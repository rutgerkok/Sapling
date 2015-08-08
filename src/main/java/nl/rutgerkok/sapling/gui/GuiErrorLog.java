package nl.rutgerkok.sapling.gui;

import java.awt.Color;
import java.util.Objects;

import javax.swing.SwingUtilities;

import nl.rutgerkok.sapling.ErrorLog;

/**
 * Logs error messages to a {@link OutputPanel}.
 *
 */
final class GuiErrorLog implements ErrorLog {

    private final OutputPanel outputPanel;

    @Override
    public void log(final String message, final Throwable cause) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputPanel.appendLine(Color.RED, message);
                outputPanel.appendLine(Color.RED, cause.getClass().getSimpleName().replace("Exception", "") + ": " + cause.getMessage());
            }
        });

    }

    public GuiErrorLog(OutputPanel outputPanel) {
        this.outputPanel = Objects.requireNonNull(outputPanel);
    }

    @Override
    public void log(final String string) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputPanel.appendLine(Color.RED, string);
            }
        });
    }

}
