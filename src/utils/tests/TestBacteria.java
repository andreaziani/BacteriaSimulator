package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import model.bacteria.Species;
import model.bacteria.SpeciesBuilder;

/**
 * Unit test for the Bacteria and Species of the model.
 */
public class TestBacteria {
    private static final String NAME_1 = "name";
    private static final String NAME_2 = "new name";

    /**
     * Test corrected functioning of build and reset of SpeciesBuilder.
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
}
