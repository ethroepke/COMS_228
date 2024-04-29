package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 
 * @author ethanroepke
 *
 */

public class casualTest {
    Town t = new Town(5,4);
    Casual c = new Casual(t, 2, 2);

    //Testing if who() returns the right state
    @Test
    void test() {
            assertEquals(c.who(), State.CASUAL);
    }
}
