package view.gui;

/**
 * Represent an object that can be colored from its name.
 */
@FunctionalInterface
public interface Colorable {
    /**
     * @return the name of the colorable object.
     */
    String getName();
}
