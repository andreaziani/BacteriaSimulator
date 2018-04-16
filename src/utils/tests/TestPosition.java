package utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import model.state.Position;
import model.state.PositionImpl;
/**
 * Test Class for position.
 * 
 *
 */
public class TestPosition {
    /**
     * Test equals of Position.
     */
    @Test
    public void testPosition() {
        final Position position1 = new PositionImpl(2.0, 1.0);
        final Position position2 = new PositionImpl(2.0, 1.0);
        final Position position3 = new PositionImpl(2.0, 3.0);
        assertEquals("Positions must be equals", position1, position2);
        assertNotEquals("Positions are not equals", position2, position3);
    }

}
