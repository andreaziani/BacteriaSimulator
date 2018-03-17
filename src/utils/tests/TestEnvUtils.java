package utils.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import model.Direction;
import model.Position;
import model.PositionImpl;
import utils.EnvUtil;

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
        assertEquals(45.0, EnvUtil.angle(ORIGIN, p1), DELTA);
        assertEquals(0.0, EnvUtil.angle(ORIGIN, p2), DELTA);
        assertEquals(90.0, EnvUtil.angle(ORIGIN, p3), DELTA);
        assertEquals(120.0, EnvUtil.angle(ORIGIN, p4), DELTA);
        assertEquals(210.0, EnvUtil.angle(ORIGIN, p5), DELTA);
        assertEquals(315.0, EnvUtil.angle(ORIGIN, p6), DELTA);
        assertEquals(247.5, EnvUtil.angle(ORIGIN, p7), DELTA);
        assertEquals(247.5, EnvUtil.angle(ORIGIN, p8), DELTA);

        assertEquals(0, EnvUtil.angle(ORIGIN, left), DELTA);
        assertEquals(90, EnvUtil.angle(ORIGIN, top), DELTA);
        assertEquals(180, EnvUtil.angle(ORIGIN, right), DELTA);
        assertEquals(270, EnvUtil.angle(ORIGIN, down), DELTA);
    }

    /**
     * Test for the conversion from angles to Direction.
     */
    @Test
    public void testDirection() {
        assertEquals(Direction.NORTHEAST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p1)));
        assertEquals(Direction.EAST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p2)));
        assertEquals(Direction.NORTH, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p3)));
        assertEquals(Direction.NORTHWEST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p4)));
        assertEquals(Direction.SOUTHWEST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p5)));
        assertEquals(Direction.SOUTHEAST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p6)));
        assertEquals(Direction.SOUTHWEST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p7)));
        assertEquals(Direction.SOUTH, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, p8)));

        assertEquals(Direction.EAST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, left)));
        assertEquals(Direction.NORTH, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, top)));
        assertEquals(Direction.WEST, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, right)));
        assertEquals(Direction.SOUTH, EnvUtil.angleToDir(EnvUtil.angle(ORIGIN, down)));
    }

    /**
     * Test for the positionStream generator.
     */
    @Test
    public void testPosStream() {
        final Set<Position> myPosition = new HashSet<>();
        final int start = 0;
        final int end = 10;
        final int bacteriaX = -3;
        final int bacteriaY = 12;

        for (int i = start; i < end; i++) {
            for (int j = start; j < end; j++) {
                if (i != 0 && j != 0) {
                    myPosition.add(new PositionImpl(bacteriaX + i, bacteriaY + j));
                }
            }
        }

        final Set<Position> streamPosition = EnvUtil.positionStream(start, end, start, end, new PositionImpl(bacteriaX, bacteriaY))
                                                  .collect(Collectors.toSet());

        assertEquals(myPosition, streamPosition);
    }
}
