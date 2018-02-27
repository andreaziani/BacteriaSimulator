package view;

import java.util.ArrayList;
import java.util.List;

import controller.ObserverCreationOfFood;
import controller.ObserverInsertionFromView;
import view.food.ViewFood;

/**
 * Manager allows observing and communicate to all the observers
 * the status changes.
 *
 */
public class ViewInteractionImpl implements ViewInteraction {
    private final List<ObserverInsertionFromView> insertionObservers = new ArrayList<>(); //TODO se c'è un solo osservatore togliere la lista.
    private final List<ObserverCreationOfFood> creationObservers = new ArrayList<>(); //TODO se c'è un solo osservatore togliere la lista.
    @Override
    public void addInsertionObserver(final ObserverInsertionFromView observer) {
        this.insertionObservers.add(observer);
    }
    @Override
    public void removeInsertionObserver(final ObserverInsertionFromView observer) {
        this.insertionObservers.remove(observer);
    }
    @Override
    public void notifyInsertionOfFood(final ViewFood food, final ViewPosition position) {
        this.insertionObservers.forEach(o -> o.update(food, position));
    }
    @Override
    public void addCreationObserver(final ObserverCreationOfFood observer) {
        this.creationObservers.add(observer);
    }
    @Override
    public void removeCreationObserver(final ObserverCreationOfFood observer) {
        this.creationObservers.remove(observer);
    }
    @Override
    public void notifyCreationOfFood(final ViewFood food) {
        this.creationObservers.forEach(o -> o.update(food));
    }
}
