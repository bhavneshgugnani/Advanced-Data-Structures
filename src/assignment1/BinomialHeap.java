package assignment1;

import java.util.LinkedList;

//This class is the Min-Binomial Heap implementation .
public class BinomialHeap implements DataStructure {

	private HeapNode root;
	private DynamicTable table;// Used to keep track of heaps encountered during
								// removeMin operation

	public BinomialHeap() {
		this.root = null;
		this.table = new DynamicTable();
	}

	// Initialise the binomial heap by inserting in the root circular list and
	// melding .
	public void initialise(int[] data) {
		for (int i = 0; i < data.length; i++)
			insert(data[i]);
	}

	// Inserts the new element by inserting in root circular linked list and
	// updating the root if necessary .
	public void insert(int data) {
		HeapNode node = new HeapNode(data);
		if (this.root == null)
			this.root = node;
		else {
			HeapNode temp;
			if (node.data < this.root.data) {// If new node is smaller than the
												// existing minimum node , make
												// it the root .
				temp = this.root.nextSibling;
				while (temp.nextSibling != this.root) {
					temp = temp.nextSibling;
				}
				temp.nextSibling = node;
				node.nextSibling = this.root;
				this.root = node;
			} else {// Else attach the new node next to root node in circular
					// list .
				temp = this.root;

				HeapNode temp1 = temp.nextSibling;
				temp.nextSibling = node;
				node.nextSibling = temp1;
			}
		}
	}

	// Removes the min element (root) from the Binomial Heap .
	public void removeMin() {
		if (this.root == null) {
			// Heap empty.
			return;
		} else {
			this.table.clear();// Clear table for new remove min operation .
			HeapNode minTree = this.root;
			LinkedList<HeapNode> list = new LinkedList<HeapNode>();
			HeapNode child = minTree.child;
			HeapNode temp1, temp;

			// Next siblings to min tree
			temp = minTree.nextSibling;
			while (temp != this.root) {
				temp1 = temp;
				temp = temp.nextSibling;
				temp1.nextSibling = temp1;
				list.add(temp1);
			}

			// Childrens of min tree
			temp = minTree.child;
			if (temp != null) {
				do {
					temp1 = temp;
					temp = temp.nextSibling;
					temp1.nextSibling = temp1;
					list.add(temp1);
				} while (temp != child);
			}

			// Pairwise combine
			HeapNode smaller = null, larger = null;
			boolean inserted;// Represents if the heap has been meld and added
								// to table . If a heap of same degree exists in
								// table , selected heap is meld with that heap
								// and assigned to smaller variable for melding
								// with another heap of new degree in next
								// iteration of while loop , else inserted into
								// the table .
			for (int i = 0; i < list.size(); i++) {
				inserted = false;
				smaller = list.get(i);
				while (!inserted) {
					larger = this.table.get(smaller.degree);
					if (larger == null) {// In case same degree heap is not
											// present in table , add the
											// current heap to table
						this.table.put(smaller);
						inserted = true;
					} else {// Else meld selected heap with heap of same degree
							// from table
						this.table.remove(larger.degree);
						// Distinguish smaller and larger heap on basis of root
						// data . Smaller is one with smaller data .
						if (smaller.data > larger.data) {
							temp = smaller;
							smaller = larger;
							larger = temp;
						}

						child = smaller.child;
						if (child == null)
							smaller.child = larger;
						else {
							temp = child.nextSibling;
							child.nextSibling = larger;
							larger.nextSibling = temp;
							if (larger.data < child.data) {
								smaller.child = larger;
							}
						}
						smaller.degree += 1;// Increase the degree of melded
											// heap
					}
				}
			}

			// Create circular linkedlist at root from table after all possible
			// meld are complete
			list = this.table.getAll();
			if (list.size() == 0)
				this.root = null;
			else {
				smaller = list.get(0);
				temp = smaller;
				for (int i = 1; i < list.size(); i++) {
					temp1 = list.get(i);
					temp.nextSibling = temp1;
					temp = temp1;
				}
				temp.nextSibling = smaller;

				// Assign the minimum as root from the circular list at created
				// at root from table .
				larger = smaller;
				temp = larger.nextSibling;
				while (temp != larger) {
					if (temp.data < smaller.data)
						smaller = temp;
					temp = temp.nextSibling;
				}
				this.root = smaller;
			}
		}
	}

	// Displays the current Heap using 2 queue method for level order traversal
	// . At any point , one queue stores the current level nodes and other queue
	// stores next level nodes .
	public void display() {
		if (this.root == null) {
			System.out.println("Empty Tree.");
			return;
		}
		QueueImpl currLevel = new QueueImpl();
		QueueImpl nextLevel = new QueueImpl();
		currLevel.enqueue(this.root);
		printLevel(currLevel, 1, nextLevel);
	}

	// Recursive method called for printing level order traversal of heap.
	private void printLevel(QueueImpl currLevel, int level, QueueImpl nextLevel) {
		HeapNode start;
		System.out.print("Level " + level + " : ");
		do {// Dequeue the current level , print it's data and enqueue it's
			// child to next level queue .
			start = (HeapNode) currLevel.dequeue();
			HeapNode temp = start;
			do {// Print all siblings of current heap node and enqueue childrens
				// of each of siblings of the heap node dequed from current
				// level queue
				System.out.print(temp.data);
				System.out.print(" , ");
				if (temp.child != null)
					nextLevel.enqueue(temp.child);
				temp = temp.nextSibling;
			} while (temp != start);
		} while (!currLevel.isEmpty());
		System.out.println();
		if (!nextLevel.isEmpty())// Call this method recursively if no heaps
									// nodes exist in next level .
			printLevel(nextLevel, level + 1, currLevel);
	}

}
