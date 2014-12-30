package assignment1;

//This class provide structure of each node in Binomial Heap . It extends Node class which has property data store input . 
class HeapNode extends Node {

	public HeapNode(int data) {
		super(data);// Call constructor of Node class to store input .
		this.degree = 0;
		this.nextSibling = this;
		this.child = null;
	}

	int degree;
	HeapNode nextSibling;
	HeapNode child;
}
