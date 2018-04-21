package model.bacteria;

/**
 * An exception representing a failure in spending a required amount of energy.
 * Enough
 */
public class NotEnoughEnergyException extends RuntimeException {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 1175026953692025593L;

    /**
     * Construct a new NotEnoughEnergyException with a given error message.
     * 
     * @param message
     *            the error message.
     */
    public NotEnoughEnergyException(final String message) {
        super(message);
    }
}
