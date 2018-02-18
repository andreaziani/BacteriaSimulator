package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class TestFood {
    private static final double V1 = 10.2;
    private static final double V2 = 0.2;
    private static final double V3 = 13.1;
    private final Map<Nutrient, Double> nutrients1 = new HashMap<>();
    private final Map<Nutrient, Double> nutrients2 = new HashMap<>();
    private void createNutrients() {
        this.nutrients1.put(Nutrient.carbohydrates, V1);
        this.nutrients1.put(Nutrient.hydrolysates, V2);
        this.nutrients1.put(Nutrient.peptones, V3);
        this.nutrients2.put(Nutrient.water, V1);
        this.nutrients2.put(Nutrient.inorganic_salts, V2);
    }

    private void modifyNutrients() {
        this.nutrients1.put(Nutrient.carbohydrates, V2);
        this.nutrients1.put(Nutrient.hydrolysates, V2);
        this.nutrients1.put(Nutrient.peptones, V3);
        this.nutrients2.put(Nutrient.water, V1);
        this.nutrients2.put(Nutrient.inorganic_salts, V2);
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
        System.out.println(food1.getNutrients().toString());
        System.out.println(food4.getNutrients().toString());
        assertEquals(food1.getNutrients(), food4.getNutrients());
        assertNotEquals("Foods are not equals", food1, food4);
    }
    @Test
    public void test() {
        createNutrients();
        final FoodFactory factory = new FoodFactoryImpl();
        final Food food1 = factory.createFoodFromNutrients(nutrients1);
        assertEquals("Nutrients must be equals", food1.getNutrients(), this.nutrients1.keySet());
    }

}
