package assignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

//Main class for this program . Execution starts from the main method in this class .
public class Heap {

	// Defines the number of random operations to be done on Min-Leftist Tree
	// and Binomial Heap in random mode .
	private static int RANDOM_OPERATIONS_COUNT = 5000;

	// Numbers from 0 to range are generated in random mode to be stored into
	// data structures in random mode .
	private static final int RANGE = 10000;

	public static void main(String[] args) {
		try {
			String mode = args[0];
			DataStructure ds = null;
			if (mode.equalsIgnoreCase("-r")) {// Random mode .
				// Generate random set of operations for running on both Min-Leftist Tree and Binomial Heap . Size 
				String[] randomOperations = generateRandomOperationsSet();

				long time;
				time = System.nanoTime();
				ds = new MinLeftistTree();
				for(int i=0;i<RANDOM_OPERATIONS_COUNT;i++)
					executeRequest(randomOperations[i], ds);
				time = System.nanoTime() - time;
				System.out.println("Time taken for Min-Leftist Tree : " + time);
				
				time = System.nanoTime();
				ds = new BinomialHeap();
				for(int i=0;i<RANDOM_OPERATIONS_COUNT;i++)
					executeRequest(randomOperations[i], ds);
				time = System.nanoTime() - time;
				System.out.println("Time taken for Binomial Heap : " + time);
				
			} else {// File input mode .
				String filename = args[1];
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				if (mode.equalsIgnoreCase("-il")) {
					ds = new MinLeftistTree();
				} else if (mode.equalsIgnoreCase("-ib")) {
					ds = new BinomialHeap();
				}
				String input;
				while ((input = reader.readLine()) != null) {
					executeRequest(input, ds);// Perform each operation read
												// from file on data structure .
				}
				
				// Display current state of data structure . 
				ds.display();
				
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Generate random set of operations for usage in case of random mode
	private static String[] generateRandomOperationsSet(){
		Random rand = new Random();
		int r;
		StringBuilder request = new StringBuilder();
		String[] randomOperations = new String[RANDOM_OPERATIONS_COUNT];
		for (int i = 0; i < RANDOM_OPERATIONS_COUNT; i++) {
			r = rand.nextInt();
			if (r % 2 == 0) {// Random way to decide if to do a insert or delete
								// min .
				request.append("i ");
				request.append(rand.nextInt(RANGE));
			} else
				request.append("d");
			randomOperations[i] = request.toString();
			request.setLength(0);// Empty request for storing next operation .
		}
		return randomOperations;
	}
	
	// Execute operation provided as request string on data structure .
	private static void executeRequest(String request, DataStructure ds) {
		String action = request.split(" ")[0];
		int data;
		if (action.equalsIgnoreCase("i")) {
			data = Integer.parseInt(request.split(" ")[1]);
			ds.insert(data);
		} else if (action.equalsIgnoreCase("d")) {
			ds.removeMin();
		}
	}

}
