package edu.iastate.cs228.hw2;

/**
 * 
 * @author  Ethan Roepke
 *
 *
 *WRITEMCPTOFILE
 *
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    	
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException{
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			Point p = new Point(pts[i]);
			points[i] = p;
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException{
		sortingAlgorithm = algo;
		
		File file = new File(inputFileName);
		// Scanner for getting points
		int length = 0;
		
		try (Scanner scnr = new Scanner(file)){
			
			int numCount = 0;
			while (scnr.hasNextInt()) {
				scnr.nextInt();
				numCount++;
			}
			
			if (numCount % 2 != 0) {
				throw new InputMismatchException();
			}
			length = numCount / 2;
			scnr.close();
		}
		catch(FileNotFoundException e) {
			
			throw new FileNotFoundException();
		}
		
		Scanner scnr2 = new Scanner(file); 
		points = new Point[length];
		for (int i = 0; i < length; i++) {
			points[i] = new Point(scnr2.nextInt(), scnr2.nextInt());
		}
		
		scnr2.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan(){ 
		AbstractSorter aSorter = null;

		if(sortingAlgorithm.equals(Algorithm.SelectionSort)){
			aSorter = new SelectionSorter(points);
		}
		
		else if(sortingAlgorithm.equals(Algorithm.MergeSort)){
			aSorter = new MergeSorter(points);
		}
		
		else if(sortingAlgorithm.equals(Algorithm.InsertionSort)){
			aSorter = new InsertionSorter(points);
		}
		
		else if(sortingAlgorithm.equals(Algorithm.QuickSort)){
			aSorter = new QuickSorter(points);
		}
		
		Point xPt, yPt; 
		int x, y; 
		long xStart = 0;
		long xTime = 0;
		long yStart = 0;
		long yTime = 0;
		
		aSorter.setComparator(0);
		xStart = System.nanoTime();
		aSorter.sort();
		xTime = System.nanoTime() - xStart;
		xPt = aSorter.getMedian();
		x = xPt.getX();
		
		aSorter.setComparator(1);
		yStart = System.nanoTime();
		aSorter.sort();
		yTime = System.nanoTime() - yStart;
		yPt = aSorter.getMedian();
		y = yPt.getY();
		

		medianCoordinatePoint = new Point(x, y);
		scanTime = xTime + yTime;
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats(){
		return sortingAlgorithm + " " + points.length + " " + scanTime;
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString(){
		String result = "";
		for (int i = 0; i < points.length; i++) {
			result += points[i].getX() + " " + points[i].getY() + "\n";
		}
		return result;
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException{
		String outputFileName = null;
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			outputFileName = "InsertionSort.txt";
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			outputFileName = "MergeSort.txt";
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			outputFileName = "QuickSort.txt";
		} else if (sortingAlgorithm == Algorithm.SelectionSort) {
			outputFileName = "SelectionSort.txt";
		}
		
		//Stores the median coordinate point in the corresponding file
		File file = new File(outputFileName);
		PrintWriter print = new PrintWriter(file);
		print.write(medianCoordinatePoint.toString());
		print.close();
	}	

	

		
}
