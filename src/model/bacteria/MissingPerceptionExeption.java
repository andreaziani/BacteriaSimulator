package model.bacteria;

/**
 * Exeption signaling the absence of a Perception for a Bacteria when asked to
 * choose an Action.
 */
public class MissingPerceptionExeption extends RuntimeException {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 1916327184605950953L;

    /**
     * Construct a new MissingPerceptionExeption with a given error message.
     * 
     * @param message
     *            the error message.
     */
    public MissingPerceptionExeption(final String message) {
        super(message);
    }
}
