package model.bacteria.behavior;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.bacteria.BacteriaKnowledge;

/**
 * A behavior that always choose to do nothing.
 */
public class VoidBehavior implements Behavior {

    @Override
    public Action chooseAction(final BacteriaKnowledge knowledge) {
        return new SimpleAction(ActionType.NOTHING);
    }

}
