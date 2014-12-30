package assignment1;

class Element {// Wrapper class for wrapping node and storing as object in a
				// queue .

	public Element(Node node) {
		this.node = node;
		this.next = null;
	}

	public Node node;
	public Element next;
}
