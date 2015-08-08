package nl.rutgerkok.sapling.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import nl.rutgerkok.sapling.ConversionReporter;
import nl.rutgerkok.sapling.ErrorLog;
import nl.rutgerkok.sapling.ProgressUpdater;
import nl.rutgerkok.sapling.Startup;

public final class Window {

    private final ProgressPanel progressPanel;
    private final OutputPanel outputPanel;
    private final StartButtonsPanel startButtonsPanel;

    public Window() {
        setNativeLookAndFeel();

        JFrame jFrame = new JFrame();
        jFrame.setTitle(Startup.APPLICATION_NAME + " " + Startup.APPLICATION_VERSION);
        jFrame.setSize(600, 200);
        jFrame.setMinimumSize(new Dimension(450, 150));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.add(startButtonsPanel = new StartButtonsPanel(), BorderLayout.NORTH);
        jFrame.add(outputPanel = new OutputPanel());
        jFrame.add(progressPanel = new ProgressPanel(), BorderLayout.SOUTH);

        changeBackground();
        wireUp();

        jFrame.setVisible(true);
    }

    private void wireUp() {
        ErrorLog errorLog = new GuiErrorLog(outputPanel);
        ProgressUpdater progressUpdater = new GuiProgressUpdater(progressPanel.getProgressModel());

        ConversionReporter reporter = new ConversionReporter(errorLog, progressUpdater);

        startButtonsPanel.setPocketToAnvilAction(new PocketToAnvilStartAction(reporter));
    }

    private void changeBackground() {
        if (System.getProperty("os.name").contains("Windows")) {
            // White background on Windows
            startButtonsPanel.setBackground(Color.WHITE);
            outputPanel.setBackground(Color.WHITE);
            progressPanel.setBackground(Color.WHITE);
        }
    }

    private void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
