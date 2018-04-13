package view.model.food;



import view.Radius;
import view.gui.Colorable;

/**
 * Representation of a provision in the view.
 * A provision is a "graphical" representation of a food in the GUI.
 *
 */
public interface ViewProvision extends Colorable {
    /**
     * @return the radius of the bacteria.
     */
    Radius getRadius();
}
