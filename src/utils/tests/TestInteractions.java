package utils.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import controller.Controller;
import controller.ControllerImpl;
import model.food.Nutrient;
import utils.exceptions.AlreadyExistingFoodException;
import utils.exceptions.PositionAlreadyOccupiedException;
import view.View;
import view.ViewImpl;
import view.model.ViewPositionImpl;
import view.model.food.ViewFood;
import view.model.food.ViewFoodImpl.ViewFoodBuilder;
/**
 * TestClass for interaction of the user adding foods.
 *
 */
public class TestInteractions {
    private final Controller controller = new ControllerImpl();
    private final View view = new ViewImpl(this.controller);

    private ViewFood creationOfFood(final String name, final Pair<Nutrient, Double> pair) {
        return new ViewFoodBuilder(name).addNutrient(pair).build();
    }

    /**
     * Testing food creation from view.
     */
    @Test
    public void testCreation() {
        this.view.addNewTypeOfFood(creationOfFood("Banana", Pair.of(Nutrient.CARBOHYDRATES, 1.0)));
        assertEquals("There is only one type of food", this.controller.getExistingViewFoods().size(), 1);
        assertEquals("There is one type of food in each set", this.view.getFoodsType().size(), this.controller.getExistingViewFoods().size());
        this.view.addNewTypeOfFood(creationOfFood("Mela", Pair.of(Nutrient.WATER, 1.0)));
        assertEquals("There are two types of food", this.controller.getExistingViewFoods().size(), 2);
        assertThrows(AlreadyExistingFoodException.class, () -> this.view.addNewTypeOfFood(creationOfFood("Mela", Pair.of(Nutrient.WATER, 1.0))));
        this.view.addNewTypeOfFood(creationOfFood("banana", Pair.of(Nutrient.CARBOHYDRATES, 1.0))); // "banana" è diverso da "Banana".
        assertEquals("banana is different from Banana", this.controller.getExistingViewFoods().size(), 3);
    }
    /**
     * Testing food insertion from view.
     */
    @Test
    public void testInsertion() {
        this.view.addNewTypeOfFood(creationOfFood("Banana", Pair.of(Nutrient.CARBOHYDRATES, 1.0)));
        this.view.addFood(creationOfFood("Banana", Pair.of(Nutrient.CARBOHYDRATES, 1.0)), new ViewPositionImpl(1.0, 2.0));
        this.view.addFood(creationOfFood("Mela", Pair.of(Nutrient.WATER, 2.0)), new ViewPositionImpl(3.0, 2.0));
        assertThrows(PositionAlreadyOccupiedException.class, () -> this.view.addFood(creationOfFood("Pera", Pair.of(Nutrient.CARBOHYDRATES, 1.0)),
                                                                                     new ViewPositionImpl(1.0, 2.0)));
//        System.out.println(this.controller.getState().toString());
        //TODO aggiungere un controllo che verifichi se inserendo dalla view viene modificato correttamente l'environment
    }
}
