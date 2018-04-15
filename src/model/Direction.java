package model;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Possible direction in which an action can be executed and in which a food can
 * be detected. The angle represent the lower limit of the direction.
 */
public enum Direction {
    /**
     * north direction.
     */
    NORTH(67.5),
    /**
     * northeast direction.
     */
    NORTHEAST(22.5),
    /**
     * east direction.
     */
    EAST(337.5),
    /**
     * southeast direction.
     */
    SOUTHEAST(292.5),
    /**
     * south direction.
     */
    SOUTH(247.5),
    /**
     * southwest direction.
     */
    SOUTHWEST(202.5),
    /**
     * west direction.
     */
    WEST(157.5),
    /**
     * northwest direction.
     */
    NORTHWEST(112.5);

    private final double angleLowerLimit;

    Direction(final double angle) {
        this.angleLowerLimit = angle;
    }

    /**
     * Return the angle lower and upper limit for the Direction, lower is inclusive, upper is exclusive.
     * @return a MyEntry<Double, Double> that represent the two angles
     */
    public Pair<Double, Double> angleInterval() {
        return Pair.of(this.angleLowerLimit, this.rotateRight().angleLowerLimit);
    }
    /**
     * @return a direction rotated clockwise.
     */
    public Direction rotateLeft() {
        return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
    }

    /**
     * @return a direction rotated counterclockwise.
     */
    public Direction rotateRight() {
        return Direction.values()[(this.ordinal() + Direction.values().length - 1) % Direction.values().length];
    }
}
