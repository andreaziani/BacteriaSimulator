package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.EnergyImpl;
import model.bacteria.NutrientStorage;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import utils.exceptions.NotEnounghEnergyException;

/**
 * Unit test for the NutrientStorage class.
 */
public class TestNutrientStorage {
    private static final double DELTA = 0.0625;
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
        final NutrientStorage storage = new NutrientStorage(n -> (new EnergyImpl(1)));
        assertEquals(0, storage.getEnergyStored().getAmount(), DELTA);
        assertTrue(storage.getNutrients().isEmpty());
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));
        try {
            storage.takeEnergy(EnergyImpl.ZERO);
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
        final NutrientStorage storage = new NutrientStorage(n -> (EnergyImpl.ZERO));
        storage.storeFood(food1);
        assertFalse(storage.getNutrients().isEmpty());
        assertEquals(0, storage.getEnergyStored().getAmount(), DELTA);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));

        storage.storeFood(food2);
        assertFalse(storage.getNutrients().isEmpty());
        assertEquals(0, storage.getEnergyStored().getAmount(), DELTA);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));
    }

    /**
     * This test add food to the store multiple times, asserting that the nutrients
     * inside are correctly updated.
     */
    @Test
    public void testStoreFood() {
        final NutrientStorage storage = new NutrientStorage(n -> (EnergyImpl.ZERO));
        storage.storeFood(food1);
        assertEquals(food1, new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()));
        storage.storeFood(food2);
        assertNotEquals(food1, new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()));
        assertNotEquals(food2, new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()));
    }

    /**
     * This test add food to the store and spend energy, asserting that the
     * contained energy is correct after each operation.
     */
    @Test
    public void testTakeAndStoreEnergy() {
        final NutrientStorage storage = new NutrientStorage(n -> (new EnergyImpl(n.ordinal())));
        storage.storeFood(food3);
        assertEquals(Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal(),
                storage.getEnergyStored().getAmount(), DELTA);
        try {
            storage.takeEnergy(new EnergyImpl(1));
        } catch (Exception e) {
            fail("Should have worked");
        }
        assertEquals(Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal() - 1, 
                storage.getEnergyStored().getAmount(), DELTA);
        try {
            storage.takeEnergy(new EnergyImpl(Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal() - 1));
        } catch (Exception e) {
            fail("Should have worked");
        }
        assertEquals(0, storage.getEnergyStored().getAmount(), DELTA);
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));
    }
}
