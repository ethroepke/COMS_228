package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 
 * @author ethanroepke
 *
 */

public class townTest {
	Town t = new Town(2,4);

	//Testing the the size of length
    @Test
    void test() {
    assertEquals(t.getLength(),2);
    }
}