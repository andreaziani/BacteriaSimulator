package model.bacteria;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import model.action.ActionType;
import model.bacteria.behavior.AbstractDecisionBehavior;
import model.bacteria.behavior.BaseDecisionBehavior;
import model.bacteria.behavior.Behavior;
import model.bacteria.behavior.BehaviorDecoratorFactory;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMaker;

/**
 * A builder for an object of type Species. Can create only a single instance of
 * the object but it can be reseted to build a new one.
 */
public class SpeciesBuilder {
    private String name;
    private Map<ActionType, DecisionMaker> decisionMakers;
    private List<BehaviorDecoratorOption> decorators;
    private boolean built;

    private class SpeciesImpl implements Species {
        private final String name;
        private final Behavior behavior;

        SpeciesImpl(final String name, final Behavior behavior) {
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

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            return Objects.equals(name, ((SpeciesImpl) obj).name);
        }
    }

    /**
     * Create a new SpeciesBuilder with no information.
     */
    public SpeciesBuilder() {
        reset();
    }

    /**
     * Reset the builder to its initial state, removing the object being constructed
     * and all the information about it.
     */
    public final void reset() {
        name = null;
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
     * @param name
     *            the name of the Species being built.
     * @throws IllegalStateException
     *             if the object has already being built.
     */
    public void setName(final String name) {
        controlBuiltIs(false);
        this.name = name;
    }

    /**
     * Set the DecisionMaker of this Species relative to an ActionType.
     * 
     * @param type
     *            the type of the action this DecisionMaker will decide.
     * @param decisionMaker
     *            a DecisionMaker.
     * @throws IllegalStateException
     *             if the object has already being built.
     */
    public void setDecisionMaker(final ActionType type, final DecisionMaker decisionMaker) {
        controlBuiltIs(false);
        decisionMakers.put(type, decisionMaker);
    }

    /**
     * @param decoratorOption
     *            add a BehaviorDecoratorOption to evaluate the decisions made by
     *            the DecisionMakers of the Species being constructed.
     * @throws IllegalStateException
     *             if the object has already being built.
     */
    public void addDecisionBehaiorDecorator(final BehaviorDecoratorOption decoratorOption) {
        controlBuiltIs(false);
        decorators.add(decoratorOption);
    }

    /**
     * @return a new Species with the given configurations if it has a name and at
     *         least a DecisionMaker.
     * @throws IllegalStateException
     *             if the object has already being built.
     */
    public Species build() {
        controlBuiltIs(false);
        if (this.name == null || decisionMakers.isEmpty()) {
            throw new IllegalStateException();
        }
        AbstractDecisionBehavior behavior = new BaseDecisionBehavior(
                decisionMakers.values()
                              .stream()
                              .collect(Collectors.toSet()));
        for (final BehaviorDecoratorOption d : decorators) {
            behavior = BehaviorDecoratorFactory.createDecorator(d, behavior);
        }
        built = true;
        return new SpeciesImpl(name, behavior);
    }
}
