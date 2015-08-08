package nl.rutgerkok.sapling;

import java.util.concurrent.atomic.AtomicInteger;

import nl.rutgerkok.hammer.util.Progress;

/**
 * Progress updater that prints the progress to {@link System#out}.
 *
 */
final class SystemOutProgressUpdater implements ProgressUpdater {

    private final AtomicInteger runningPercentage = new AtomicInteger(-1);

    @Override
    public void update(Progress progress) {
        int percentage = progress.getIntPercentage();
        int oldPercentage = runningPercentage.getAndSet(percentage);
        if (oldPercentage != percentage) {
            // New percentage!
            System.out.println(progress.getIntPercentage() + "%");
        }
    }

    @Override
    public void complete() {
        System.out.println("Conversion completed!");
    }

}
