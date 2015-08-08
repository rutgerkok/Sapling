package nl.rutgerkok.sapling.gui;

import java.util.Objects;

import javax.swing.BoundedRangeModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import nl.rutgerkok.hammer.util.Progress;
import nl.rutgerkok.sapling.ProgressUpdater;

/**
 * Updates a visual progress bar.
 *
 */
final class GuiProgressUpdater implements ProgressUpdater {

    private static final int PRECISION = 10000;

    private final BoundedRangeModel progressBar;

    public GuiProgressUpdater(BoundedRangeModel progressBar) {
        this.progressBar = Objects.requireNonNull(progressBar);
        progressBar.setMinimum(0);
        progressBar.setMaximum(PRECISION);
    }

    @Override
    public void update(Progress progress) {
        final double fraction = progress.getFraction();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setValue((int) (fraction * PRECISION));
            }
        });
    }

    @Override
    public void complete() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setValue(progressBar.getMaximum());

                JOptionPane.showMessageDialog(null, "Conversion completed!");
            }
        });
    }

}
