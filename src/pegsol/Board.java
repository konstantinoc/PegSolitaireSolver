package pegsol;

import java.util.ArrayList;

public class Board {
	private int[][] board;
	private double score;
	private int[] move;
	
	public Board(int[][] board, int[] move) {
		this.board = board;
		this.move = move;
		
		if (Main.algo.equals("best")){
			this.score = calcScore(); 
		}
	}
	
	
	//Calculate how many pegs cant move in any direction. 
	//It cant move if all around positions are 2 or 0 or two continuous 1 1 or 1 0
	//different algorithm if problem is big.
	public int pegsNoMove(){
		int pegs = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				int side = 0;
				if(board[0].length<7 && board.length<7) {
					try {
						if(board[i][j]==1) {
							if(board[i-1][j]==0 || board[i-1][j]==2  || (board[i-1][j]==1 && board[i-2][j]!=2))
								side++;
						}
					}catch(Exception e) {
						//If there is no cell above
						side++;
					}
					
					try {
						if(board[i][j]==1) {
							if(board[i+1][j]==0 || board[i+1][j]==2 || (board[i+1][j]==1 && board[i+2][j]!=2))
								side++;
						}
					}catch(Exception e) {
						//If there is no cell below
						side++;
					}
					
					try {
						if(board[i][j]==1) {
							if(board[i][j-1]==0 || board[i][j-1]==2 || (board[i][j-1]==1 && board[i][j-2]!=2))
								side++;
						}
					}catch(Exception e) {
						//If there is no cell on the left
						side++;
					}
					
					try {
						if(board[i][j]==1) {
							if(board[i][j+1]==0 || board[i][j+1]==2 || (board[i][j+1]==1 && board[i][j+2]!=2))
								side++;
						}
					}catch(Exception e) {
						//If there is no cell on the right
						side++;
					}
					
					//if the peg cant move in any diraction
					if(side==4)
						pegs++;
					
				//if the problem is big
				}else {
					side = 0;
					try {
						if(board[i-1][j]==1 && board[i-2][j]==0 || board[i-2][j]==2 ) {
							side++;
						}
					}catch(Exception e) {
						//If there is no cell above
						side++;
					}
					
					try {
						if(board[i+1][j]==1 && board[i+2][j]==2 || board[i+2][j]==0) {
							side++;
						}
					}catch(Exception e) {
						//If there is no cell below
						side++;
					}
					
					try {
						if(board[i][j-1]==1 && board[i][j-2]==2 || board[i][j-2]==0) {
							side++;
						}
					}catch(Exception e) {
						//If there is no cell on the left
						side++;
					}
					
					try {
						if(board[i][j+1]==1 && board[i][j+2]==2 || board[i][j+2]==0) {
							side++;
						}
					}catch(Exception e) {
						//If there is no cell on the right
						side++;
					}
					
					//if the peg cant move in any diraction
					if(side==4)
						pegs++;
				}
			}
		}
		return pegs;
	}
	
	//Count how many pegs there are on the board
	public int countPegs() {
		int pegs = 0;
		for(int i=0; i<board.length; i++)
			for(int j=0; j<board[i].length; j++) {
				if(board[i][j]==1)
					pegs++;
			}
		return pegs;
	}
	
	//Score = rectangle area that contains all pegs*how many pegs cant move.
	public double calcScore() {
		int mini = 1000;
		int maxi = -1;
		int minj = 1000;
		int maxj = -1;
		
		//find the corners of the rectangle where all pegs are inside
		for(int i=0; i<board.length;i++) {
			for(int j=0; j<board[i].length;j++) {
				if(board[i][j]==1 && i<mini)
					mini=i;
				if(board[i][j]==1 && i>maxi)
					maxi=i;
				if(board[i][j]==1 && j<minj)
					minj=j;
				if(board[i][j]==1 && j>maxj)
					maxj=j;
			}
			
		}
		
		double height = Math.pow(maxi-mini+1, 2);
		double width = Math.pow(maxj-minj+1,2);
		double total = height*width;
		
		
		int noMove = pegsNoMove();
		
		if(noMove>0) {
			total = total*noMove;
		}
		
		return total;
	}
	
	//return a list where contains Boards for any adjacent situation.
	public ArrayList<Board> getAdj() {
		ArrayList<Board> adj = new ArrayList<>();
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				try {
					if(board[i][j]==1 && board[i-1][j]==1 && board[i-2][j]==2) {
//						System.out.println("Go up");
						int[][] b = copyBoard(board);
						b[i][j] = 2;
						b[i-1][j] = 2;
						b[i-2][j] = 1;
						
						//the move that must be made by the parent to reach this state
						int[] move = {j,i,j,i-2};
						adj.add(new Board(b,move));
					}
					else {
//						System.out.println("Cant go UP");
					}
				}catch(Exception e){
//					System.out.println("Cant go UP");
				}
				try {	
					if(board[i][j]==1 && board[i+1][j]==1 && board[i+2][j]==2) {
//						System.out.println("Go Down");
						int[][] b = copyBoard(board);
						b[i][j] = 2;
						b[i+1][j] = 2;
						b[i+2][j] = 1;
						
						//the move that must be made by the parent to reach this state
						int[] move = {j,i,j,i+2};
						adj.add(new Board(b,move));
					}
					else {
//						System.out.println("Cant go DOWN");
					}
				}catch(Exception e){
//					System.out.println("Cant go DOWN");
				}
				try {
					if(board[i][j]==1 && board[i][j+1]==1 && board[i][j+2]==2) {
//						System.out.println("Go right");
						int[][] b = copyBoard(board);
						b[i][j] = 2;
						b[i][j+1] = 2;
						b[i][j+2] = 1;
						
						//the move that must be made by the parent to reach this state
						int[] move = {j,i,j+2,i};
						adj.add(new Board(b,move));
					}
					else {
//						System.out.println("Cant go RIGHT");
					}
				}catch(Exception e){
//					System.out.println("Cant go RIGHT");
				}
				try {
					if(board[i][j]==1 && board[i][j-1]==1 && board[i][j-2]==2) {
//						System.out.println("Go left");
						int[][] b = copyBoard(board);
						b[i][j] = 2;
						b[i][j-1] = 2;
						b[i][j-2] = 1;
						
						//the move that must be made by the parent to reach this state
						int[] move = {j,i,j-2,i};
						adj.add(new Board(b,move));
					}
					else {
//						System.out.println("Cant go LEFT");
					}
				}catch(Exception e){
//					System.out.println("Cant go LEFT");
				}
				
//				System.out.println("------------");
			}
			
		}
//		System.out.println("END ADJ\n--------------");
		return adj;
	}
	
	//Copy the board in an other variable.
	public int[][] copyBoard(int[][] board){
		int[][] b2 = new int[board.length][board[0].length];
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				b2[i][j] = board[i][j];
			}
		}
		return b2;
	}
	
	public int[][] getBoard() {
		return board;
	}

	public int[] getMove() {
		return move;
	}
	
	public double getScore() {
		return this.score;
	}
	
	//*For debug*//
	public void printBoard() {
		for(int i=0; (int) i<(board.length); i++) {
			for(int j=0; j<board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
