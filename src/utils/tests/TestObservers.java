package utils.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import controller.ConcreteObserverCreationOfFood;
import controller.ConcreteObserverInsertionFromView;
import controller.ObserverCreationOfFood;
import controller.ObserverInsertionFromView;
import model.food.Nutrient;
import utils.FakeController;
import utils.Pair;
import view.ViewInteraction;
import view.ViewInteractionImpl;
import view.ViewPositionImpl;
import view.food.ViewFood;
import view.food.ViewFoodImpl;
/**
 * 
 * Test in which it occurs if the observer is actually updated when the ViewInteraction change his state.
 *
 */
public class TestObservers {
    private final FakeController controller = new FakeController(); // controller used only for 
    private final ObserverCreationOfFood obsCreation = new ConcreteObserverCreationOfFood(controller);
    private final ObserverInsertionFromView obsInsertion = new ConcreteObserverInsertionFromView(controller);
    private final ViewInteraction subject = new ViewInteractionImpl();
    /**
     * 
     */
    @Test
    public void testObservers() {
        final ViewFood food = new ViewFoodImpl.ViewFoodBuilder().setName("Banana").addNutrient(new Pair<>(Nutrient.CARBOHYDRATES, 1.0)).build();
        final ViewFood food2 = new ViewFoodImpl.ViewFoodBuilder().setName("Pera").addNutrient(new Pair<>(Nutrient.CARBOHYDRATES, 3.0)).build();
        this.subject.addCreationObserver(obsCreation);
        this.subject.addInsertionObserver(obsInsertion);
        this.subject.notifyCreationOfFood(food);
        this.subject.notifyInsertionOfFood(food, new ViewPositionImpl(1.0, 1.0));
        assertEquals("Add only one food.", this.controller.getFoodsType().size(), 1);
        assertEquals("Inserted one food,", this.controller.getInsertedFoods().size(), 1);
        this.subject.notifyCreationOfFood(food2);
        assertEquals("Inserted second food type", this.controller.getFoodsType().size(), 2);
        this.subject.notifyInsertionOfFood(food2, new ViewPositionImpl(1.0, 2.0));
        assertTrue("This type must be contained", this.controller.getFoodsType().contains(food));
        assertTrue("", this.controller.getInsertedFoods().containsKey(new ViewPositionImpl(1.0, 2.0)));
    }
}
