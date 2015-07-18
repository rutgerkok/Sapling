package nl.rutgerkok.sapling;

/**
 * Simple error logging interface.
 *
 */
public interface ErrorLog {

    /**
     * Logs an error along with the exception that caused it.
     * 
     * @param message
     *            Message, represents the context of the exception (don't repeat
     *            {@link Throwable#getMessage()}).
     * @param cause
     *            The exception that caused this error.
     */
    void log(String message, Throwable cause);

    /**
     * Logs an error.
     * 
     * @param string
     *            The error message.
     */
    void log(String string);
}
