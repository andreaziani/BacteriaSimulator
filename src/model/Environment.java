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
     */
    void addFood(Food food);
    /** return the EnvState.
     * 
     * @return EnvState state of env.
     */
    EnvState getState();
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
