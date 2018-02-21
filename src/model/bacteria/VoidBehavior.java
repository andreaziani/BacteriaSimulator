package model.bacteria;

import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.perception.Perception;

/**
 * A behavior that always choose to do nothing.
 */
public class VoidBehavior implements Behavior {

    @Override
    public Action chooseAction(final Perception perception) {
        return new SimpleAction(ActionType.NOTHING);
    }

}
