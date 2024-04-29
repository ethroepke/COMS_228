package edu.iastate.cs228.hw1;

/**
 * @author ethanroepke
 *
 * The Streamer class represents a streamer cell in a town simulation.
 * Streamer cells can change state based on certain conditions
 *
 *COMPLETE
 */

public class Streamer extends TownCell{
	public Streamer(Town t, int row, int col) {
        super(t, row, col);
    }

    //override method to return the state type of the cell
    @Override
    public State who() {
        return State.STREAMER;
    }


    //override method to determine the next state of the cell based on specific rules.
    @Override
    public TownCell next(Town newT) {

        int[] neighborCensus = new int[5];

        census(neighborCensus);

        if(neighborCensus[EMPTY] + neighborCensus[OUTAGE] <= 1){
            return new Reseller(newT,row,col);
        }
        
        else if(neighborCensus[RESELLER] > 0){
            return new Outage(newT,row,col);
        }
        
        else if(neighborCensus[OUTAGE] > 0){
            return new Empty(newT,row,col);
        }

        return new Streamer(newT,row,col);
    }
}
