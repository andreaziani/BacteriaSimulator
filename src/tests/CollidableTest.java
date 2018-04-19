package tests;

import model.simulator.Collidable;

/**
 * Dummy class for collision testing.
 *
 */
public class CollidableTest implements Collidable {
    private final double radius;
    /**
     * Construct a Collidable object form just its radius.
     * @param r The radius value
     */
    public CollidableTest(final double r) {
        this.radius = r;
    }

    @Override
    public double getRadius() {
        return this.radius;
    }

}
