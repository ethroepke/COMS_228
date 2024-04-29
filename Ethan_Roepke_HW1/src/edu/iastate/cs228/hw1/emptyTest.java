package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 
 * @author ethanroepke
 *
 */

public class emptyTest {

    Town t = new Town(2,4);
    Empty e = new Empty(t, 2, 2);

    //Testing if who() returns the right state
    @Test
    void test() {
        assertEquals(e.who(), State.EMPTY);
    }
}