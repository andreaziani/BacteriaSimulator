package utils;

import model.Direction;
import model.Position;

/**
 * 
 * Utility class for geometric operations.
 *
 */
public final class EnvGeometry {
    private static final double NORTHEAST_LIMIT = 67.5;
    private static final double NORTH_LIMIT = 112.5;
    private static final double NORTHWEST_LIMIT = 157.5;
    private static final double WEST_LIMIT = 202.5;
    private static final double SOUTHWEST_LIMIT = 247.5;
    private static final double SOUTH_LIMIT = 292.5;
    private static final double SOUTHEAST_LIMIT = 337.5;
    private static final double EAST_LIMIT = 22.5;

    private EnvGeometry() {
    }
    /**
     * Calculate the distance between two Position in a 2D space.
     * @param current the original Position
     * @param other the other Position from which calculate the distance
     * @return the absolute value of the distance between two Position
     */
    public static double distance(final Position current, final Position other) {
        return Math.sqrt((Math.pow(current.getX() - other.getX(), 2) + Math.pow(current.getY() - other.getY(), 2)));
    }

    /**
     * Calculate the angle of the vector between two Position.
     * @param current the Position considered as the origin of the vector
     * @param other the Position considered as the end of the vector
     * @return the angle of the vector in the range [0, 360]
     */
    public static double angle(final Position current, final Position other) {
        final double slope = (other.getY() - current.getY()) / (other.getX() - current.getX());
        double angle = Math.toDegrees(Math.atan(slope));
        if (other.getX() < current.getX()) {
            angle += 180.0;
        } else if (other.getY() < current.getY()) {
            angle += 360.0;
        }
        return angle;
    }

    /**
     * Convert an angle to the closest Direction.
     * @param angle the angle to be converted in Direction
     * @return the Direction
     */
    public static Direction directionFromAngle(final double angle) {
        if (angle > EnvGeometry.EAST_LIMIT && angle <= EnvGeometry.NORTHEAST_LIMIT) {
            return Direction.NORTHEAST;
        } else if (angle > EnvGeometry.NORTHEAST_LIMIT && angle <= EnvGeometry.NORTH_LIMIT) {
            return Direction.NORTH;
        } else if (angle > EnvGeometry.NORTH_LIMIT && angle <= EnvGeometry.NORTHWEST_LIMIT) {
            return Direction.NORTHWEST;
        } else if (angle > EnvGeometry.NORTHWEST_LIMIT && angle <= EnvGeometry.WEST_LIMIT) {
            return Direction.WEST;
        } else if (angle > EnvGeometry.WEST_LIMIT && angle <= EnvGeometry.SOUTHWEST_LIMIT) {
            return Direction.SOUTHWEST;
        } else if (angle > EnvGeometry.SOUTHWEST_LIMIT && angle <= EnvGeometry.SOUTH_LIMIT) {
            return Direction.SOUTH;
        } else if (angle > EnvGeometry.SOUTH_LIMIT && angle <= EnvGeometry.SOUTHEAST_LIMIT) {
            return Direction.SOUTHEAST;
        } else {
            return Direction.EAST;
        }
    }
}
