package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Position;
import model.PositionImpl;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import utils.exceptions.AlreadyExistingFoodException;
import utils.exceptions.PositionAlreadyOccupiedException;
/**
 * Test class for food.
 * 
 *
 */
public class TestFood {
    private static final double V1 = 10.2;
    private static final double V2 = 0.2;
    private static final double V3 = 13.1;
    private final Map<Nutrient, Double> nutrients1 = new HashMap<>();
    private final Map<Nutrient, Double> nutrients2 = new HashMap<>();
    private final Map<Nutrient, Double> nutrients3 = new HashMap<>();
    /**
     * Initialize nutrients.
     */
    @Before
    public void createNutrients() {
        this.nutrients1.put(Nutrient.CARBOHYDRATES, V1);
        this.nutrients1.put(Nutrient.HYDROLYSATES, V2);
        this.nutrients1.put(Nutrient.PEPTONES, V3);
        this.nutrients3.put(Nutrient.CARBOHYDRATES, V1);
        this.nutrients3.put(Nutrient.HYDROLYSATES, V2);
        this.nutrients3.put(Nutrient.PEPTONES, V3);
        this.nutrients3.put(Nutrient.WATER, V1);
        this.nutrients2.put(Nutrient.WATER, V1);
        this.nutrients2.put(Nutrient.INORGANIC_SALT, V2);
    }

    private void modifyNutrients() {
        this.nutrients1.put(Nutrient.CARBOHYDRATES, V2);
        this.nutrients1.put(Nutrient.HYDROLYSATES, V2);
        this.nutrients1.put(Nutrient.PEPTONES, V3);
        this.nutrients2.put(Nutrient.WATER, V1);
        this.nutrients2.put(Nutrient.INORGANIC_SALT, V2);
    }
    /**
     * Test for food creation (Food, FoodFactory).
     * Creating 5 foods from different type of nutrients and
     * check if they are equals.
     */
    @Test
    public void testFoodCreation() {
        final FoodFactory factory = new FoodFactoryImpl();
        final Food food1 = factory.createFoodFromNameAndNutrients("banana", nutrients1);
        final Food food2 = factory.createFoodFromNameAndNutrients("pera", nutrients2);
        final Food food3 = factory.createFoodFromNameAndNutrients("banana", nutrients1);
        final Food food4 = factory.createFoodFromNameAndNutrients("pesca", nutrients3);

        assertNotEquals("Foods are not equals", food1, food2);
        assertNotEquals("Foods are not equals", food1, food4);
        assertEquals("Foods are equals", food1, food3);
        modifyNutrients(); // verifico che modificando i nutrienti non si modifichino i food già creati.
        final Food food5 = factory.createFoodFromNameAndNutrients("Banana", nutrients1);
        assertNotEquals("Foods are not equals", food1, food5);
    }
    /**
     * Test for ExsistingFoodManager's methods.
     * Create some different type of foods and try to add into
     * ExistingFoodManager, maybe it throws exceptions if a food already exist
     * or if you try to add food from the unmodifiable copy of the set.
     */
    @Test
    public void testManager() {
        final FoodFactory factory = new FoodFactoryImpl();
        final Food food1 = factory.createFoodFromNameAndNutrients("Banana", nutrients1);
        final Food food2 = factory.createFoodFromNameAndNutrients("pera", nutrients2);
        final Food food3 = factory.createFoodFromNameAndNutrients("Banana", nutrients1);
        final ExistingFoodManager manager = new ExistingFoodManagerImpl();

        manager.addFood(food1);
        manager.addFood(food2);
        assertThrows(AlreadyExistingFoodException.class, () -> manager.addFood(food3));
        modifyNutrients();
        final Food food4 = factory.createFoodFromNameAndNutrients("lampone", nutrients1);
        assertThrows(UnsupportedOperationException.class, () -> manager.getExistingFoodsSet().add(food4)); // try to add into an unmodifiable copy.
        assertEquals("Size must be 2", manager.getExistingFoodsSet().size(), 2);
        manager.addFood(food4);
        assertEquals("Size must be 3", manager.getExistingFoodsSet().size(), 3);
        assertTrue("names are equals", manager.getExistingFoodsSet().contains(food4));
    }
    /**
     * Test for FoodEnvironment's methods.
     * Trying to add some foods in different positions of environment.
     */
    @Test
    public void testFoodEnv() {
        createNutrients();
        final FoodFactory factory = new FoodFactoryImpl();
        final Position position = new PositionImpl(V1, V2);
        final Position position2 = new PositionImpl(V3, V2);
        final Position position3 = new PositionImpl(V1, V3);
        final Food food1 = factory.createFoodFromNameAndNutrients("banana", nutrients1);
        final Food food2 = factory.createFoodFromNameAndNutrients("mela", nutrients2);
        final Food food3 = factory.createFoodFromNameAndNutrients("arancia", nutrients1);
        final ExistingFoodManager manager = new ExistingFoodManagerImpl();
        final FoodEnvironment env = new FoodEnvironmentImpl(manager);
        manager.addFood(food1);
        manager.addFood(food2);
        manager.addFood(food3);
        env.addFood(food1, position);
        env.addFood(food1, position3);
        assertEquals("Size must be 2", env.getFoodsState().size(), 2);
        env.removeFood(food1, position3);
        assertEquals("Size must be 1", env.getFoodsState().size(), 1);
        env.changeFoodPosition(position, position2, food1);
        assertEquals("Foods must be equals", env.getFoodsState().get(position2), food1);
        assertThrows(IllegalArgumentException.class, () -> env.changeFoodPosition(position, position3, food1)); // trying to change food's 
                                                                                                                // position but position is wrong.
        assertThrows(PositionAlreadyOccupiedException.class, () -> env.addFood(food1, position2)); // trying to add a food in a position already occupied.
        for (int i = 0; i < 100; i++) {
            env.addRandomFood();
        }
//        System.out.println(env.getFoodsState().size());
//        env.getFoodsState().forEach((k, v) -> System.out.println(k.getX() + " " + k.getY()));
    }

}
