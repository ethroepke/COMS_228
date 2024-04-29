package edu.iastate.cs228.hw1;

/**
 * 
 * @author ethanroepke
 *
 * Represents an abstract base class for cells in a Town simulation.
 * This class defines common properties and methods for different cell types.
 * 
 * COMPLETE
 */

public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

		int width = plain.getWidth();
		int length = plain.getLength();

		for (int i = row - 1; i < row + 2; i++) {
			for (int j = col - 1; j < col + 2; j++) {

				if ((i != row || j != col) && i > -1 && i < length && j > -1 && j < width) {
					switch (plain.grid[i][j].who()) {
						case CASUAL:
							nCensus[CASUAL] += 1;
							break;
							
						case RESELLER:
							nCensus[RESELLER] += 1;
							break;
							
						case EMPTY:
							nCensus[EMPTY] += 1;
							
							break;
						case OUTAGE:
							nCensus[OUTAGE] += 1;
							
							break;
						case STREAMER:
							nCensus[STREAMER] += 1;
							
							break;
					}
				}
			}

		}

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town newT);
}
