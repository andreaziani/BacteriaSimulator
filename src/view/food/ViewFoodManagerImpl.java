package view.food;

import java.util.ArrayList;
import java.util.List;

import controller.ObserverInsertionFromView;
import view.ViewPosition;

/**
 * Manager implementation.
 *
 */
public class ViewFoodManagerImpl implements ViewFoodManager{
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
    public void insertFood(final ViewFood food, final ViewPosition position) {
        this.observers.forEach(o -> o.update(food, position));
    }

}
