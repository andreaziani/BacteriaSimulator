package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class TestFood {
    private static final double V1 = 10.2;
    private static final double V2 = 0.2;
    private static final double V3 = 13.1;
    private final Map<Nutrient, Double> nutrients1 = new HashMap<>();
    private final Map<Nutrient, Double> nutrients2 = new HashMap<>();
    private void createNutrients() {
        this.nutrients1.put(Nutrient.CARBOHYDRATES, V1);
        this.nutrients1.put(Nutrient.HYDROLYSATES, V2);
        this.nutrients1.put(Nutrient.PEPTONES, V3);
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

    @Test
    public void testFoodCreation() {
        createNutrients();
        final FoodFactory factory = new FoodFactoryImpl();
        final Food food1 = factory.createFoodFromNutrients(nutrients1);
        final Food food3 = factory.createFoodFromNutrients(nutrients1);
        final Food food2 = factory.createFoodFromNutrients(nutrients2);
        assertNotEquals("Foods are not equals", food1, food2);
        assertEquals("Foods are equals", food1, food3);
        modifyNutrients();
        final Food food4 = factory.createFoodFromNutrients(nutrients1);
        assertEquals(food1.getNutrients(), food4.getNutrients());
        assertNotEquals("Foods are not equals", food1, food4);
        createNutrients();
        final FoodFactory factory2 = new FoodFactoryImpl();
        final Food food = factory2.createFoodFromNutrients(nutrients1);
        assertEquals("Nutrients must be equals", food.getNutrients(), this.nutrients1.keySet());
    }

    @Test
    public void testManager() {
        createNutrients();
        final FoodFactory factory = new FoodFactoryImpl();
        final Food food1 = factory.createFoodFromNutrients(nutrients1);
        final Food food2 = factory.createFoodFromNutrients(nutrients2);
        Food food3 = factory.createFoodFromNutrients(nutrients1);
        final ExistentFoodManager manager = new ExistentFoodManagerImpl();
        manager.addFood("banana", food1);
        manager.addFood("mela", food2);
        try {
            manager.addFood("lampone", food3);
            fail("Expected an AlreadyExistingException to be thrown");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        modifyNutrients();
        food3 = factory.createFoodFromNutrients(nutrients1);
        try {
            manager.getExsistentFoods().add(food3);
            fail("Expected an UnsupportedOperationException to be thrown");
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
        assertTrue(manager.getExsistentFoods().size() == 2);
        manager.addFood("lampone", food3);
        assertTrue(manager.getExsistentFoods().size() == 3);
        assertEquals("foods must be equals", manager.getFood("banana").get(), food1);
        assertEquals("foods must be equals", manager.getFood("pera"), Optional.empty());
        assertNotEquals("foods are not equals", manager.getFood("banana").get(), food2);
    }


}
