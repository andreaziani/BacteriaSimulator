package view.model.bacteria;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.action.ActionType;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;

/**
 * A class that encapsulate the process of creating a Species from the View
 * perspective.
 */
public class ViewSpeciesFactory {
    private final Map<ActionType, List<DecisionMakerOption>> decisionOptionsMap;
    private final List<BehaviorDecoratorOption> decoratorOptionsList;

    /**
     * Create a new ViewSpeciesFactory that take information for the View from the
     * enums of the model.
     */
    public ViewSpeciesFactory() {
        decisionOptionsMap = Arrays.asList(ActionType.values()).stream()
                .collect(Collectors.toMap(Function.identity(), a -> Arrays.asList(DecisionMakerOption.values()).stream()
                        .filter(x -> x.getType().equals(a)).collect(Collectors.toList())));
        decoratorOptionsList = Arrays.asList(BehaviorDecoratorOption.values());
    }

    /**
     * @return a map associating each ActionType to a list of options for the
     *         BehaviorDecorators.
     */
    public Map<ActionType, List<String>> getDecisionOptions() {
        return decisionOptionsMap.entrySet().stream().collect(Collectors.toMap(x -> x.getKey(),
                x -> x.getValue().stream().map(d -> d.toString()).collect(Collectors.toList())));
    }

    /**
     * @return a map associating each ActionType to a list of options for the
     *         DecisionMakers.
     */
    public List<String> getDecoratorOptions() {
        return decoratorOptionsList.stream().map(x -> x.toString()).collect(Collectors.toList());
    }

    /**
     * Create a species from the options given by the user.
     * 
     * @param name
     *            the name of the Species.
     * @param decisionOptions
     *            the decision makers associated with each ActionType.
     * @param decorators
     *            the behavior decorators independent from the action types.
     * @return a ViewSpecies create from the given parameters.
     * @throws SimulationAlreadyStartedExeption
     *             - if the simulation is already started.
     * @throws InvalidSpeciesExeption
     *             - if the given Species cannot be added correctly.
     * @throws AlreadyExistingSpeciesExeption
     *             if a species with that name already exists.
     */
    public ViewSpecies createSpecies(final String name,
            final Map<ActionType, Integer> decisionOptions, final List<Boolean> decorators) {
        return new ViewSpecies(name,
                decisionOptions.entrySet().stream().map(x -> decisionOptionsMap.get(x.getKey()).get(x.getValue()))
                        .collect(Collectors.toSet()),
                decoratorOptionsList.stream().filter(x -> decorators.get(decoratorOptionsList.indexOf(x)))
                        .collect(Collectors.toList()));
    }
}
