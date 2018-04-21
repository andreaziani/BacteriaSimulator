package controller;
/**
 * Exception that represent a request to use a file with an inappropriate extension.
 */
public class IllegalExtensionException extends RuntimeException {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -6051567318193381015L;

    /**
     * Construct a new IllegalExtensionException with a given error message.
     * 
     * @param message
     *            the error message.
     */
    public IllegalExtensionException(final String message) {
        super(message);
    }
}
