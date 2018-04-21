package view.model.bacteria;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.action.ActionType;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.bacteria.species.SpeciesOptions;
import model.bacteria.species.behavior.BehaviorDecoratorOption;

/**
 * A static factory that encapsulate the process of creating a Species from the
 * View perspective. This utility class interprets selected options of the View
 * and build an object that will be used to build a species in the model.
 */
public final class SpeciesOptionsFactory {
    private static final Map<ActionType, List<DecisionMakerOption>> DECISION_OPTIONS_MAP;
    private static final List<BehaviorDecoratorOption> DECORATOR_OPTIONS_LIST;

    static {
        DECISION_OPTIONS_MAP = Arrays.asList(ActionType.values()).stream()
                .collect(Collectors.toMap(Function.identity(), a -> Arrays.asList(DecisionMakerOption.values()).stream()
                        .filter(x -> x.getType().equals(a)).collect(Collectors.toList())));
        DECORATOR_OPTIONS_LIST = Arrays.asList(BehaviorDecoratorOption.values());
    }

    private SpeciesOptionsFactory() {
    }

    /**
     * @return a map associating each ActionType to a list of options for the
     *         BehaviorDecorators.
     */
    public static Map<ActionType, List<String>> getDecisionOptions() {
        return DECISION_OPTIONS_MAP.entrySet().stream().collect(Collectors.toMap(x -> x.getKey(),
                x -> x.getValue().stream().map(d -> d.toString()).collect(Collectors.toList())));
    }

    /**
     * @return a map associating each ActionType to a list of options for the
     *         DecisionMakers.
     */
    public static List<String> getDecoratorOptions() {
        return DECORATOR_OPTIONS_LIST.stream().map(x -> x.toString()).collect(Collectors.toList());
    }

    /**
     * Create a wrapper for the options given by the user for a species.
     * 
     * @param name
     *            the name of the Species.
     * @param decisionOptions
     *            the decision makers associated with each ActionType.
     * @param decorators
     *            the behavior decorators independent from the action types.
     * @return a SpeciesOptions create from the given parameters.
     */
    public static SpeciesOptions createSpecies(final String name, final Map<ActionType, Integer> decisionOptions,
            final List<Boolean> decorators) {
        return new SpeciesOptions(name,
                decisionOptions.entrySet().stream().map(x -> DECISION_OPTIONS_MAP.get(x.getKey()).get(x.getValue()))
                        .collect(Collectors.toSet()),
                DECORATOR_OPTIONS_LIST.stream().filter(x -> decorators.get(DECORATOR_OPTIONS_LIST.indexOf(x)))
                        .collect(Collectors.toList()));
    }
}
