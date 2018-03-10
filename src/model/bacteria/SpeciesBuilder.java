package model.bacteria;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import model.action.ActionType;
import model.bacteria.behavior.AbstractDecisionBehavior;
import model.bacteria.behavior.BaseDecisionBehavior;
import model.bacteria.behavior.Behavior;
import model.bacteria.behavior.BehaviorDecoratorFactory;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * 
 */
public class SpeciesBuilder { // TODO documentation!
    private String name;
    private final Map<ActionType, DecisionMaker> decisionMakers;
    private final List<BehaviorDecoratorOption> decorators;
    private boolean built;

    private class SpieciesImpl implements Species {
        private final String name;
        private final Behavior behavior;

        SpieciesImpl(final String name, final Behavior behavior) {
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

    /**
     * 
     */
    public SpeciesBuilder() {
        built = false;
        decisionMakers = new EnumMap<>(ActionType.class);
        decorators = new ArrayList<>();
    }

    private void controlBuiltIs(final boolean builtState) {
        if (built != builtState) {
            throw new IllegalStateException();
        }
    }

    /**
     * 
     * @param name
     */
    public void setName(final String name) {
        controlBuiltIs(false);
        this.name = name;
    }

    /**
     * 
     * @param type
     * @param decisionMaker
     */
    public void setDecisionMaker(final ActionType type, final DecisionMaker decisionMaker) {
        controlBuiltIs(false);
        decisionMakers.put(type, decisionMaker);
    }

    /**
     * 
     * @param decoratorOption
     */
    public void addDecisionBehaiorDecorator(final BehaviorDecoratorOption decoratorOption) {
        controlBuiltIs(false);
        decorators.add(decoratorOption);
    }

    /**
     * 
     * @return
     */
    public Species build() {
        controlBuiltIs(true);
        if (this.name == null || decisionMakers.isEmpty()) {
            throw new IllegalStateException();
        }
        AbstractDecisionBehavior behavior = new BaseDecisionBehavior(decisionMakers);
        for (final BehaviorDecoratorOption d : decorators) {
            behavior = BehaviorDecoratorFactory.createDecorator(d, behavior);
        }
        built = true;
        return new SpieciesImpl(name, behavior);
    }
}
