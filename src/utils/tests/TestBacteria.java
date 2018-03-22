package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;
import model.bacteria.SpeciesManager;
import model.bacteria.SpeciesManagerImpl;
import model.bacteria.behavior.BehaviorDecoratorOption;
import model.bacteria.behavior.decisionmaker.DecisionMakerOption;
import utils.exceptions.AlreadyExistingSpeciesExeption;

/**
 * Unit test for the Bacteria and Species of the model.
 */
public class TestBacteria {
    private static final String NAME_1 = "name";
    private static final String NAME_2 = "new name";

    /**
     * Tests corrected functioning of build and reset of SpeciesBuilder.
     */
    @Test
    public void testBuildSpecies() {
        final SpeciesBuilder builder = new SpeciesBuilder(NAME_1);
        Species species = tryBuild(builder);
        assertEquals(NAME_1, species.getName());

        assertThrows(IllegalStateException.class, builder::build);
        builder.reset(NAME_2);
        species = tryBuild(builder);
        assertEquals(NAME_2, species.getName());
    }

    private Species tryBuild(final SpeciesBuilder builder) {
        Species species = null;
        try {
            species = builder.build();
        } catch (Exception e) {
            fail("should have worked");
        }
        return species;
    }

    /**
     * Tests the insertion of Species in a SpeciesManager.
     */
    @Test
    public void testSpeciesManager() {
        final SpeciesManager manager = new SpeciesManagerImpl();
        assertTrue(manager.getSpecies().isEmpty());
        manager.addSpecies(new SpeciesBuilder(NAME_1).build());
        assertFalse(manager.getSpecies().isEmpty());
        assertEquals(NAME_1, manager.getSpecies().stream().findFirst().get().getName());

        assertThrows(AlreadyExistingSpeciesExeption.class, () -> manager.addSpecies(new SpeciesBuilder(NAME_1).build()));
        assertEquals(1, manager.getSpecies().size());

        final SpeciesBuilder builder = new SpeciesBuilder(NAME_1);
        builder.addDecisionBehaiorDecorator(BehaviorDecoratorOption.COST_FILTER);
        builder.addDecisionMaker(DecisionMakerOption.ALWAYS_EAT);
        assertThrows(AlreadyExistingSpeciesExeption.class, () -> manager.addSpecies(builder.build()));
        assertEquals(1, manager.getSpecies().size());

        manager.addSpecies(new SpeciesBuilder(NAME_2).build());
        assertEquals(2, manager.getSpecies().size());
    }
}
