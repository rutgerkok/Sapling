package nl.rutgerkok.sapling;

/**
 * Simple error logging interface.
 *
 */
public interface ErrorLog {

    /**
     * Logs an error along with the exception that caused it. This method can be
     * called from any thread.
     *
     * @param message
     *            Message, represents the context of the exception (don't repeat
     *            {@link Throwable#getMessage()}).
     * @param cause
     *            The exception that caused this error.
     */
    void log(String message, Throwable cause);

    /**
     * Logs an error. This method can be called from any thread.
     *
     * @param string
     *            The error message.
     */
    void log(String string);
}
