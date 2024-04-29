package iastate.cs228.hw4;

import java.util.ArrayList;

/**
 * @author ethanroepke
 */

public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	public static ArrayList<Integer> bitsInCode = new ArrayList<>();
	private static int staticCharIdx = 0;

	/*
	 * Constructor to build the message tree from an encoding string
	 */
	public MsgTree(String encodingString) {
		payloadChar = encodingString.charAt(staticCharIdx);
		staticCharIdx += 1;
		left = new MsgTree(encodingString.charAt(staticCharIdx));
		/*
		 * If the payloadChar of the left subtree is '^', recursively create the left
		 * subtree
		 */
		if (left.payloadChar == '^') {
			left = new MsgTree(encodingString);
		}

		staticCharIdx += 1;
		right = new MsgTree(encodingString.charAt(staticCharIdx));
		/*
		 * If the payloadChar of the right subtree is '^', recursively create the right
		 * subtree
		 */
		if (right.payloadChar == '^') {
			right = new MsgTree(encodingString);
		}
	}

	/*
	 * Constructor to create a leaf node with a given payload character
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
	}

	/*
	 * Method to print the codes (binary representations) of characters in the
	 * message tree
	 */
	public static void printCodes(MsgTree root, String code) {
		if (root == null) {
			return;
		}
		/*
		 * If the current node is not a leaf (payloadChar is not '^')
		 */
		if (root.payloadChar != '^') {
			if (root.payloadChar == '\n') {
				System.out.println("\\n" + "         " + code);
				bitsInCode.add(code.length());
			} else {
				System.out.println(root.payloadChar + "          " + code);
				bitsInCode.add(code.length());
			}
		}
		// Recursively traverse the left subtree, adding '0' to the code
		printCodes(root.left, code + "0");
		// Recursively traverse the right subtree, adding '1' to the code
		printCodes(root.right, code + "1");
	}
}