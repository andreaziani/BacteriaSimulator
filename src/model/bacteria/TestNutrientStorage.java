package model.bacteria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import utils.NotEnounghEnergyException;

/**
 * Unit test for the NutrientStorage class.
 */
public class TestNutrientStorage {
    private static final double V1 = 10.2;
    private static final double V2 = 0.2;
    private static final double V3 = 13.1;

    private Food food1;
    private Food food2;
    private Food food3;

    /**
     * Initialize all the foods the tests in this unit will need.
     */
    @Before
    public void createFoods() {
        final FoodFactory factory = new FoodFactoryImpl();
        Map<Nutrient, Double> nutrients = new HashMap<>();
        nutrients.put(Nutrient.CARBOHYDRATES, V1);
        nutrients.put(Nutrient.HYDROLYSATES, V2);
        nutrients.put(Nutrient.PEPTONES, V3);
        food1 = factory.createFoodFromNutrients(nutrients);

        nutrients = new HashMap<>();
        nutrients.put(Nutrient.WATER, V1);
        nutrients.put(Nutrient.INORGANIC_SALT, V2);
        food2 = factory.createFoodFromNutrients(nutrients);

        nutrients = new HashMap<>();
        nutrients.put(Nutrient.WATER, 1.0);
        nutrients.put(Nutrient.INORGANIC_SALT, 1.0);
        food3 = factory.createFoodFromNutrients(nutrients);
    }

    /**
     * This test assert that an empty storage has no energy, no nutrients and only
     * zero energy can be taken.
     */
    @Test
    public void testEmpty() {
        final NutrientStorage storage = new NutrientStorage(n -> (() -> 1));
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertTrue(storage.getNutrients().isEmpty());
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));
        try {
            storage.takeEnergy(() -> 0);
        } catch (Exception e) {
            fail("Should have worked");
        }
    }

    /**
     * This test assert that a storage has no energy if all its nutrients don't
     * provide any.
     */
    @Test
    public void testNoEnergy() {
        final NutrientStorage storage = new NutrientStorage(n -> (() -> 0));
        storage.storeFood(food1);
        assertFalse(storage.getNutrients().isEmpty());
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));

        storage.storeFood(food2);
        assertFalse(storage.getNutrients().isEmpty());
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));
    }

    /**
     * This test add food to the store multiple times, asserting that the nutrients
     * inside are correctly updated.
     */
    @Test
    public void testStoreFood() {
        final NutrientStorage storage = new NutrientStorage(n -> (() -> 0));
        storage.storeFood(food1);
        assertEquals(new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()), food1);
        storage.storeFood(food2);
        assertNotEquals(new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()), food1);
        assertNotEquals(new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()), food2);
    }

    /**
     * This test add food to the store and spend energy, asserting that the
     * contained energy is correct after each operation.
     */
    @Test
    public void testTakeAndStoreEnergy() {
        final NutrientStorage storage = new NutrientStorage(n -> (() -> n.ordinal()));
        storage.storeFood(food3);
        assertEquals(storage.getEnergyStored().getAmount(),
                Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal());
        try {
            storage.takeEnergy(() -> 1);
        } catch (Exception e) {
            fail("Should have worked");
        }
        assertEquals(storage.getEnergyStored().getAmount(),
                Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal() - 1);
        try {
            storage.takeEnergy(() -> Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal() - 1);
        } catch (Exception e) {
            fail("Should have worked");
        }
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));
    }
}
