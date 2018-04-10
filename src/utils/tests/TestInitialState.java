package utils.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import com.google.gson.Gson;

import controller.InitialState;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import model.food.Nutrient;
import view.model.bacteria.ViewSpecies;
import view.model.food.CreationViewFoodImpl;
import view.model.food.CreationViewFoodImpl.ViewFoodBuilder;

/**
 * Tests the InitialState class and related classes.
 */
public class TestInitialState {
    /**
     * Tests correctness of the json serialization of an InitialState object.
     */
    @Test
    public void testJsonSerialization() {
        final InitialState state = new InitialState(TestUtils.getLargeDouble(), TestUtils.getLargeDouble());
        state.addFood((CreationViewFoodImpl) new ViewFoodBuilder("FoodName").addColor(Color.BLACK)
                .addNutrient(Pair.of(Nutrient.CARBOHYDRATES, TestUtils.getSmallDouble())).build());
        state.addSpecies(new ViewSpecies("SpeciesName", Color.BLACK,
                new HashSet<>(Collections.singleton(DecisionMakerOption.ALWAYS_EAT)),
                Arrays.asList(BehaviorDecoratorOption.COST_FILTER)));
        final Gson gson = new Gson();
        final String json = gson.toJson(state);
        final InitialState stateRestored = gson.fromJson(json, InitialState.class);
        assertEquals(state, stateRestored);
    }
}
