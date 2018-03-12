package utils.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import model.Direction;
import model.Position;
import model.PositionImpl;
import utils.EnvGeometry;;

/**
 * Test class for the EnvGeometry class.
 *
 *
 */
public class TestGeometry {
    private static final double DELTA = 1e-5;
    private static final Position ORIGIN = new PositionImpl(0, 0);
    private final Position p1 = new PositionImpl(10, 10);
    private final Position p2 = new PositionImpl(10, 0);
    private final Position p3 = new PositionImpl(0, 10);
    private final Position p4 = new PositionImpl(-5, 5 * Math.sqrt(3));
    private final Position p5 = new PositionImpl(-5 * Math.sqrt(3), -5);
    private final Position p6 = new PositionImpl(10, -10);
    private final Position p7 = new PositionImpl(-10.0, -24.142135);
    private final Position p8 = new PositionImpl(-10.0, -24.142136);
    private final Position left = new PositionImpl(1, 0);
    private final Position top = new PositionImpl(0, 1);
    private final Position right = new PositionImpl(-1, 0);
    private final Position down = new PositionImpl(0, -1);

    /**
     * Testing for the angles calculations.
     */
    @Test
    public void testAngle() {
        assertEquals(45.0, EnvGeometry.angle(ORIGIN, p1), DELTA);
        assertEquals(0.0, EnvGeometry.angle(ORIGIN, p2), DELTA);
        assertEquals(90.0, EnvGeometry.angle(ORIGIN, p3), DELTA);
        assertEquals(120.0, EnvGeometry.angle(ORIGIN, p4), DELTA);
        assertEquals(210.0, EnvGeometry.angle(ORIGIN, p5), DELTA);
        assertEquals(315.0, EnvGeometry.angle(ORIGIN, p6), DELTA);
        assertEquals(247.5, EnvGeometry.angle(ORIGIN, p7), DELTA);
        assertEquals(247.5, EnvGeometry.angle(ORIGIN, p8), DELTA);

        assertEquals(0, EnvGeometry.angle(ORIGIN, left), DELTA);
        assertEquals(90, EnvGeometry.angle(ORIGIN, top), DELTA);
        assertEquals(180, EnvGeometry.angle(ORIGIN, right), DELTA);
        assertEquals(270, EnvGeometry.angle(ORIGIN, down), DELTA);
    }

    /**
     * Test for the conversion from angles to Direction.
     */
    @Test
    public void testDirection() {
        assertEquals(Direction.NORTHEAST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p1)));
        assertEquals(Direction.EAST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p2)));
        assertEquals(Direction.NORTH, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p3)));
        assertEquals(Direction.NORTHWEST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p4)));
        assertEquals(Direction.SOUTHWEST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p5)));
        assertEquals(Direction.SOUTHEAST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p6)));
        assertEquals(Direction.SOUTHWEST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p7)));
        assertEquals(Direction.SOUTH, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, p8)));
        
        assertEquals(Direction.EAST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, left)));
        assertEquals(Direction.NORTH, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, top)));
        assertEquals(Direction.WEST, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, right)));
        assertEquals(Direction.SOUTH, EnvGeometry.directionFromAngle(EnvGeometry.angle(ORIGIN, down)));
    }
}
