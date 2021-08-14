package pegsol;

import java.util.ArrayList;

public class Node implements Comparable{
	private Board value;
	private Node parent;
	private ArrayList<Node> children;
	private boolean visited;
	public Node(Board value, Node parent) {
		this.value = value;
		this.children = new ArrayList<>();
		this.parent = parent;
	}
	
	public void findChildren() {
		ArrayList<Board> adj = value.getAdj();
		for(Board b:adj) {
			children.add(new Node(b,this));
		}
	}

	public void addChild(Node n) {
		children.add(n);
	}
	
	public Node getChild(int i) {
		return children.get(i);
	}
	
	public void removeChild(Node n) {
		children.remove(n);
	}
	
	public ArrayList<Node> getChildren(){
		return this.children;
	}
	
	public Node getParent() {
		return this.parent;
	}
	
	public boolean hasChild(){
		if(!children.isEmpty())
			return true;
		return false;
	}

	public Board getValue() {
		return value;
	}
	
	public boolean win() {
		if(value.countPegs()==1)
			return true;
		return false;
	}

	@Override
	public int compareTo(Object o) {
		if(value.getScore() > ((Node)o).getValue().getScore())
			return 1;
		else if(value.getScore() < ((Node)o).getValue().getScore())
			return -1;
		return 0;
	}
}
