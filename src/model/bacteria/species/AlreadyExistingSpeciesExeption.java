package model.bacteria.species;

/**
 * An exception signaling an insertion of a Species with repeated name.
 */
public class AlreadyExistingSpeciesExeption extends RuntimeException {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 7332058960298691203L;

    /**
     * Construct a new AlreadyExistingSpeciesExeption with a default error message.
     */
    public AlreadyExistingSpeciesExeption() {
        super("The species already exists");
    }
}
