package edu.iastate.cs228.hw1;

/**
 * @author ethanroepke
 *
 * The Reseller class represents a reseller cell in a town simulation
 * Reseller cells can change state based on certain conditions.
 *
 *COMPLETE
 */

public class Reseller extends TownCell{
	public Reseller(Town t, int row, int col) {
        super(t, row, col);
    }
   
	//override method to return the state type of the cell
    @Override
    public State who() {
        return State.RESELLER;
    }

    /*
     * override method to determine the next state of the cell
     * based on specific conditions
    */
    @Override
    public TownCell next(Town tNew) {

        int[] neighborCensus = new int[5];

        census(neighborCensus);

        if(neighborCensus[CASUAL] <= 3 || neighborCensus[EMPTY] >= 3){
            return new Empty(tNew,row,col);
        }
        
        if(neighborCensus[CASUAL] >= 5){
            return new Streamer(tNew,row,col);
        }
        
        else {
        	return new Reseller(tNew,row,col);
        }
    }
}
