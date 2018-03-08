package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Analisys;
import view.InitialState;
import view.ObserverExistingFoods;
import view.ViewPosition;
import view.food.ViewFood;
/**
 * Fake controller used only for JUnit test for Observers.
 *
 */
public class FakeController implements Controller {
    private final Map<ViewPosition, ViewFood> map = new HashMap<>();
    private final Set<ViewFood> foods = new HashSet<>();
    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.map.put(position, food);
    }


    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.foods.add(food);
    }
    /**
     * 
     * @return List of food's type.
     */
    public Set<ViewFood> getFoodsType() {
        return this.foods;
    }
    /**
     * 
     * @return all the inserted foods.
     */
    public Map<ViewPosition, ViewFood> getInsertedFoods() {
        return this.map;
    }
    @Override
    public void start(final InitialState state) {
        // TODO Auto-generated method stub
    }
    @Override
    public void loadReplay(final String path) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveReplay(final String path, final Replay rep) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveAnalisys(final String path, final Analisys analisys) {
        // TODO Auto-generated method stub

    }


    @Override
    public Set<ViewFood> getExistingViewFoods() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void addObserverExisistingFoods(final ObserverExistingFoods obs) {
        // TODO Auto-generated method stub
    }
}
