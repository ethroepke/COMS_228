package edu.iastate.cs228.hw1;

import java.net.SocketOption;
import java.util.stream.Stream;

/**
 * @author ethanroepke
 *
 * The casual class represents a Casual cell in a Town simulation.
 * Casual cells can change state based on certain conditions.
 * 
 * COMPLETE
 */

public class Casual extends TownCell{
	
	/*
	 * @param t  The town object to which the cell belongs
	 * @param row The row index of this cell
	 * @param col The column index of this cell
	 */
	public Casual(Town t, int row, int col) {
        super(t, row, col);
    }

	//Override method to return the State of cell
    @Override
    public State who() {
        return State.CASUAL;
    }

    //Override method to determine the next state of the cell
    @Override
    public TownCell next(Town newT) {

        int[] neighborCensus = new int[5];

        census(neighborCensus);

        if(neighborCensus[EMPTY] + neighborCensus[OUTAGE] <= 1) {
            return new Reseller(newT,row,col);
        }
        
        else if(neighborCensus[RESELLER] > 0) {
            return new Outage(newT,row,col);
        }
        
        else if(neighborCensus[STREAMER] > 0 || neighborCensus[CASUAL] >= 5) {
            return new Streamer(newT,row,col);
        }
        
        else {
            return new Casual(newT,row,col);
        }



    }
}
