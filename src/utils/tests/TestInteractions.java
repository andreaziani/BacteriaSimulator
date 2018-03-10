package utils.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import controller.ConcreteObserverCreationOfFood;
import controller.ConcreteObserverInsertionFromView;
import controller.Controller;
import controller.ControllerImpl;
import controller.ObserverCreationOfFood;
import controller.ObserverInsertionFromView;
import model.food.Nutrient;
import utils.Pair;
import utils.exceptions.AlreadyExistingFoodException;
import utils.exceptions.PositionAlreadyOccupiedException;
import view.ObserverExistingFoods;
import view.ObserverExistingFoodsImpl;
import view.View;
import view.ViewImpl;
import view.ViewPositionImpl;
import view.food.ViewFood;
import view.food.ViewFoodImpl;
/**
 * TestClass for interaction of the user adding foods.
 *
 */
public class TestInteractions {
    private final ObserverExistingFoods observerExistingFoods = new ObserverExistingFoodsImpl();
    private final Controller controller = new ControllerImpl();
    private final ObserverCreationOfFood observerCreation = new ConcreteObserverCreationOfFood(this.controller);
    private final ObserverInsertionFromView observerInsertion = new ConcreteObserverInsertionFromView(this.controller);
    private final View view = new ViewImpl(observerExistingFoods, observerCreation, observerInsertion);

    private ViewFood creationOfFood(final String name, final Pair<Nutrient, Double> pair) {
        return new ViewFoodImpl.ViewFoodBuilder().addNutrient(pair).setName(name).build();
    }

    /**
     * Testing food creation from view.
     */
    @Test
    public void testCreation() {
        this.controller.addObserverExisistingFoods(this.observerExistingFoods);
        this.view.addNewTypeOfFood(creationOfFood("Banana", new Pair<>(Nutrient.CARBOHYDRATES, 1.0)));
        assertEquals("There is only one type of food", this.observerExistingFoods.getTypeOfFoods().size(), 1);
        this.view.addNewTypeOfFood(creationOfFood("Mela", new Pair<>(Nutrient.WATER, 1.0)));
        assertEquals("There are two types of food", this.observerExistingFoods.getTypeOfFoods().size(), 2);
        //TODO ancora non ho gestito l'eccezione, penso che quando verrà tirata la catcherò portando una finestra di dialogo all'utente.
        assertThrows(AlreadyExistingFoodException.class, () -> this.view.addNewTypeOfFood(creationOfFood("Mela", new Pair<>(Nutrient.WATER, 1.0))));
        this.view.addNewTypeOfFood(creationOfFood("banana", new Pair<>(Nutrient.CARBOHYDRATES, 1.0))); // "banana" è diverso da "Banana".
        assertEquals("banana is different from Banana", this.observerExistingFoods.getTypeOfFoods().size(), 3);
    }
    /**
     * Testing food insertion from view.
     */
    @Test
    public void testInsertion() {
        this.controller.addObserverExisistingFoods(this.observerExistingFoods);
        this.view.addNewTypeOfFood(creationOfFood("Banana", new Pair<>(Nutrient.CARBOHYDRATES, 1.0)));
        this.view.addFood(creationOfFood("Banana", new Pair<>(Nutrient.CARBOHYDRATES, 1.0)), new ViewPositionImpl(1.0, 2.0));
        // il cibo precedente viene correttamente inserito poichè è impossibile inserire un nuovo cibo in quella posizione.
        // TODO ancora non ho gestito l'eccezione, penso che quando verrà tirata la catcherò portando una finestra di dialogo all'utente.
        assertThrows(PositionAlreadyOccupiedException.class, () -> this.view.addFood(creationOfFood("Pera", new Pair<>(Nutrient.CARBOHYDRATES, 1.0)),
                                                                                     new ViewPositionImpl(1.0, 2.0)));
        //TODO aggiungere un controllo che verifichi se inserendo dalla view viene modificato correttamente l'environment
    }
}
