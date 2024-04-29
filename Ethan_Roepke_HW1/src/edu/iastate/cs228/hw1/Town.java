package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author ethanroepke
 *
 *COMPLETE
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		//TODO: Write your code here.
		this.length = length;
		this.width = width;
		
		grid = new TownCell[width][length];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);

		/*
		 * Try Catch create for FileNotFound Exception
		 */
		try {
			Scanner scan = new Scanner(file);
			this.width = scan.nextInt();
			this.length = scan.nextInt();

			grid = new TownCell[width][length];
			while(scan.hasNextLine()) {
				
				/*
				 * Loops the grid constantly and then updates based on condition
				 */
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < length; y++) {
						String cellType = scan.next();
						if ("C".equals(cellType)) {
						    grid[x][y] = new Casual(this, x, y);
						} 
						
						else if ("S".equals(cellType)) {
						    grid[x][y] = new Streamer(this, x, y);
						} 
						
						else if ("R".equals(cellType)) {
						    grid[x][y] = new Reseller(this, x, y);
						} 
						
						else if ("E".equals(cellType)) {
						    grid[x][y] = new Empty(this, x, y);
						} 
						
						else if ("O".equals(cellType)) {
						    grid[x][y] = new Outage(this, x, y);
						}

					}
				}
			}

			scan.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Invalid Path Entered!" + e.toString());
		}
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		//double for loop and else if statement to create a new object
		for(int x = 0; x < width; x++){

			for(int y = 0; y < length; y++){
				
				int randval = rand.nextInt(5);
				
				if (randval == TownCell.CASUAL) {
					
				    grid[x][y] = new Casual(this, x, y);
				} 
				
				else if (randval == TownCell.STREAMER) {
				    grid[x][y] = new Streamer(this, x, y);
				} 
				
				else if (randval == TownCell.RESELLER) {
				    grid[x][y] = new Reseller(this, x, y);
				} 
				
				else if (randval == TownCell.EMPTY) {
				    grid[x][y] = new Empty(this, x, y);
				} 
				
				else {
					grid[x][y] = new Outage(this, x, y);
				}

			}
		}


	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		/*
		 * initialize s and double for loop to get size of grid so know
		 * when to print new line
		 */
		String s = "";
		for(int x = 0; x < width; x++){
			for(int y = 0; y < length; y++){
				s += grid[x][y].who().toString().charAt(0) + " ";
			}
			s += '\n';
		}
		return s;
	}
}