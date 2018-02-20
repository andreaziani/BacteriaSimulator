package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import utils.NotEnounghEnergyException;

/**
 *Unit test for the NutrientStorage class.
 */
public class TestNutrientStorage {

    @Test
    public void testEmpty() {
        NutrientStorage storage = new NutrientStorage(n -> (() -> 0));
        assertEquals(storage.getEnergyStored().getAmount(), 0);
        assertTrue(storage.getNutrients().isEmpty());
        assertThrows(NotEnounghEnergyException.class, () -> storage.takeEnergy(() -> 1));
    }
}
