package view.model.food;

import java.awt.Color;

import view.Radius;

/**
 * 
 * Implementation of ViewProvision.
 *
 */
public class ViewProvisionImpl implements ViewProvision {
    private final Radius radius;
    private final ViewFood food;
    /**
     * 
     * @param radius the radius of the food in the view.
     * @param food the ViewFood to represent.
     */
    public ViewProvisionImpl(final Radius radius, final ViewFood food) {
        this.radius = radius;
        this.food = food;
    }

    @Override
    public Radius getRadius() {
        return this.radius;
    }

    @Override
    public Color getColor() {
        return this.food.getColor();
    }

}