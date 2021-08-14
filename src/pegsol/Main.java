package pegsol;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static String algo;
	public static String output;
	public static long startTime;
	public static void main(String[] args) {
		algo = args[0];
		output = args[2];
		
		Board board = createFirstBoard(args[1]);
		Node root = new Node(board, null);
		
		Tree tree = new Tree(root);
		
		
		if(algo.equals("depth")) {
			startTime = System.currentTimeMillis();
			tree.DFS(root);

		}else {
			startTime = System.currentTimeMillis();
			tree.Best();
		}
			
	}
	
	//Read from file and return a Board 
	public static Board createFirstBoard(String fileName) {
		int[][] b = null;
		try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      
		      int rows = myReader.nextInt();
		      int columns = myReader.nextInt();
		      
		      b = new int[rows][columns];
		      
		      int i = 0;
		      int j = 0;
		      while (myReader.hasNextInt()) {
		    	 if(j<b[i].length) {
		    		 b[i][j] = myReader.nextInt();
		    		 j++;
		    	 }else {
		    		 i++;
		    		 j=0;
		    		 b[i][j] = myReader.nextInt();
		    		 j++;
		    	 }
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return new Board(b, null);
	}

}