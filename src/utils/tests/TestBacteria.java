package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.Test;

import model.Direction;
import model.Energy;
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
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
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
        final Bacteria bacteria = new BacteriaImpl(0, new SpeciesBuilder("").build(), code, EnergyImpl.ZERO);

        Action action = new SimpleAction(ActionType.EAT);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));
        action = new SimpleAction(ActionType.REPLICATE);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));
        action = new SimpleAction(ActionType.NOTHING);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));
        action = new DirectionalActionImpl(ActionType.MOVE, Direction.NORTH, 0);
        assertEquals(code.getActionCost(action), bacteria.getActionCost(action));

        assertEquals(EnergyImpl.ZERO, bacteria.getEnergy());

        assertEquals(code.getRadius(), bacteria.getRadius(), TestUtils.getDoubleCompareDelta());
        assertEquals(code.getPerceptionRadius(), bacteria.getPerceptionRadius(), TestUtils.getDoubleCompareDelta());

        assertEquals(code.getSpeed(), bacteria.getSpeed(), TestUtils.getDoubleCompareDelta());

        assertTrue(bacteria.isDead());

        assertEquals(new SpeciesBuilder("").build(), bacteria.getSpecies());
    }

    private GeneticCode getRandomCode() {
        return new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(),
                TestUtils.getLargeDouble());
    }

    private Bacteria getTestBacteria(final GeneticCode code, final Energy startingEnergy) {
        return new BacteriaImpl(0, new Species() {

            @Override
            public String getName() {
                return "";
            }

            @Override
            public Behavior getBehavior() {
                return (b) -> new SimpleAction(ActionType.EAT);
            }
        }, code, startingEnergy);
    }

    /**
     * Test correct functionality of bacteria perception and action selection.
     */
    @Test
    public void testPerceptionAndActionSelection() {
        final Bacteria bacteria = getTestBacteria(getRandomCode(), TestUtils.getLargeEnergy());
        assertFalse(bacteria.isDead());

        assertThrows(MissingPerceptionExeption.class, bacteria::getAction);

        bacteria.setPerception(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), TestUtils.bestDirection(Direction.NORTH)));
        assertTrue(bacteria.getPerception().getFood().isPresent());
        assertEquals(TestUtils.getAFood(), bacteria.getPerception().getFood().get());

        assertEquals(new SimpleAction(ActionType.EAT), bacteria.getAction());
    }

    /**
     * Test dynamics of energy consumption and accumulation for the bacteria.
     */
    @Test
    public void testEnergyUsage() {
        final GeneticCode code = getRandomCode();
        Energy correctEnergy = TestUtils.getLargeEnergy();
        Bacteria bacteria = getTestBacteria(code, correctEnergy);

        bacteria.spendEnergy(TestUtils.getSmallEnergy());
        correctEnergy = correctEnergy.subtract(TestUtils.getSmallEnergy());
        assertEquals(correctEnergy, bacteria.getEnergy());

        bacteria.addFood(TestUtils.getAFood());
        Energy gain = EnergyImpl.ZERO;
        for (final Nutrient n : TestUtils.getAFood().getNutrients()) {
            if (code.getEnergyFromNutrient(n).getAmount() > 0) {
                gain = gain.add(code.getEnergyFromNutrient(n).multiply(TestUtils.getAFood().getQuantityFromNutrient(n)));
            }
        }
        correctEnergy = correctEnergy.add(gain);
        assertEquals(correctEnergy.getAmount(), bacteria.getEnergy().getAmount(), TestUtils.getDoubleCompareDelta());

        bacteria = getTestBacteria(getRandomCode(), TestUtils.getLargeEnergy());

        bacteria.addFood(TestUtils.getAFood());
        assertEquals(TestUtils.getAFood(), bacteria.getInternalFood(new FoodFactoryImpl()));
    }
}
