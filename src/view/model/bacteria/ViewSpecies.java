package view.model.bacteria;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.action.ActionType;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;

/**
 * Representation of a Species for the View.
 */
public class ViewSpecies {
    private final String name;
    private final Color color;
    private Optional<Set<DecisionMakerOption>> decisionOptions;
    private Optional<List<BehaviorDecoratorOption>> decoratorOptions;

    /**
     * Create a new ViewSpecies, assigning a name and a color.
     * 
     * @param name
     *            the name of the species.
     * @param color
     *            the color of the bacteria of this species.
     */
    public ViewSpecies(final String name, final Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Create a new ViewSpecies, assigning name, color and the components of the
     * behavior.
     * 
     * @param name
     *            the name of the species.
     * @param color
     *            the color of the bacteria of this species.
     * @param decisionOptions
     *            the DecisionMaker options.
     * @param decoratorOptions
     *            the BehaviorDecorator options.
     */
    public ViewSpecies(final String name, final Color color, final Set<DecisionMakerOption> decisionOptions,
            final List<BehaviorDecoratorOption> decoratorOptions) {
        this(name, color);
        this.decisionOptions = Optional.of(decisionOptions);
        this.decoratorOptions = Optional.of(decoratorOptions);
    }

    /**
     * @return the name of the species.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the color of the bacteria of this species.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return a Set containing all DecosionMakerOption for this Species.
     */
    public Set<DecisionMakerOption> getDecisionOptions() {
        return Collections.unmodifiableSet(decisionOptions.orElse(Collections.emptySet()));
    }

    /**
     * @return the behavior decorators.
     */
    public List<BehaviorDecoratorOption> getDecoratorOptions() {
        return Collections.unmodifiableList(decoratorOptions.orElse(Collections.emptyList()));
    }
}
