package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 
 * @author ethanroepke
 *
 */

public class ISPBusinessTest {
	Town t = new Town(2,4);

	//Testing the profit return 
    @Test
    void test() {
        t.randomInit(10);
        assertEquals(ISPBusiness.getProfit(t),1);
    }
}
