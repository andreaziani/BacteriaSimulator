package view;

import java.util.Set;

import model.Analisys;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.food.ViewFood;

/** View.
 * 
 *
 *
 */
public interface View {
    /** Update view state.
     * 
     * @param state of view.
     */
    void update(ViewState state);
    /**
     * Insert a new food.
     * @param food info.
     * @param position of the food.
     */
    void addFood(ViewFood food, ViewPosition position);
    /**
     * Add a new Food type.
     * @param food type of food to add.
     */
    void addNewTypeOfFood(ViewFood food);
    /**
     * 
     * @return all the type of foods created.
     */
    Set<ViewFood> getFoodsType();
    /** Load a replay.
     * 
     * @param path of the Replay file.
     */
    void loadReplay(String path);
    /** Show analisys.
     * 
     * @param analisys to show.
     */
    void showAnalisys(Analisys analisys);
}
