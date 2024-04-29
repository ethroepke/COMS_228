
package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author ethanroepke
 *
 * The ISPBusiness class performs a simulation over a grid plain with cells occupied by different TownCell types.
 * It updates the grid for billing cycles, calculates profits, and displays the final profit percentage.
 * The user can choose to specify elements of the grid via an input file or generate it randomly.
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 */
	public static Town updatePlain(Town OldT) {
		Town newT = new Town(OldT.getLength(), OldT.getWidth());
		 // Double loop to iterate over the rows and columns of the grid
		for(int x = 0; x < OldT.getWidth(); x++){
			for(int y = 0; y < OldT.getLength(); y++){
				// Get the next state of the current cell and update it in the new grid
				newT.grid[x][y] = OldT.grid[x][y].next(newT);
			}
		}
		
		return newT;
	}
	
	/**
	 *  Calculates and returns the profit for the current state in the town grid.
	 */
	public static int getProfit(Town town) {
		int C = 0;
		
		/**
		* Double for loop to iterate through the grid's width and length,
		* counting CASUAL customer cells and accumulating their count in 'C'.
		*/
		for(int x = 0; x < town.getWidth(); x++) {
			for (int y = 0; y < town.getLength(); y++) {
				// Check if the current cell in the grid is of type CASUAL	
				if(town.grid[x][y].who() == State.CASUAL){
						C+=1;
				}
			}
		}
		
		return C;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		//TODO: Write your code here.
		int UserInput;
		double Profit;
		final int BillingCycle = 12;

		Scanner scan = new Scanner(System.in);

		//Checking if user is updating a file or generating a random seed
		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
		UserInput = scan.nextInt();
		Town t = null;

		if (UserInput == 1) {
			String filePath = "";
			try {
				//Checking user to update file name and to create a file object
				System.out.println("Please Enter The File Name or Path!");
				scan.nextLine();
				filePath = scan.nextLine();
				File file = new File(filePath);
				t = new Town(filePath);
			}
				
			catch (FileNotFoundException e){
				System.out.println("Hey looks like you entered in a invalid file path!" + e.toString());
			}
		}

		// If the user chose option 2, populate the grid randomly with specified rows, columns, and seed
		if(UserInput == 2){
			int seed;
			int row;
			int col;
			System.out.println("Enter rows, cols and the seed integer seperated by spaces.");
			row = scan.nextInt();
			col = scan.nextInt();
			seed = scan.nextInt();
			t = new Town(row,col);
			t.randomInit(seed);
		}
				
		double profit = 0.0;
		
		for(int month = 0; month < 12; month++){
			//updates profit every month
			profit += (getProfit(t) / ((double)t.getWidth() * (double)t.getLength())) * 100;
			t = updatePlain(t);
		}
		
		profit = profit / 12;
		System.out.printf("%.2f%c",profit,'%');

	}
}
