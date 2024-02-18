package it.unibo.object_onepiece;

import it.unibo.object_onepiece.model.NavigationSystem;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Compass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;


public class NavigationSystemTest {
    private final NavigationSystem nav = new Compass();
    private static final Position POS1 = new Position(3,2);
    private static final Position POS2 = new Position(4, 2);
    private static final Position POS3 = new Position(2, 2);
    private static final Position POS4 = new Position(3, 3);
    private static final Position POS5 = new Position(3, 1);
  
    private static final Position DPOS1 = new Position(2,1);
    private static final Position DPOS2 = new Position(4,3);
    private static final Position DPOS3 = new Position(4,1);
    private static final Position DPOS4 = new Position(2,3);


    @Test
    void testDirection(){
        assertEquals(CardinalDirection.SOUTH, nav.move(POS2, POS1));
        assertEquals(CardinalDirection.NORTH, nav.move(POS3, POS1));
        assertEquals(CardinalDirection.EAST, nav.move(POS4, POS1));
        assertEquals(CardinalDirection.WEST, nav.move(POS5, POS1));

        assertTrue(List.of(CardinalDirection.NORTH ,CardinalDirection.WEST).contains(nav.move(DPOS1, POS1)));
        assertTrue(List.of(CardinalDirection.SOUTH ,CardinalDirection.EAST).contains(nav.move(DPOS2, POS1)));
        assertTrue(List.of(CardinalDirection.SOUTH ,CardinalDirection.WEST).contains(nav.move(DPOS3, POS1)));
        assertTrue(List.of(CardinalDirection.NORTH ,CardinalDirection.EAST).contains(nav.move(DPOS4, POS1)));
        
    }
}
