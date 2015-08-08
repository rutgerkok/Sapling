package nl.rutgerkok.sapling.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Panel with a progress bar.
 *
 */
final class ProgressPanel extends JPanel {

    private static final long serialVersionUID = -1264783954174285795L;

    private final JProgressBar bar;

    ProgressPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        add(bar = new JProgressBar());
    }

    /**
     * Gets the model of the progress bar.
     * 
     * @return The model.
     */
    public BoundedRangeModel getProgressModel() {
        return bar.getModel();
    }
}
