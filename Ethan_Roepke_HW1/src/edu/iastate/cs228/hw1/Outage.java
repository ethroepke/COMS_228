package edu.iastate.cs228.hw1;

/**
 * @author ethanroepke
 *
 * The outage class represents an outage cell in a town simulation.
 * Outage cells change to empty cells
 * 
 * COMPLETE
 */

public class Outage extends TownCell{
	public Outage(Town t, int row, int col) {
        super(t, row, col);
    }

    //override method to return the state type of the cell
    @Override
    public State who() {
        return State.OUTAGE;
    }

    //override method to determine the next state of the cell based on specific rules.
    @Override
    public TownCell next(Town newT) {
        return new Empty(newT,row,col);
    }
}
