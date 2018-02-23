package model.bacteria.behavior;

import java.util.Random;

import model.Direction;
import model.action.ActionType;
import model.action.DirectionalActionImpl;

public class RandomMovementDecisionBehavior extends DecisionBehaviorDecorator implements MovementBehavior {

    public RandomMovementDecisionBehavior(AbstractDecisionBehavior delegate) {
        super(delegate);
    }
    
    @Override
    protected void updateDecisionSet() {
        Random rand = new Random();
        //this.getDecisionSet().add(new Decisionnew DirectionalActionImpl(ActionType.MOVE, Direction.values()[rand.nextInt(Direction.values().length)]));
        super.updateDecisionSet();
    }

}
