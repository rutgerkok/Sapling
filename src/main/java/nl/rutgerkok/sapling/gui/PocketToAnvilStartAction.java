package nl.rutgerkok.sapling.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Objects;

import nl.rutgerkok.hammer.util.Consumer;
import nl.rutgerkok.sapling.ConversionReporter;
import nl.rutgerkok.sapling.Startup;
import nl.rutgerkok.sapling.gui.filechooser.FileChooser;

class PocketToAnvilStartAction implements ActionListener {

    private final ConversionReporter reporter;

    public PocketToAnvilStartAction(ConversionReporter reporter) {
        this.reporter = Objects.requireNonNull(reporter, "reporter");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileChooser.create().chooseFile(new Consumer<Path>() {

            @Override
            public void accept(Path levelDat) {
                if (levelDat == null) {
                    return;
                }
                startConversionAsync(levelDat);
            }
        });

    }

    /**
     * Creates a new thread that starts the conversion.
     * 
     * @param levelDat
     *            The world to convert.
     */
    private void startConversionAsync(final Path levelDat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Startup.init(reporter.getErrorLog(), reporter.getProgressUpdater(), levelDat);
            }
        }).start();
    }

}
