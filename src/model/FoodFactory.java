package model;
/** A factory to create some different type of food.
 * 
 * 
 *
 */
public interface FoodFactory {
    /** Create food inserting the nutrients that compose it.
     * 
     * @param nutrients variable arg of nutrients.
     */
    void createFoodFromNutrients(Nutrients... nutrients);
}
