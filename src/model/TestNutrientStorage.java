package model;

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

import utils.NotEnounghEnergyException;

/**
 *Unit test for the NutrientStorage class.
 */
public class TestNutrientStorage {
    private static final double V1 = 10.2;
    private static final double V2 = 0.2;
    private static final double V3 = 13.1;

    private Food food1;
    private Food food2;

    @Before
    public void createFoods() {
        FoodFactory factory = new FoodFactoryImpl();
        Map<Nutrient, Double> nutrients1 = new HashMap<>();
        Map<Nutrient, Double> nutrients2 = new HashMap<>();
        nutrients1.put(Nutrient.CARBOHYDRATES, V1);
        nutrients1.put(Nutrient.HYDROLYSATES, V2);
        nutrients1.put(Nutrient.PEPTONES, V3);
        nutrients2.put(Nutrient.WATER, V1);
        nutrients2.put(Nutrient.INORGANIC_SALT, V2);
        food1 = factory.createFoodFromNutrients(nutrients1);
        food2 = factory.createFoodFromNutrients(nutrients2);
    }
    @Test
    public void testEmpty() {
        NutrientStorage storage = new NutrientStorage(n -> (() -> 1));
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertTrue(storage.getNutrients().isEmpty());
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));
        try {
            storage.takeEnergy(() -> 0);
        } catch (Exception e) {
            fail("Should have worked");
        }
    }

    @Test
    public void testNoEnergy() {
        NutrientStorage storage = new NutrientStorage(n -> (() -> 0));
        storage.storeFood(food1);
        assertFalse(storage.getNutrients().isEmpty());
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));

        storage.storeFood(food2);
        assertFalse(storage.getNutrients().isEmpty());
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));
    }

    @Test
    public void testStoreFood() {
        NutrientStorage storage = new NutrientStorage(n -> (() -> 0));
        storage.storeFood(food1);
        assertEquals(new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()), food1);
        storage.storeFood(food2);
        assertNotEquals(new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()), food1);
        assertNotEquals(new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()), food2);
    }
}
