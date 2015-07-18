package nl.rutgerkok.sapling;

/**
 * Logs messages to {@link System#err}, one error per line.
 *
 */
final class SystemErrErrorLog implements ErrorLog {

    @Override
    public void log(String message, Throwable cause) {
        System.err.println(message);
        System.err.println(cause.getClass().getSimpleName().replace("Exception", "") + ": " + cause.getMessage());
    }

    @Override
    public void log(String string) {
        System.err.println(string);
    }

}
