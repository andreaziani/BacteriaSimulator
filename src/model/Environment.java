package model;
/** Environment interface.
 * 
 * 
 *
 */
public interface Environment {
    /** add food.
     * 
     * @param food to insert.
     * @param position of the food in the environment.
     */
    void addFood(Food food, Position position);
    /** return the EnvState.
     * 
     * @return EnvState state of env.
     */
    State getState();
    /** update environment.
     * 
     */
    void update();
    /** return the analisys of the species.
     * 
     * @return Analisys of fitness.
     */
    Analisys getAnalisys();
}
