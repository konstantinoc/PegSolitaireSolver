package pegsol;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Tree {
	private ArrayList<Node> list;
	private Node root;
	private int counter;
	
	public Tree(Node root) {
		this.root = root;
		this.list = new ArrayList<Node>();
		this.list.add(root);
		this.counter = 0;
	}
	
	/**Depth First Algorithm
	 * Find the children of the current node
	 * While it has it gets the first child and execute the DFS for this.
	 * When a current node hasnt child, we check if this is the solution
	 * if is, we get the moves
	 * else we remove the child from his parent
	 */
	
	public void DFS(Node n) {
		n.findChildren();
		
		while(n.hasChild()) {
			counter++;
			DFS(n.getChild(0));
		}
		
		if(n.win()) {
			getMoves(n);
		    System.out.println("Deapth took " + (System.currentTimeMillis() - Main.startTime) + " milliseconds");
			System.out.println("DFS visited " + counter + " nodes");
		    System.exit(0);
		}
		else {
			if(n.getParent()!=null)
				n.getParent().removeChild(n);
			else {
				System.out.println("No Solution");
				System.out.println("Deapth took " + (System.currentTimeMillis() - Main.startTime) + " milliseconds");
				System.out.println("DFS visited " + counter + " nodes");
				writeOut("No Solution");
			    System.exit(0);
			}
		}
	}
	
	/**
	 * We have a list with all border nodes.
	 * while it's not empty, we get the node with the minimum heuristic score
	 * we get its children, if it has, we add them in the list,
	 * if it hasnt we check if this node is our solution.
	 * then we remove the current node from the list.
	 */
	@SuppressWarnings("unchecked")
	public void Best() {
		while(!list.isEmpty()) {
			Node current = Collections.min(list);
			counter++;
			current.findChildren();
			if(current.hasChild()) {
				list.addAll(current.getChildren());
			}
			else {
				if(current.win()) {
					System.out.println("Found");
					getMoves(current);
				    System.out.println("Best First took " + (System.currentTimeMillis() - Main.startTime) + " milliseconds");
				    System.out.println("Best First visited " + counter + " nodes");
				    System.exit(0);
				}
			}
			list.remove(current);
			
		}
		System.out.println("No Solution");
		System.out.println("Best took " + (System.currentTimeMillis() - Main.startTime) + " milliseconds");
		writeOut("No Solution");
		System.exit(0);
	}
	
	/*
	 * find the route from the current node backward to the root, and push them to a stack.
	 * Then pop one by one the nodes from the stack and gets the moves.
	 */
	public void getMoves(Node n) {
		Stack<Node> stack = new Stack<Node>();
		String res = "";
		int count = 0;
		
		while(n.getParent()!=null) {
			count++;
			stack.push(n);
			n = n.getParent();
		}
		
		while(!stack.isEmpty()) {
			Node currentNode = stack.pop();
			for(int i=0; i<4; i++) 
				res=res + Integer.toString(currentNode.getValue().getMove()[i]+ 1) + " ";
			
			res+="\n";
		}
		res = count+"\n" + res; 
		writeOut(res);
	}
	
	//write in file
	public void writeOut(String string) {
		try {
		      FileWriter myWriter = new FileWriter(Main.output);
		      myWriter.write(string);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
