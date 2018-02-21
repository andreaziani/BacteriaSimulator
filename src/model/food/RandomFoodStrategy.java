package model.food;
/**
 * Interface for adding foods randomly taken from existing foods.
 *
 */
public interface RandomFoodStrategy {
    /**
     * 
     * @param manager from which to take foods.
     * @return a food chose randomly.
     */
    Food getFood(ExistingFoodManager manager);
}
