package view.model.bacteria;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
    private Map<ActionType, DecisionMakerOption> decisionOptions;
    private List<BehaviorDecoratorOption> decoratorOptions;

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
        this.decisionOptions = new EnumMap<>(ActionType.class);
        this.decoratorOptions = new ArrayList<>();
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
        return decisionOptions.values().stream().collect(Collectors.toSet());
    }

    /**
     * @param decisionOptions
     *            set a mapping between actions and decision makers.
     */
    public void setDecisionOptions(final Map<ActionType, DecisionMakerOption> decisionOptions) {
        this.decisionOptions = Collections.unmodifiableMap(decisionOptions);
    }

    /**
     * @return the behavior decorators.
     */
    public List<BehaviorDecoratorOption> getDecoratorOptions() {
        return Collections.unmodifiableList(decoratorOptions);
    }

    /**
     * @param decoratorOptions
     *            add a list of behavior that will reconsider actions after the
     *            decision makers.
     */
    public void setDecoratorOptions(final List<BehaviorDecoratorOption> decoratorOptions) {
        this.decoratorOptions = Collections.unmodifiableList(decoratorOptions);
    }
}
