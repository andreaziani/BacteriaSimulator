package model.bacteria;

/**
 * Represent a Species of bacteria. It defines a common behavior between its members.
 */
public interface Species {
    /**
     * @return the name of the Species.
     */
    String getName();
    /**
     * @return the behavior of the bacteria of this species.
     */
    Behavior getBehavior();
}
