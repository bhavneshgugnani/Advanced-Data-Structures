package assignment1;

import java.util.LinkedList;

//A data structure implemented to have a dynamic space allocation with almost constant amoritsed cost for access and storage of heap nodes for maintaining table for Binomial Heap's delete Min operation .
public class DynamicTable {
	private HeapNode[] array;

	// Default size defined as 5 for array . Would be doubled everytime memory
	// is not enough .
	public DynamicTable() {
		this.array = new HeapNode[5];
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}

	// Inserts new node into table with index as degree of heap node .
	public void put(HeapNode node) {
		while (this.array.length - 1 < node.degree)
			doubleSize();
		array[node.degree] = node;
	}

	// Returns the heap node with degree same as argument by returning the same
	// indexed element in internal array .
	public HeapNode get(int degree) {
		while (this.array.length - 1 < degree)
			doubleSize();
		return array[degree];
	}

	// Returns all Heap nodes stored in the table .
	public LinkedList<HeapNode> getAll() {
		LinkedList list = new LinkedList();
		for (HeapNode node : array)
			if (node != null)
				list.add(node);
		return list;
	}

	// Removes the heap node from table with degree provided as argument .
	public void remove(int degree) {
		this.array[degree] = null;
	}

	// Clears the table for next remove min operation .
	public void clear() {
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}

	// Doubles the internal storage size of the array for storing heap node
	// references in case memory is not enough .
	private void doubleSize() {
		HeapNode[] array1 = new HeapNode[2 * this.array.length];
		int i;
		for (i = 0; i < this.array.length; i++)
			array1[i] = this.array[i];
		this.array = array1;
		for (; i < this.array.length; i++)
			this.array[i] = null;
	}
}
