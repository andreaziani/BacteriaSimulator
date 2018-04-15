package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.Test;

import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.bacteria.SpeciesManager;
import model.bacteria.SpeciesManagerImpl;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.CostFilterDecisionBehavior;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import utils.exceptions.AlreadyExistingSpeciesExeption;

/**
 * Unit test for the Species building and storing in the manager.
 */
public class TestSpecies {
    private static final String NAME_1 = "name";
    private static final String NAME_2 = "new name";

    /**
     * Tests corrected functioning of build, reset of SpeciesBuilder and insertion
     * of behavior.
     */
    @Test
    public void testBuildSpecies() {
        final SpeciesBuilder builder = new SpeciesBuilder(NAME_1);
        Species species = tryBuild(builder);
        assertEquals("Name should be " + NAME_1, NAME_1, species.getName());

        assertThrows(IllegalStateException.class, builder::build);
        builder.reset(NAME_2);
        species = tryBuild(builder);
        assertEquals("Name should be " + NAME_2, NAME_2, species.getName());

        species = builder.reset(NAME_1)
                         .addDecisionBehaiorDecorator(BehaviorDecoratorOption.COST_FILTER)
                         .addDecisionMaker(DecisionMakerOption.ALWAYS_EAT).addDecisionMaker(DecisionMakerOption.NO_MOVEMENT)
                         .addDecisionMaker(DecisionMakerOption.NO_REPLICATION).build();

        assertEquals(
                "The species's behavior should be a costFilter with ALWAYS_EAT, NO_MOVEMENT and NO_REPLICATION options",
                new CostFilterDecisionBehavior(
                        TestUtils.baseBehaviorFromOptions(Arrays.asList(DecisionMakerOption.ALWAYS_EAT,
                                DecisionMakerOption.NO_MOVEMENT, DecisionMakerOption.NO_REPLICATION))),
                species.getBehavior());
    }

    private Species tryBuild(final SpeciesBuilder builder) {
        Species species = null;
        try {
            species = builder.build();
        } catch (Exception e) {
            fail("Should have worked");
        }
        return species;
    }

    /**
     * Tests the insertion of Species in a SpeciesManager.
     */
    @Test
    public void testSpeciesManager() {
        final SpeciesManager manager = new SpeciesManagerImpl();
        assertTrue("The manager should be empty of species", manager.getSpecies().isEmpty());
        manager.addSpecies(new SpeciesBuilder(NAME_1).build());
        assertFalse("The manager should not be empty of species", manager.getSpecies().isEmpty());
        assertEquals("The name of the only species in the manager should be " + NAME_1, NAME_1,
                manager.getSpecies().stream().findFirst().get().getName());

        assertThrows(AlreadyExistingSpeciesExeption.class,
                () -> manager.addSpecies(new SpeciesBuilder(NAME_1).build()));
        assertEquals("The manager should have one species", 1, manager.getSpecies().size());

        assertThrows(AlreadyExistingSpeciesExeption.class,
                () -> manager.addSpecies(
                        new SpeciesBuilder(NAME_1).addDecisionBehaiorDecorator(BehaviorDecoratorOption.COST_FILTER)
                                                  .addDecisionMaker(DecisionMakerOption.ALWAYS_EAT)
                                                  .build()));
        assertEquals("The manager should have one species", 1, manager.getSpecies().size());

        manager.addSpecies(new SpeciesBuilder(NAME_2).build());
        assertEquals("The manager should have two species", 2, manager.getSpecies().size());
    }
}
