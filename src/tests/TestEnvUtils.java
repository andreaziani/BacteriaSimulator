package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import model.Direction;
import model.simulator.EnvironmentUtil;
import model.simulator.bacteria.Collidable;
import model.state.Position;
import model.state.PositionImpl;

/**
 * Test class for the EnvUtils class.
 *
 *
 */
public class TestEnvUtils {
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
        assertEquals(45.0, EnvironmentUtil.angle(ORIGIN, p1), DELTA);
        assertEquals(0.0, EnvironmentUtil.angle(ORIGIN, p2), DELTA);
        assertEquals(90.0, EnvironmentUtil.angle(ORIGIN, p3), DELTA);
        assertEquals(120.0, EnvironmentUtil.angle(ORIGIN, p4), DELTA);
        assertEquals(210.0, EnvironmentUtil.angle(ORIGIN, p5), DELTA);
        assertEquals(315.0, EnvironmentUtil.angle(ORIGIN, p6), DELTA);
        assertEquals(247.5, EnvironmentUtil.angle(ORIGIN, p7), DELTA);
        assertEquals(247.5, EnvironmentUtil.angle(ORIGIN, p8), DELTA);

        assertEquals(0, EnvironmentUtil.angle(ORIGIN, left), DELTA);
        assertEquals(90, EnvironmentUtil.angle(ORIGIN, top), DELTA);
        assertEquals(180, EnvironmentUtil.angle(ORIGIN, right), DELTA);
        assertEquals(270, EnvironmentUtil.angle(ORIGIN, down), DELTA);
    }

    /**
     * Test for the conversion from angles to Direction.
     */
    @Test
    public void testDirection() {
        assertEquals(Direction.NORTHEAST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p1)));
        assertEquals(Direction.EAST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p2)));
        assertEquals(Direction.NORTH, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p3)));
        assertEquals(Direction.NORTHWEST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p4)));
        assertEquals(Direction.SOUTHWEST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p5)));
        assertEquals(Direction.SOUTHEAST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p6)));
        assertEquals(Direction.SOUTHWEST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p7)));
        assertEquals(Direction.SOUTH, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, p8)));

        assertEquals(Direction.EAST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, left)));
        assertEquals(Direction.NORTH, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, top)));
        assertEquals(Direction.WEST, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, right)));
        assertEquals(Direction.SOUTH, EnvironmentUtil.angleToDir(EnvironmentUtil.angle(ORIGIN, down)));
    }

    /**
     * Test for the positionStream generator.
     */
    @Test
    public void testPosStream() {
        final Set<Position> myPosition = new HashSet<>();
        final int start = 0;
        final int end = 5;
        final int bacteriaX = -2;
        final int bacteriaY = 12;
        final Position maxPos = new PositionImpl(100, 100);

        for (int i = start; i < end; i++) {
            for (int j = start; j < end; j++) {
                if (i != 0 || j != 0) {
                    final Position pos = new PositionImpl(bacteriaX + i, bacteriaY + j);
                    if (pos.getX() > 0 && pos.getX() < maxPos.getX() && pos.getY() > 0 && pos.getY() < maxPos.getY()) {
                        myPosition.add(pos);
                    }
                }
            }
        }
        System.out.println(myPosition);
        final Set<Position> streamPosition = EnvironmentUtil.positionStream(start, end, start, end, new PositionImpl(bacteriaX, bacteriaY), maxPos)
                                                  .collect(Collectors.toSet());
        System.out.println(streamPosition);
        assertEquals(myPosition, streamPosition);
    }

    /**
     * Test the Collision detector.
     */
    @Test
    public void testCollision() {
        //final Collidable c1 = new CollidableTest(0.5);
        final Collidable c2 = new CollidableTest(2.0 * Math.sqrt(2));
        final Collidable c3 = new CollidableTest(2.5);
        //final Collidable c4 = new CollidableTest(3.5);
        //final Collidable c5 = new CollidableTest(4.5);

        final Position p1 = new PositionImpl(0.0, 0.0);
        final Position p2 = new PositionImpl(0.0, 5.1);
        final Position p3 = new PositionImpl(2.1, 2.1);
        final Position p4 = new PositionImpl(0.0, 5.0);
        final Position p5 = new PositionImpl(2.0, 2.0);

        assertTrue(EnvironmentUtil.isCollision(Pair.of(p1, c2), Pair.of(p5, c2)));
        assertTrue(EnvironmentUtil.isCollision(Pair.of(p1, c2), Pair.of(p3, c2)));
        assertTrue(EnvironmentUtil.isCollision(Pair.of(p1, c3), Pair.of(p4, c3)));
        assertFalse(EnvironmentUtil.isCollision(Pair.of(p1, c3), Pair.of(p2, c3)));
    }
}
