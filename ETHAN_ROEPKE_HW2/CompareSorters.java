package edu.iastate.cs228.hw2;

/**
 *  
 * @author Ethan Roepke
 *
 *
 *COMPLETE
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	private static boolean keyValue = true;
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException{		
		Point[] pts = null;
		PointScanner[] scanners = new PointScanner[4];
	
		int trial = 1;
		
		int input = 0;
		Scanner scnr = new Scanner(System.in);
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		while (input != 3) {
			System.out.print("Trial " + trial + ": ");
			input = scnr.nextInt();
			// Random integers
			if (input == 1) {
				System.out.print("Enter number of random points: ");
				int num = scnr.nextInt();
				pts = generateRandomPoints(num, new Random());
				scanners[0] = new PointScanner(pts, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(pts, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(pts, Algorithm.MergeSort);
				scanners[3] = new PointScanner(pts, Algorithm.QuickSort);
			}
			// File input
			else if (input == 2) {
				System.out.println("Points from a file");
				System.out.print("File name: ");
				String file = scnr.next();
				scanners[0] = new PointScanner(file, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(file, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(file, Algorithm.MergeSort);
				scanners[3] = new PointScanner(file, Algorithm.QuickSort);
			}
			// Breaks loop if user enters anything else besides 1 or 2
			else {
				break;
			}
			System.out.println("algorithm size time (ns)");
			System.out.println("----------------------------------");
			for(int i = 0; i < scanners.length; i++) {
				
				scanners[i].scan();
				System.out.println(scanners[i].stats());
				
			}
			System.out.println("----------------------------------");
			scanners[3].writeMCPToFile();
			
			trial++;
			System.out.println();
		}
		scnr.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException{ 
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		Point[] p = new Point[numPts];
		for (int i = 0; i < p.length; i++) {
			int x= rand.nextInt(101) - 50;
			int y = rand.nextInt(101) - 50;
			p[i] = new Point(x, y);
		}
		return p;

	}
}
