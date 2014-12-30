package assignment1;

//Interface to declare methods to be supported by both Min-Leftist Tree and Binomial Heap . This interface is implemented by both the data structures .
public interface DataStructure {

	public void initialise(int[] data);

	public void insert(int data);

	public void removeMin();

	public void display();
}
