package utils;

/**
 * Log utility.
 */
public final class Log {
    private static final Log SINGLETON = new Log();

    private Log() {
    };

    /**
     * Return the reference to the logger.
     * @return Log 
     */
    public static Log getLog() {
        return SINGLETON;
    }

    /**
     * Log a message.
     * @param s the string containing the message
     */
    public void info(final String s) {
        System.err.println(s);
    }
}
