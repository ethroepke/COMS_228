package edu.iastate.cs228.hw1;

/**
 * @author ethanroepke
 *
 * The Empty class represents an empty cell in a town simulation.
 * The empty cells can change state based on certain conditions.
 *
 *COMPLETE
 */

public class Empty extends TownCell{
	public Empty(Town t, int row, int col) {
        super(t, row, col);
    }

    //override method to returnthe state type of the cell
    @Override
    public State who() {
        return State.EMPTY;
    }

    //override method to return the state type of the cell
    @Override
    public TownCell next(Town newT) {

        int[] neighborCensus = new int[5];

        census(neighborCensus);

        if(neighborCensus[EMPTY] + neighborCensus[OUTAGE] <= 1){
            return new Reseller(newT,row,col);
        }
        return new Casual(newT,row,col);
    }
}
