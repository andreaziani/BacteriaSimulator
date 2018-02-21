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
    /** Add a food.
     * 
     * @param food info.
     */
    void addFood(ViewFood food);
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
