package model.bacteria.species;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import view.gui.Colorable;

/**
 * Wrapper of options for the creation of a species.
 */
public class SpeciesOptions implements Colorable {
    private final String name;
    private Set<DecisionMakerOption> decisionOptions = Collections.emptySet();
    private List<BehaviorDecoratorOption> decoratorOptions = Collections.emptyList();

    /**
     * Create a new SpeciesOptions, assigning a name and a color.
     * 
     * @param name
     *            the name of the species.
     */
    public SpeciesOptions(final String name) {
        this.name = name;
    }

    /**
     * Create a new SpeciesOptions, assigning name, color and the components of the
     * behavior.
     * 
     * @param name
     *            the name of the species.
     * @param decisionOptions
     *            the DecisionMaker options.
     * @param decoratorOptions
     *            the BehaviorDecorator options.
     */
    public SpeciesOptions(final String name, final Set<DecisionMakerOption> decisionOptions,
            final List<BehaviorDecoratorOption> decoratorOptions) {
        this(name);
        this.decisionOptions = new HashSet<>(decisionOptions);
        this.decoratorOptions = new ArrayList<>(decoratorOptions);
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return a Set containing all DecosionMakerOption for this Species.
     */
    public Set<DecisionMakerOption> getDecisionOptions() {
        return Collections.unmodifiableSet(decisionOptions);
    }

    /**
     * @return the behavior decorators.
     */
    public List<BehaviorDecoratorOption> getDecoratorOptions() {
        return Collections.unmodifiableList(decoratorOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, decisionOptions, decoratorOptions);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SpeciesOptions other = (SpeciesOptions) obj;
        return Objects.equals(this.name, other.name)
                && this.decisionOptions.containsAll(other.decisionOptions)
                && other.decisionOptions.containsAll(this.decisionOptions)
                && this.decoratorOptions.containsAll(other.decoratorOptions)
                && other.decoratorOptions.containsAll(this.decoratorOptions);
    }
}
