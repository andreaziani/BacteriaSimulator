package model;
/** Position, an interface to manage the coordinates
 *  of bacteria and food.
 *
 *
 */
public interface Position {
    /**
     * 
     * @return the x coordinate.
     */
    double getX();
    /**
     * 
     * @return the y coordinate.
     */
    double getY();
    /**
     * 
     * @param x is coordinate to set.
     */
    void setX(double x);
    /**
     * 
     * @param y is the coordinate to set.
     */
    void setY(double y);
}
