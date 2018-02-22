package view;

import model.Analisys;
import view.food.ViewFood;

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
