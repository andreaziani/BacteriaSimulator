package view.model.food;



import view.Radius;
import view.gui.Colorable;

/**
 * Representation of a provision in the view.
 * A provision is a representation of a food in the gui.
 *
 */
public interface ViewProvision extends Colorable {
    /**
     * @return the radius of the bacteria.
     */
    Radius getRadius();
}
