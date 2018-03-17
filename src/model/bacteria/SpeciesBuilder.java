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
import model.bacteria.behavior.decisionmaker.DecisionMakerFactory;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;

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
     * 
     * @return the builder.
     */
    public final SpeciesBuilder reset() {
        name = null;
        built = false;
        decisionMakers = new EnumMap<>(ActionType.class);
        decorators = new ArrayList<>();
        return this;
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
     * @return the builder.
     */
    public SpeciesBuilder setName(final String name) {
        controlBuiltIs(false);
        this.name = name;
        return this;
    }

    /**
     * @param option
     *            add a DecisionMaker associated to a DecisionMakerOption to the
     *            decision makers of the Species Behavior. If there is already a
     *            DecisionMaker associated with a particular type it will be
     *            replaced instead of added.
     * @return the builder.
     * @throws IllegalStateException
     *             if the object has already being built.
     */
    public SpeciesBuilder addDecisionMaker(final DecisionMakerOption option) {
        controlBuiltIs(false);
        decisionMakers.put(option.getType(), DecisionMakerFactory.createDecisionMaker(option));
        return this;
    }

    /**
     * @param decoratorOption
     *            add a BehaviorDecoratorOption to evaluate the decisions made by
     *            the DecisionMakers of the Species being constructed.
     * @return the builder.
     * @throws IllegalStateException
     *             if the object has already being built.
     */
    public SpeciesBuilder addDecisionBehaiorDecorator(final BehaviorDecoratorOption decoratorOption) {
        controlBuiltIs(false);
        decorators.add(decoratorOption);
        return this;
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
                decisionMakers.values().stream().collect(Collectors.toSet()));
        for (final BehaviorDecoratorOption d : decorators) {
            behavior = BehaviorDecoratorFactory.createDecorator(d, behavior);
        }
        built = true;
        return new SpeciesImpl(name, behavior);
    }
}
