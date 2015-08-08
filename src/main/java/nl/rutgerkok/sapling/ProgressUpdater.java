package nl.rutgerkok.sapling;

import nl.rutgerkok.hammer.util.Progress;

/**
 * Used to track progress of an operation.
 *
 */
public interface ProgressUpdater {

    /**
     * Called when there's a progress update. Can be called from any thread.
     *
     * @param progress
     *            The new progress.
     */
    void update(Progress progress);

    /**
     * Called when the conversion process completed.
     */
    void complete();
}
