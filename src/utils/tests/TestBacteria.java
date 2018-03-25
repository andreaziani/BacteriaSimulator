package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.Test;

import model.Direction;
import model.EnergyImpl;
import model.action.Action;
import model.action.ActionType;
import model.action.DirectionalActionImpl;
import model.action.SimpleAction;
import model.bacteria.Bacteria;
import model.bacteria.BacteriaImpl;
import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.bacteria.behavior.Behavior;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCode;
import model.geneticcode.GeneticCodeImpl;
import model.perception.PerceptionImpl;
import utils.exceptions.MissingPerceptionExeption;

/**
 * Unit test for Bacteria.
 */
public class TestBacteria {

    /**
     * Test a Bacteria in a non dynamic context.
     */
    @Test
    public void testCreation() {
        final GeneticCode code = new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(),
                TestUtils.getLargeDouble());
        final Bacteria bacteria = new BacteriaImpl(new SpeciesBuilder("").build(), code, EnergyImpl.ZERO);

        Action action = new SimpleAction(ActionType.EAT);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));
        action = new SimpleAction(ActionType.REPLICATE);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));
        action = new SimpleAction(ActionType.NOTHING);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));
        action = new DirectionalActionImpl(ActionType.MOVE, Direction.NORTH);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));

        assertEquals(EnergyImpl.ZERO, bacteria.getEnergy());

        assertEquals(code.getRadius(), bacteria.getRadius(), TestUtils.getDoubleCompareDelta());
        assertEquals(code.getPerceptionRadius(), bacteria.getPerceptionRadius(), TestUtils.getDoubleCompareDelta());

        assertEquals(code.getSpeed(), bacteria.getSpeed(), TestUtils.getDoubleCompareDelta());

        assertTrue(bacteria.isDead());

        assertEquals(new SpeciesBuilder("").build(), bacteria.getSpecies());
    }

    /**
     * Test correct functionality of bacteria perception and action selection.
     */
    @Test
    public void testPerceptionAndActionSelection() {
        final GeneticCode code = new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(),
                TestUtils.getLargeDouble());
        final Bacteria bacteria = new BacteriaImpl(new Species() {

            @Override
            public String getName() {
                return "";
            }

            @Override
            public Behavior getBehavior() {
                return (b) -> new SimpleAction(ActionType.EAT);
            }
        }, code, TestUtils.getLargeEnergy());
        assertFalse(bacteria.isDead());

        assertThrows(MissingPerceptionExeption.class, bacteria::getAction);

        bacteria.setPerception(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), TestUtils.bestDirection(Direction.NORTH)));
        assertTrue(bacteria.getPerception().getFood().isPresent());
        assertEquals(TestUtils.getAFood(), bacteria.getPerception().getFood().get());

        assertEquals(new SimpleAction(ActionType.EAT), bacteria.getAction());
    }
}
