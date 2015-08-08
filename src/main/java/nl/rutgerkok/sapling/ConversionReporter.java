package nl.rutgerkok.sapling;

import java.util.Objects;

/**
 * Immutable class that holds the objects for reporting the converter state.
 *
 */
public final class ConversionReporter {

    private final ErrorLog errorLog;
    private final ProgressUpdater progressUpdater;

    public ConversionReporter(ErrorLog errorLog, ProgressUpdater progressUpdater) {
        this.errorLog = Objects.requireNonNull(errorLog, "errorLog");
        this.progressUpdater = Objects.requireNonNull(progressUpdater, "progressUpdater");
    }

    /**
     * Gets the error logger.
     * 
     * @return The error logger.
     */
    public ErrorLog getErrorLog() {
        return errorLog;
    }

    /**
     * Gets the progress updater.
     * 
     * @return The progress updater.
     */
    public ProgressUpdater getProgressUpdater() {
        return progressUpdater;
    }
}
