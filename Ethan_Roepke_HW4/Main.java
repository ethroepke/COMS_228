package iastate.cs228.hw4;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ethanroepke
 */

public class Main {
	public static int position = 0;

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		// Prompt the user to enter the filename to decode
		System.out.println("Please enter filename to decode: ");
		String fileName = scnr.next();

		ArrayList<String> fileLines = fileReader(fileName);

		// Construct the encoding string and the code string from the file contents
		String encodingString = "";
		for (int i = 0; i < fileLines.size() - 2; i++) {
			encodingString += fileLines.get(i) + '\n';
		}
		encodingString += fileLines.get(fileLines.size() - 2);
		String codeString = fileLines.get(fileLines.size() - 1);

		// Build the encoding tree from the encoding string
		MsgTree encodingTree = new MsgTree(encodingString);

		// Print the character codes
		System.out.println("character code\n-------------------------");
		encodingTree.printCodes(encodingTree, "");
		System.out.println("-------------------------\nMESSAGE:");

		// Decode the message using the encoding tree and code string
		while (position < codeString.length()) {
			decode(encodingTree, codeString);
		}

		// Calculate the total number of bits used
		double numBits = 0;
		for (int i = 0; i < encodingTree.bitsInCode.size(); i++) {
			numBits += encodingTree.bitsInCode.get(i);
		}
	}

	// Method to read lines from a file and return them as an ArrayList
	public static ArrayList fileReader(String fileName) {
		ArrayList<String> fileLines = new ArrayList<String>();

		try {
			File file = new File(fileName);
			Scanner scnr = new Scanner(file);

			// Read each line from the file and add it to the ArrayList
			while (scnr.hasNextLine()) {
				fileLines.add(scnr.nextLine());
			}
			scnr.close();
		} catch (Exception e) {
			System.out.println("ERROR: File not found. Enter path name correctly.");
		}
		return fileLines;
	}

	// Method to decode the message using the encoding tree and code string
	public static void decode(MsgTree codes, String msg) {
		while (codes.left != null && codes.right != null) {
			// Traverse the encoding tree based on the current character in the code string
			if (msg.charAt(position) == '0') {
				codes = codes.left;
			} else {
				codes = codes.right;
			}
			position++;
		}
		// Print the decoded character
		System.out.print(codes.payloadChar);
	}
}