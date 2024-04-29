package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author ethanroepke
 */

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean add(E item) {
		if (item == null) {
			throw new NullPointerException();
		}

		Node newNode;

		if (size == 0 || tail.previous.count == nodeSize) {
			newNode = new Node();
			newNode.addItem(item);
			head.next = n;
			newNode.next = tail;
			newNode.previous = head;
			tail.previous = n;
		}

		else {
			newNode = tail.previous;
		}

		newNode.addItem(item);
		size++;
		return true;
	}

	@Override
	public void add(int pos, E item) {

		/*
		 * Check to see if we are out of bounds If out lsits is empty, add new node
		 */
		if (pos > size || pos < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (head.next == tail || tail.previous.count == nodeSize) {
			add(item);
			return;
		}

		/*
		 * Find the node and offset at the specified position
		 */
		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;

		/*
		 * If offset is 0 We will check the previous node has less than nodeSize Reach
		 * end then add item to end
		 */
		if (offset == 0 && (temp.previous.count < nodeSize || temp == tail)) {
			temp.previous.addItem(item);
		} else if (temp.count < nodeSize) {
			temp.addItem(offset, item);
		} else {
			Node newTemp = new Node();
			int half = nodeSize / 2;

			for (int i = half; i < nodeSize; i++) {
				newTemp.addItem(temp.data[i]);
				temp.removeItem(half);
			}

			Node predecessor = temp.next;

			temp.next = newTemp;
			newTemp.previous = temp;
			newTemp.next = predecessor;
			predecessor.previous = newTemp;

			if (offset <= half) {
				temp.addItem(offset, item);
			} else {
				newTemp.addItem(offset - half, item);
			}
		}
		size++;
	}

	@Override
	public E remove(int pos) {
		if (pos >= size || pos < 0) {
			throw new IndexOutOfBoundsException();
		}

		NodeInfo info = find(pos);
		Node temp = info.node;
		int offset = info.offset;
		E nodeValue = temp.data[offset];

		if (temp.next == tail && temp.count == 1) {
			Node prev = temp.previous;
			prev.next = tail;
			tail.previous = prev;
			temp = null;
		} else if (temp.next == tail || temp.count > nodeSize / 2) {
			temp.removeItem(offset);
		} else {
			temp.removeItem(offset);
			Node successor = temp.next;
			if (successor.count > nodeSize / 2) {
				temp.addItem(successor.data[0]);
				successor.removeItem(0);
			} else {
				for (int i = 0; i < successor.count; i++) {
					temp.addItem(successor.data[i]);
				}
				temp.next = successor.next;
				successor.next.previous = temp;
				successor = null;
			}
		}
		size--;
		return nodeValue;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		E[] dataSorter = (E[]) new Comparable[size];
		int arrayCount = 0;
		Node temp = head.next;

		/*
		 * Move through the list and copy elements to dataSorter
		 */
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				dataSorter[arrayCount++] = temp.data[i];
			}
			temp = temp.next;
		}

		// Reset the list
		head.next = tail;
		tail.previous = head;

		// Sort the dataSorter array
		insertionSort(dataSorter, new SortComparator());

		// Add sorted elements back to the list
		for (E item : dataSorter) {
			add(item);
		}
	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		// Create an array to store the elements for sorting
		E[] dataSorter = (E[]) new Comparable[size];
		int tempIndex = 0;
		Node temp = head.next;

		// Move through the list and copy elements to the dataSorter array
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				dataSorter[tempIndex++] = temp.data[i];
			}
			temp = temp.next;
		}

		// Reset the list
		head.next = tail;
		tail.previous = head;

		// Sort the dataSorter array in reverse order
		bubbleSort(dataSorter);

		size = 0;

		// Add sorted elements back to the list
		for (int i = 0; i < dataSorter.length; i++) {
			add(dataSorter[i]);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 * 
	 *             DO NOT MESS WITH
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 * 
		 *             DO NOT MESS WITH
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + count + " to
			// node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 * 
		 *               DO NOT MESS WITH
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 * 
		 *               DO NOT MESS WITH
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	private class StoutListIterator implements ListIterator<E> {
		int pos;
		int direction;
		static final int BEHIND = -1;
		static final int NONE = 0;
		static final int AHEAD = 1;
		public E[] dataList;

		/*
		 * Default constructor
		 */
		public StoutListIterator() {
			pos = 0;
			direction = BEHIND;
			dataSort();
		}

		/*
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			pos = 0;
			direction = BEHIND;
			dataSort();
		}

		@Override
		public boolean hasNext() {
			if (pos >= size)
				return false;
			else {
				return true;
			}
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			direction = AHEAD;
			return dataList[pos++];
		}

		@Override
		public void remove() {
			if (direction == AHEAD) {
				StoutList.this.remove(pos - 1);
				dataSort();
				direction = BEHIND;
				pos--;
				if (pos < 0)
					pos = 0;
			} else if (direction == NONE) {
				StoutList.this.remove(pos);
				dataSort();
				direction = BEHIND;
			} else {
				throw new IllegalStateException();
			}
		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//

		/*
		 * Puts the StoutList data into an array and stores in dataList
		 */
		private void dataSort() {
			dataList = (E[]) new Comparable[size];
			int tempIndex = 0;
			Node temp = head.next;

			// Move through the list and copy elements to the dataList array
			while (temp != tail) {
				for (int i = 0; i < temp.count; i++) {
					dataList[tempIndex++] = temp.data[i];
				}
				temp = temp.next;
			}
		}

		/**
		 * @return true if there is a previous element, false otherwise.
		 */

		@Override
		public boolean hasPrevious() {
			if (pos <= 0)
				return false;
			else
				return true;
		}

		@Override
		public E previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			direction = NONE;
			pos--;
			return dataList[pos];
		}

		/**
		 * Returns the previous element in the list
		 *
		 * @throws NoSuchElementException if there is no previous element in the list.
		 * @return the previous element in the list.
		 */
		@Override
		public int nextIndex() {
			return pos;
		}

		/**
		 * @return previous elmement in the list with cursor moved back one
		 */
		@Override
		public int previousIndex() {
			return pos - 1;
		}

		/**
		 * @return the index of the previous element in the list.
		 */
		@Override
		public void set(E e) {
			if (direction == AHEAD) {
				// Find the node and offset at the position before the current position
				NodeInfo nodeInfo = find(pos - 1);
				// Update the value at the specified position in StoutList
				nodeInfo.node.data[nodeInfo.offset] = e;
				// Update the value at the specified position in dataList
				dataList[pos - 1] = e;
			} else if (direction == NONE) {
				// Find the node and offset at the current position
				NodeInfo nodeInfo = find(pos);
				// Update the value at the specified position in StoutList
				nodeInfo.node.data[nodeInfo.offset] = e;
				// Update the value at the specified position in dataList
				dataList[pos] = e;
			} else {
				throw new IllegalStateException();
			}
		}

		/**
		 * Inserts the specified element at the current cursor position and advances the
		 * cursor.
		 *
		 * @param e the element to be added. Must not be null.
		 * @throws NullPointerException if the specified element is null.
		 */
		@Override
		public void add(E e) {
			if (e == null)
				throw new NullPointerException();

			StoutList.this.add(pos, e);
			pos++;
			dataSort();
			direction = BEHIND;
		}

	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) {
			E temp = arr[i];
			int j = i - 1;

			while (j >= 0 && comp.compare(arr[j], temp) > 0) {
				arr[j + 1] = arr[j];
				j--;

			}
			arr[j + 1] = temp;
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		E temp;

		for (int i = arr.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (arr[j].compareTo(arr[j + 1]) <= 0) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

	public class SortComparator<E extends Comparable<E>> implements Comparator<E> {

		@Override
		public int compare(E a, E b) {
			return a.compareTo(b);
		}

	}

	/**
	 * Private class representing information about a node along with its offset.
	 */
	private class NodeInfo {
		/**
		 * The node associated with this NodeInfo.
		 */
		public Node node;
		/**
		 * The offset value associated with this NodeInfo.
		 */
		public int offset;

		/**
		 * Constructs a new NodeInfo with the specified node and offset.
		 *
		 * @param node   the node to be associated with this NodeInfo.
		 * @param offset the offset value to be associated with this NodeInfo.
		 */
		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * Finds a node at a specified position in the list and returns its information.
	 *
	 * @param pos the position of the node to be found.
	 * @return a NodeInfo object containing information about the found node and its
	 *         offset.
	 */
	private NodeInfo find(int pos) {
		Node temp = head.next;
		int current = 0;

		// Traverse the list until reaching the tail node
		while (temp != tail) {
			// If the current node doesn't cover the position, move to the next node
			if (current + temp.count <= pos) {
				current += temp.count;
				temp = temp.next;
				continue;
			}

			// Calculate the offset within the current node
			int offset = pos - current;
			// Create a NodeInfo object to store the node and offset
			NodeInfo nodeInfo = new NodeInfo(temp, offset);
			return nodeInfo;
		}

		// Return null if the position is out of bounds
		return null;
	}
}