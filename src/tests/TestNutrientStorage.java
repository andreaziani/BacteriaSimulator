package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import model.EnergyImpl;
import model.bacteria.NotEnoughEnergyException;
import model.bacteria.NutrientStorage;
import model.bacteria.NutrientStorageImpl;
import model.food.Food;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;

/**
 * Unit test for the NutrientStorageImpl class.
 */
public class TestNutrientStorage {
    /**
     * This test assert that an empty storage has no energy, no nutrients and only
     * zero energy can be taken.
     */
    @Test
    public void testEmpty() {
        final NutrientStorage storage = new NutrientStorageImpl(n -> (new EnergyImpl(1)));
        assertEquals("The storage should not have energy", 0, storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        assertTrue("The storage should be empty", storage.getNutrients().isEmpty());
        assertThrows(NotEnoughEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));
        try {
            storage.takeEnergy(EnergyImpl.ZERO);
        } catch (Exception e) {
            fail("Should have worked");
        }
    }

    /**
     * This test assert that the constructor which takes the initial energy works
     * properly.
     */
    @Test
    public void testStartingEnergy() {
        final NutrientStorage storage = new NutrientStorageImpl(TestUtils.getLargeEnergy(), TestUtils.allNutrientGood());
        assertEquals(TestUtils.getLargeEnergy().getAmount(), storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        assertEquals("The storage should not have energy equals to " + TestUtils.getLargeEnergy(),
                TestUtils.getLargeEnergy(), storage.getEnergyStored());
    }

    /**
     * This test assert that a storage has no energy if all its nutrients don't
     * provide any.
     */
    @Test
    public void testNoEnergy() {
        final NutrientStorage storage = new NutrientStorageImpl(n -> (EnergyImpl.ZERO));
        storage.storeFood(TestUtils.getAFood());
        assertFalse("The storage should be empty", storage.getNutrients().isEmpty());
        assertEquals("The storage should not have energy", 0, storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        assertThrows(NotEnoughEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));

        storage.storeFood(TestUtils.getNotEqualFood(TestUtils.getAFood()));
        assertFalse("The storage should not be empty", storage.getNutrients().isEmpty());
        assertEquals("The storage should not have energy", 0, storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        assertThrows(NotEnoughEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));
    }

    /**
     * This test add food to the store multiple times, asserting that the nutrients
     * inside are correctly updated.
     */
    @Test
    public void testStoreFood() {
        final NutrientStorage storage = new NutrientStorageImpl(n -> (EnergyImpl.ZERO));
        storage.storeFood(TestUtils.getAFood());
        assertEquals("Food stored should be the same that has been inserted", TestUtils.getAFood(),
                new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()));
        final Food secondFood = TestUtils.getNotEqualFood(TestUtils.getAFood());
        storage.storeFood(secondFood);
        assertNotEquals("Food stored should be the first that has been inserted", TestUtils.getAFood(),
                new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()));
        assertNotEquals("Food stored should be the second that has been inserted", secondFood,
                new FoodFactoryImpl().createFoodFromNutrients(storage.getNutrients()));
    }

    /**
     * This test add food to the store and spend energy, asserting that the
     * contained energy is correct after each operation.
     */
    @Test
    public void testTakeAndStoreEnergy() {
        final FoodFactory factory = new FoodFactoryImpl();
        final Map<Nutrient, Double> nutrients = new HashMap<>();
        nutrients.put(Nutrient.WATER, 1.0);
        nutrients.put(Nutrient.INORGANIC_SALT, 1.0);
        final Food food3 = factory.createFoodFromNutrients(nutrients);

        final NutrientStorage storage = new NutrientStorageImpl(n -> (new EnergyImpl(n.ordinal())));
        storage.storeFood(food3);
        assertEquals("Energy stored should corrispond to the energy given",
                Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal(), storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        try {
            storage.takeEnergy(new EnergyImpl(1));
        } catch (Exception e) {
            fail("Should have worked");
        }
        assertEquals("Energy stored should corrispond to the energy given",
                Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal() - 1, storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        try {
            storage.takeEnergy(new EnergyImpl(Nutrient.WATER.ordinal() + Nutrient.INORGANIC_SALT.ordinal() - 1));
        } catch (Exception e) {
            fail("Should have worked");
        }
        assertEquals("Energy stored should be zero", 0, storage.getEnergyStored().getAmount(),
                TestUtils.getDoubleCompareDelta());
        assertThrows(NotEnoughEnergyException.class, () -> storage.takeEnergy(new EnergyImpl(1)));
    }
}
