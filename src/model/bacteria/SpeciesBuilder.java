package model.bacteria;

import java.util.List;
import java.util.Map;

import model.action.ActionType;
import model.bacteria.behavior.AbstractDecisionBehavior;
import model.bacteria.behavior.BaseDecisionBehavior;
import model.bacteria.behavior.Behavior;
import model.bacteria.behavior.BehaviorDecoratorFactory;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.DecisionBehaviorDecorator;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

public class SpeciesBuilder {
    private class SpieciesImpl implements Species{
        String name;
        Behavior behavior;
        
        SpieciesImpl(String name, Behavior behavior) {
            super();
            this.name = name;
            this.behavior = behavior;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Behavior getBehavior() {
            return behavior;
        }
    }
    
    private String name;
    private Map<ActionType, DecisionMaker> decisionMakers;
    private List<BehaviorDecoratorOption> decorators;
    private boolean built;
    
    public SpeciesBuilder() {
        built = false;
    }
    
    private void controlBuiltIs(boolean builtState) {
        if (built != builtState) {
            throw new IllegalStateException();
        }
    }
    
    public void setName(String name) {
        controlBuiltIs(false);
        this.name = name;
    }
    
    public void setDecisionMaker(ActionType type, DecisionMaker decisionMaker) {
        controlBuiltIs(false);
        decisionMakers.put(type, decisionMaker);
    }
    
    public void addDecisionBehaiorDecorator(BehaviorDecoratorOption decoratorOption) {
        controlBuiltIs(false);
        decorators.add(decoratorOption);
    }
    
    public Species build() {
        controlBuiltIs(true);
        AbstractDecisionBehavior behavior = new BaseDecisionBehavior(decisionMakers);
        for (BehaviorDecoratorOption d : decorators) {
            behavior = BehaviorDecoratorFactory.createDecorator(d, behavior);
        }
        built = true;
        return new SpieciesImpl(name, behavior);
    }
}
