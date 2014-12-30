package assignment1;

// This class is the implementation of Min-Leftist Tree .
public class MinLeftistTree implements DataStructure {
	private TreeNode root;

	public MinLeftistTree() {
		this.root = null;
	}

	// Initialises the Min-Leftist Tree by inserting all elements in queue and
	// melding them pairwise .
	public void initialise(int[] input) {
		QueueImpl queue = new QueueImpl();
		TreeNode node1, node2;
		for (int in : input) {
			node1 = new TreeNode(in);
			queue.enqueue(node1);
		}
		while (queue.size() > 1) {
			node1 = (TreeNode) queue.dequeue();
			node2 = (TreeNode) queue.dequeue();
			queue.enqueue(meld(node1, node2));
		}
		this.root = (TreeNode) queue.dequeue();
	}

	// Remove min element (root) from tree and melding it's childrens .
	public void removeMin() {
		if (this.root == null) {
			// Tree empty .
			return;
		} else {
			TreeNode temp = this.root;
			this.root = meld(this.root.leftChild, this.root.rightChild);
		}
	}

	// Inserts the input element into tree and melds with older root element .
	public void insert(int value) {
		TreeNode temp = new TreeNode(value);
		this.root = meld(this.root, temp);
	}

	// Displays the current Tree using 2 queue method for level order traversal
	// . At any point , one queue stores current level nodes and other queue
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

	// This method is called recursively for printing the level of Tree .
	private void printLevel(QueueImpl currLevel, int level, QueueImpl nextLevel) {
		TreeNode temp;
		System.out.print("Level " + level + " : ");
		do {
			temp = (TreeNode) currLevel.dequeue();
			System.out.print(temp.data);
			// Add childrens of current level node to next level queue for
			// display in next level .
			if (temp.rightChild != null)
				nextLevel.enqueue(temp.rightChild);
			if (temp.leftChild != null)
				nextLevel.enqueue(temp.leftChild);
			System.out.print(" , ");
		} while (!currLevel.isEmpty());
		System.out.println();
		if (!nextLevel.isEmpty())
			printLevel(nextLevel, level + 1, currLevel);
	}

	// Melds the two trees recursively provided as arguments and returns the new
	// root node of melded tree .
	private TreeNode meld(TreeNode tree1, TreeNode tree2) {
		if (tree1 != null && tree2 != null) {
			TreeNode smallest, largest;
			// Decide Smaller and larger of 2 trees depending on root data .
			if (tree1.data <= tree2.data) {
				smallest = tree1;
				largest = tree2;
			} else {
				smallest = tree2;
				largest = tree1;
			}
			// Recursively call meld of larger and right child of smaller node .
			if (smallest.rightChild != null) {
				smallest.rightChild = meld(smallest.rightChild, largest);
			} else {
				smallest.rightChild = largest;
			}
			// Swap childrens of new smaller root node depending on s values of
			// child nodes to make it leftist tree .
			if (smallest.leftChild == null) {
				swapChildrens(smallest);
			} else if (smallest.rightChild != null && smallest.rightChild.sValue < smallest.leftChild.sValue) {
				swapChildrens(smallest);
			}

			// Assign appropriate s value to smaller node depending on
			// children's s value
			int leftSValue, rightSValue;
			if (smallest.leftChild != null)
				leftSValue = smallest.leftChild.sValue;
			else
				leftSValue = 0;
			if (smallest.rightChild != null)
				rightSValue = smallest.rightChild.sValue;
			else
				rightSValue = 0;
			smallest.sValue = Math.min(leftSValue, rightSValue) + 1;

			return smallest;
		}
		return tree1 == null ? tree2 : tree1;
	}

	// Swaps the childrens of the node provided as argument .
	private void swapChildrens(TreeNode root) {
		TreeNode temp = root.leftChild;
		root.leftChild = root.rightChild;
		root.rightChild = temp;
	}

}
