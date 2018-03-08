package model.bacteria.behavior;

import java.util.function.Function;

import model.Energy;
import model.action.Action;
import model.action.ActionType;
import model.action.SimpleAction;
import model.food.Nutrient;
import model.perception.Perception;

/**
 * A behavior that always choose to do nothing.
 */
public class VoidBehavior implements Behavior {

    @Override
    public Action chooseAction(final Perception perception, final Function<Nutrient, Energy> nutrientToEnergyConverter,
            final Function<Action, Energy> actionCostFunction) {
        return new SimpleAction(ActionType.NOTHING);
    }

}
