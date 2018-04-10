package utils;

/**
 * Log utility.
 */
public final class Logger {
    private static final Logger LOG = new Logger();

    private Logger() {
    };

    /**
     * Return the reference to the logger.
     * @return Log 
     */
    public static Logger getLog() {
        return LOG;
    }

    /**
     * Log a message.
     * @param s the string containing the message
     */
    public void info(final String s) {
        System.out.println(s);
    }
}
