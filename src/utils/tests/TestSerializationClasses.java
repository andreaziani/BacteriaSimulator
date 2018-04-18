package utils.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import model.AnalysisImpl;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.bacteria.species.Species;
import model.bacteria.species.SpeciesBuilder;
import model.bacteria.species.SpeciesOptions;
import model.food.Food;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCodeImpl;
import model.replay.Replay;
import model.state.InitialState;
import model.state.Position;
import model.state.PositionImpl;
import model.state.SimpleState;
import model.state.State;
import model.state.StateImpl;

/**
 * Tests InitialState, SimpleState and Replay.
 */
public class TestSerializationClasses {
    private static final int NUM_STATES_IN_REPLAY = 100;
    private static final String SPECIES_NAME2 = "Other species name";
    private static final String SPECIES_NAME1 = "SpeciesName";

    private InitialState noStateInitialState;
    private InitialState fullInitialState;
    private State state;
    private Function<SpeciesOptions, Species> speciesMapper;

    /**
     * Initialize objects for tests.
     */
    @Before
    public void initTests() {
        fullInitialState = new InitialState(TestUtils.getLargeDouble(), TestUtils.getLargeDouble());
        fullInitialState.addFood(new FoodFactoryImpl().createFoodFromNameAndNutrients("FoodName",
                Collections.singletonMap(Nutrient.CARBOHYDRATES, TestUtils.getSmallDouble())));
        fullInitialState.addSpecies(new SpeciesOptions(SPECIES_NAME1));
        final Set<DecisionMakerOption> options = new HashSet<>();
        options.add(DecisionMakerOption.ALWAYS_EAT);
        options.add(DecisionMakerOption.NO_MOVEMENT);
        options.add(DecisionMakerOption.NO_REPLICATION);
        fullInitialState.addSpecies(new SpeciesOptions(SPECIES_NAME2, options,
                Collections.singletonList(BehaviorDecoratorOption.COST_FILTER)));

        noStateInitialState = new InitialState(TestUtils.getLargeDouble(), TestUtils.getLargeDouble());
        fullInitialState.getExistingFood().forEach(noStateInitialState::addFood);
        fullInitialState.getSpecies().forEach(noStateInitialState::addSpecies);
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
        final Species species2 = new SpeciesBuilder(SPECIES_NAME2)
                .addDecisionBehaiorDecorator(BehaviorDecoratorOption.COST_FILTER)
                .addDecisionMaker(DecisionMakerOption.ALWAYS_EAT).addDecisionMaker(DecisionMakerOption.NO_MOVEMENT)
                .addDecisionMaker(DecisionMakerOption.NO_REPLICATION).build();
        bacteriaState.put(new PositionImpl(TestUtils.getSmallDouble(), TestUtils.getLargeDouble()),
                new BacteriaImpl(0, species2,
                        new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(), TestUtils.getSmallDouble()),
                        TestUtils.getSmallEnergy()));

        state = new StateImpl(foodState, bacteriaState);
        fullInitialState.setState(state);
        speciesMapper = x -> x.getName().equals(SPECIES_NAME1) ? species1 : species2;
    }

    /**
     * Tests correctness of the json serialization of SimpleState, InitialState and
     * Replay.
     */
    @Test
    public void testJsonSerialization() {
        final Gson gson = new Gson();
        String json = gson.toJson(new SimpleState(state, fullInitialState.getSpecies()));
        assertEquals("state constructed from json should equals the original",
                new SimpleState(state, fullInitialState.getSpecies()), gson.fromJson(json, SimpleState.class));

        json = gson.toJson(noStateInitialState);
        assertEquals("initial state constructed from json should equals the original", noStateInitialState,
                gson.fromJson(json, InitialState.class));

        json = gson.toJson(noStateInitialState);
        assertEquals("initial state constructed from json should equals the original", noStateInitialState,
                gson.fromJson(json, InitialState.class));

        final Replay replay = new Replay(fullInitialState);
        replay.setAnalysis(new AnalysisImpl("analysis result"));
        json = gson.toJson(replay);
        assertEquals("replay constructed from json should equals the original", replay,
                gson.fromJson(json, Replay.class));
        replay.addState(state);
        json = gson.toJson(replay);
        assertEquals("replay constructed from json should equals the original", replay,
                gson.fromJson(json, Replay.class));
    }

    /**
     * Test of an alternative way of serializing a replay that can be done without explicitly constructing the json string of the entire replay.
     */
    @Test
    public void testReplaySerialization() {
        final Gson gson = new Gson();
        final Replay replay = new Replay(fullInitialState);
        for (int i = 0; i < NUM_STATES_IN_REPLAY; i++) {
            replay.addState(state);
        }
        replay.setAnalysis(new AnalysisImpl("analysis result"));
        final List<String> strings = new ArrayList<>();
        strings.add(gson.toJson(replay.getInitialState(), replay.getInitialState().getClass()));
        strings.add(gson.toJson(replay.getAnalysis(), replay.getAnalysis().getClass()));
        replay.getStateList().forEach(s -> strings.add(gson.toJson(s, s.getClass())));

        final Replay reconstructed = new Replay(gson.fromJson(strings.get(0), InitialState.class));
        reconstructed.setAnalysis(gson.fromJson(strings.get(1), AnalysisImpl.class));
        for (int i = 0; i < NUM_STATES_IN_REPLAY; i++) {
            reconstructed.addSimpleState(gson.fromJson(strings.get(i + 2), SimpleState.class));
        }
        assertEquals("replay reconstructed should equals the original", replay,
                reconstructed);
    }
    /**
     * Tests that a conversion from a state into a SimpleState has no loss of
     * informations and tests that an InitialState does the conversion correctly.
     */
    @Test
    public void testStateConversion() {
        assertEquals("state reconstructed from fullInitialState should equals the given state", state,
                fullInitialState.reconstructState(speciesMapper, () -> TestUtils.getSmallEnergy()));
    }
}
