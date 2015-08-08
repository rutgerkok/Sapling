package nl.rutgerkok.sapling.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Two buttons to start conversion in either direction.
 */
final class StartButtonsPanel extends JPanel {

    private static final long serialVersionUID = -7916006701539400731L;
    private final JButton peToPc;
    private final JButton pcToPe;

    public StartButtonsPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        peToPc = new JButton("Pocket Edition to Java PC Edition");
        peToPc.setEnabled(false);
        add(peToPc);

        pcToPe = new JButton("Java PC Edition to Pocket Edition");
        pcToPe.setEnabled(false);
        add(pcToPe);
    }

    /**
     * Sets the action when the Pocket to Anvil conversion button is pressed.
     * 
     * @param action
     *            The action.
     */
    void setPocketToAnvilAction(ActionListener action) {
        peToPc.addActionListener(Objects.requireNonNull(action));
        peToPc.setEnabled(true);
    }

}
