package model;

/**
 * Possible direction in which an action can be executed and in which a food can
 * be detected.
 */
public enum Direction {
    /**
     * north direction.
     */
    NORTH,
    /**
     * northeast direction.
     */
    NORTHEAST,
    /**
     * east direction.
     */
    EAST,
    /**
     * southeast direction.
     */
    SOUTHEAST,
    /**
     * south direction.
     */
    SOUTH,
    /**
     * southwest direction.
     */
    SOUTHWEST,
    /**
     * west direction.
     */
    WEST,
    /**
     * northwest direction.
     */
    NORTHWEST;
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
