package assignment1;


//A program specific queue implementation . Works as a simple FIFO queue but stores only elements of type Element .
public class QueueImpl {

	private Element head;
	private Element tail;

	public QueueImpl() {
		this.head = null;
		this.tail = null;
	}

	// Wraps the argument node in an element object and stores in the queue .
	public void enqueue(Node node) {
		Element elem = new Element(node);
		if (head == null) {
			head = elem;
			tail = elem;
		} else {
			tail.next = elem;
			tail = elem;
		}
	}

	// Deletes the element from queue and returns the Node reference wrapped in
	// the element object dequed .
	public Node dequeue() {
		if (head == null)
			return null;
		Node node = head.node;
		head = head.next;
		return node;
	}

	// Returns the number of elements stored in the queue .
	public int size() {
		if (head == null)
			return 0;
		int i = 0;
		Element temp = head;
		while (temp != tail) {
			i++;
			temp = temp.next;
		}
		i++;
		return i;
	}

	// Returns true if the queue is empty , false otherwise .
	public boolean isEmpty() {
		if (head == null)
			return true;
		else
			return false;
	}
}
