package assignment1;

// This class is structure of each node in Min-Leftist Tree . It extends Node class to store input .
public class TreeNode extends Node {

	public TreeNode(int data) {
		super(data);// Call constructor of Node class .
		this.sValue = 1;
		this.leftChild = null;
		this.rightChild = null;
	}

	int sValue;
	TreeNode leftChild;
	TreeNode rightChild;
}
