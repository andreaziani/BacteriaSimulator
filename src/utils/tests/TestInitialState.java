package utils.tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import com.google.gson.Gson;

import controller.InitialState;
import model.Position;
import model.PositionImpl;
import model.State;
import model.StateImpl;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.food.Food;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCodeImpl;
import view.model.bacteria.ViewSpecies;
import view.model.food.CreationViewFoodImpl;
import view.model.food.CreationViewFoodImpl.ViewFoodBuilder;

/**
 * Tests the InitialState class and related classes.
 */
public class TestInitialState {
    private static final String SPECIES_NAME2 = "Other species name";
    private static final String SPECIES_NAME1 = "SpeciesName";

    /**
     * Tests correctness of the json serialization of an InitialState object.
     */
    @Test
    public void testJsonSerialization() {
        final InitialState state = new InitialState(TestUtils.getLargeDouble(), TestUtils.getLargeDouble());
        state.addFood((CreationViewFoodImpl) new ViewFoodBuilder("FoodName")
                .addNutrient(Pair.of(Nutrient.CARBOHYDRATES, TestUtils.getSmallDouble())).build());
        state.addSpecies(
                new ViewSpecies(SPECIES_NAME1, new HashSet<>(Collections.singleton(DecisionMakerOption.ALWAYS_EAT)),
                        Arrays.asList(BehaviorDecoratorOption.COST_FILTER)));
        final Gson gson = new Gson();
        final String json = gson.toJson(state);
        final InitialState stateRestored = gson.fromJson(json, InitialState.class);
        assertEquals("state constructed from json should not equals to the original", state, stateRestored);
    }

    /**
     * Tests that a conversion from a state into a SimpleState has no loss of
     * informations and tests that an InitialState does the conversion correctly.
     */
    @Test
    public void testStateConversion() { 
        final InitialState initialState = new InitialState(TestUtils.getLargeDouble(), TestUtils.getLargeDouble());
        initialState.addFood((CreationViewFoodImpl) new ViewFoodBuilder("FoodName")
                .addNutrient(Pair.of(Nutrient.CARBOHYDRATES, TestUtils.getSmallDouble())).build());
        initialState.addSpecies(new ViewSpecies(SPECIES_NAME1));
        final Set<DecisionMakerOption> options = new HashSet<>();
        options.add(DecisionMakerOption.ALWAYS_EAT);
        options.add(DecisionMakerOption.NO_MOVEMENT);
        options.add(DecisionMakerOption.NO_REPLICATION);
        initialState.addSpecies(new ViewSpecies(SPECIES_NAME2, options,
                Collections.singletonList(BehaviorDecoratorOption.COST_FILTER)));
        final Map<Position, Food> foodState = new HashMap<>();
        foodState.put(new PositionImpl(TestUtils.getSmallDouble(), TestUtils.getLargeDouble()), TestUtils.getAFood());
        foodState.put(new PositionImpl(TestUtils.getLargeDouble(), TestUtils.getSmallDouble()),
                new FoodFactoryImpl().createFoodFromNameAndNutrients("FoodName",
                        Collections.singletonMap(Nutrient.CARBOHYDRATES, TestUtils.getSmallDouble())));
        final Map<Position, Bacteria> bacteriaState = new HashMap<>();
        final Species species1 = new SpeciesBuilder(SPECIES_NAME1).build();
        bacteriaState.put(new PositionImpl(TestUtils.getSmallDouble(), TestUtils.getSmallDouble()),
                new BacteriaImpl(0, species1,
                        new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(), TestUtils.getSmallDouble()),
                        TestUtils.getSmallEnergy()));
        final Species species2 = new SpeciesBuilder(SPECIES_NAME2).addDecisionBehaiorDecorator(BehaviorDecoratorOption.COST_FILTER)
                                                                  .addDecisionMaker(DecisionMakerOption.ALWAYS_EAT)
                                                                  .addDecisionMaker(DecisionMakerOption.NO_MOVEMENT)
                                                                  .addDecisionMaker(DecisionMakerOption.NO_REPLICATION)
                                                                  .build();
        bacteriaState.put(new PositionImpl(TestUtils.getSmallDouble(), TestUtils.getLargeDouble()),
                new BacteriaImpl(0, species2,
                        new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(), TestUtils.getSmallDouble()),
                        TestUtils.getSmallEnergy()));

        final State state = new StateImpl(foodState, bacteriaState);
        initialState.setState(state);
        final Function<ViewSpecies, Species> speciesMapper = x -> x.getName().equals(SPECIES_NAME1) ? species1
                : species2;
        assertEquals("state reconstructed from initialState should equals the given state", 
                state, initialState.reconstructState(speciesMapper, () -> TestUtils.getSmallEnergy()));
    }
}
