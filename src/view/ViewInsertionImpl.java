package view;

import java.util.ArrayList;
import java.util.List;

import controller.ObserverInsertionFromView;
import view.food.ViewFood;

/**
 * Manager allows observing and communicate to all the observers
 * the status changes.
 *
 */
public class ViewInsertionImpl implements ViewInsertion {
    private final List<ObserverInsertionFromView> observers = new ArrayList<>(); //TODO se c'è un solo osservatore togliere la lista.
    @Override
    public void addObserver(final ObserverInsertionFromView observer) {
        this.observers.add(observer);
    }
    @Override
    public void removeObserver(final ObserverInsertionFromView observer) {
        this.observers.remove(observer);
    }
    @Override
    public void notifyInsertionOfFood(final ViewFood food, final ViewPosition position) {
        this.observers.forEach(o -> o.update(food, position));
    }

}
