package tests;

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
import model.bacteria.MissingPerceptionExeption;
import model.bacteria.species.Species;
import model.bacteria.species.SpeciesBuilder;
import model.bacteria.species.behavior.Behavior;
import model.food.FoodFactoryImpl;
import model.food.Nutrient;
import model.geneticcode.GeneImpl;
import model.geneticcode.GeneticCode;
import model.geneticcode.GeneticCodeImpl;
import model.perception.PerceptionImpl;

/**
 * Unit test for Bacteria.
 */
public class TestBacteria {

    private static final String ACTION_EQUALS_MESSAGE = "Action should cost the same";

    /**
     * Test a Bacteria in a non dynamic context.
     */
    @Test
    public void testCreation() {
        final GeneticCode code = new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(),
                TestUtils.getLargeDouble());
        final Bacteria bacteria = new BacteriaImpl(0, new SpeciesBuilder("").build(), code, EnergyImpl.ZERO);

        Action action = new SimpleAction(ActionType.EAT);
        assertEquals(ACTION_EQUALS_MESSAGE, code.getActionCost(action), bacteria.getActionCost(action));
        action = new SimpleAction(ActionType.REPLICATE);
        assertEquals(ACTION_EQUALS_MESSAGE, code.getActionCost(action), bacteria.getActionCost(action));
        action = new SimpleAction(ActionType.NOTHING);
        assertEquals(ACTION_EQUALS_MESSAGE, code.getActionCost(action), bacteria.getActionCost(action));
        action = new DirectionalActionImpl(ActionType.MOVE, Direction.NORTH, 0);
        assertEquals(ACTION_EQUALS_MESSAGE, code.getActionCost(action), bacteria.getActionCost(action));

        assertEquals("Bacteria should not have energy", EnergyImpl.ZERO, bacteria.getEnergy());

        assertEquals("Radius should be the same", code.getRadius(), bacteria.getRadius(),
                TestUtils.getDoubleCompareDelta());
        assertEquals("Perception radius should be the same", code.getPerceptionRadius(), bacteria.getPerceptionRadius(),
                TestUtils.getDoubleCompareDelta());

        assertEquals("Speed should be the same", code.getSpeed(), bacteria.getSpeed(),
                TestUtils.getDoubleCompareDelta());

        assertTrue("Bacteria should be dead with ZERO energy", bacteria.isDead());

        assertFalse("Bacteria should not be replicating", bacteria.isReplicating());

        assertEquals("Species should be the same", new SpeciesBuilder("").build(), bacteria.getSpecies());
    }

    private GeneticCode getRandomCode() {
        return new GeneticCodeImpl(new GeneImpl(), TestUtils.getSmallDouble(), TestUtils.getLargeDouble());
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
        assertFalse("Bacteria should be dead with ZERO energy", bacteria.isDead());

        assertThrows(MissingPerceptionExeption.class, bacteria::getAction);

        bacteria.setPerception(
                new PerceptionImpl(Optional.of(TestUtils.getAFood()), TestUtils.bestDirection(Direction.NORTH)));
        assertTrue("Bacteria should know that there is a food", bacteria.getPerception().getFood().isPresent());
        assertEquals("Percepted food should match with real one", TestUtils.getAFood(),
                bacteria.getPerception().getFood().get());

        assertEquals("Bacteria should want to eat", new SimpleAction(ActionType.EAT), bacteria.getAction());

        bacteria.enterReplicating();
        assertTrue("Bacteria should be replicating", bacteria.isReplicating());
        bacteria.exitReplicating();
        assertFalse("Bacteria should not be replicating", bacteria.isReplicating());
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
        assertEquals("Energies should be equal", correctEnergy, bacteria.getEnergy());

        bacteria.addFood(TestUtils.getAFood());
        Energy gain = EnergyImpl.ZERO;
        for (final Nutrient n : TestUtils.getAFood().getNutrients()) {
            if (code.getEnergyFromNutrient(n).getAmount() > 0) {
                gain = gain
                        .add(code.getEnergyFromNutrient(n).multiply(TestUtils.getAFood().getQuantityFromNutrient(n)));
            }
        }
        correctEnergy = correctEnergy.add(gain);
        assertEquals("Energies should be equal", correctEnergy.getAmount(), bacteria.getEnergy().getAmount(),
                TestUtils.getDoubleCompareDelta());

        bacteria = getTestBacteria(getRandomCode(), TestUtils.getLargeEnergy());

        bacteria.addFood(TestUtils.getAFood());
        assertEquals("Food stored should be what it eats", TestUtils.getAFood(),
                bacteria.getInternalFood(new FoodFactoryImpl()));
    }
}
