package utils;

/**
 * Log utility.
 */
public final class Logger {
    private static final Logger SINGLETON = new Logger();

    private Logger() {
    };

    /**
     * Return the reference to the logger.
     * @return Log 
     */
    public static Logger getInstance() {
        return SINGLETON;
    }

    /**
     * Log a message.
     * @param src the source from which the log is generate
     * @param msg the message that have to be logged
     */
    public void info(final String src, final String msg) {
        System.out.println("[" + src + "] : " + msg);
    }
}
