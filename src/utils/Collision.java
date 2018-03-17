package utils;

import java.util.Map;
import model.Position;

/**
 * Functional Interface for collisions.
 * @param <X> Class type for which check the collision
 */
@FunctionalInterface
public interface Collision<X> {
    /**
     * Method to check whether two Position generate a Collision.
     * @param p1 the first Position
     * @param p2 the second Position
     * @param map the map in which check the collision
     * @return boolean result that indicates if a collision occur
     */
    boolean check(Position p1, Position p2, Map<Position, X> map);
}
