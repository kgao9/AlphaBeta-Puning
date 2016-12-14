import java.util.*;
import java.lang.*;

///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            Tic Tac Toe
//Files:            TicTacToe.java
//Semester:         cs540 Fall 2016
//section: 			lec 1
//
//Author:           Kenny Gao
//Wisc username:    kgao9
//Email:            kgao9@wisc.edu
//CS Login:         kenny
//Lecturer's Name:  Jerry Zhu
//Homework #:       5
//Submission Date: 10/25/16
//
//
////////////////////////////80 columns wide //////////////////////////////////

public class TicTacToe
{
	//for our search, we need nodes
	//each node has a state which is the
	//number of liters in jug1 and the number of liters in jug2
	//each node will be able to check if they equal to each other
	//i.e. if they have the same state
	//each node can also create successors and test to see if it's a goal
	private static class Node 
	{
		//this is the state of the board
		private String [][] board = new String[3][4];

		//this variable indicates whose turn it is
		//if human's turn, 1, not human's turn -1
		private int turn;

		/**
		 * The purpose of this method is to instantiate the state of each node
		 * @param input - array to fill the board with
		 * Length of array should be 12
		 * should only contain O X or _
		 * No returns
		 */

		private Node(String [] input)
		{
			//for each entry on the board, initialize 
			for(int i = 0; i < 3; i++)
			{	
				for(int j = 0; j < 4; j++)
				{
					//get input from array
					String getInput = input[i*4 + j];

					//strings needs to be O X or _
					if(getInput.equals("O") || getInput.equals("X") || getInput.equals("_") || getInput.equals("#"))
					{
						//initialize
						this.board[i][j] = getInput;
					}

					else
					{
						//error checking
						System.out.print("USAGE: invalid parameter " + getInput);
						System.out.print(" found - only O, X, _ are accepted.");
						System.exit(0);
					}

					//it's always the X's turn
					this.turn = -1;
				}
			}
		}     

		/**
		 * The purpose of this method is to instantiate the state of each node
		 * @param Node - the current state our algorithm is at
		 * @param row - the row in which to make the move
		 * @param col - the column in which to make the move
		 * No returns
		 */

		private Node(Node currNode, int row, int col)
		{
			//these entries don't exist
			if(row < 0 || col < 0 || row > 2 || col > 3)
			{
				throw new IllegalArgumentException();
			}

			//copy of the board
			this.board = currNode.getBoard();

			//the player plays his move, and then it's the opponent's turn
			if(currNode.getTurn() == -1)
			{	
				this.board[row][col] = "X";
				this.turn = 1;
			}    		

			else
			{
				this.board[row][col] = "O";
				this.turn = -1;
			}	
		}

		/**
		 * The purpose of this method is to return a copy of the board
		 * no parameters
		 * @return the current board 
		 */

		private String [][] getBoard()
		{
			//copy of the board
			String [][] boardCopy = new String[3][4];

			//for each entry in the current board
			for(int i = 0; i < 3; i++)
			{	
				for(int j = 0; j < 4; j++)
				{
					//copy to a copy of the board
					boardCopy[i][j] = this.board[i][j];
				}
			}

			//return an exact copy of the board
			return boardCopy;
		}

		/**
		 * The purpose of this method is to see if a player has won yet
		 * no parameters
		 * @return true if a player has won, false otherwise
		 */

		private boolean win()
		{	
			//check for rows
			for(int i = 0; i < 3; i++)
			{	
				for(int j = 0; j < 2; j++)
				{
					//f = first, s = second, t = third
					String fElement = this.board[i][j];
					String sElement = this.board[i][j+1];
					String tElement = this.board[i][j+2];

					//found three elements in a row
					if(fElement.equals(sElement) && fElement.equals(tElement))
					{
						//check for four in a row
						//Jerry says four in a row is not a win
						String fourthElement = "";

						//get fourth element
						if(j == 0)
							fourthElement = this.board[i][j + 3];

						else
							fourthElement = this.board[i][j - 1];

						//if it's not a four in a row, return true
						if(!(fElement.equals(fourthElement)))
						{
							if(fElement.equals("X") || fElement.equals("O"))
								return true;
						}    
					}
				}
			}

			//check for columns
			for(int i = 0; i < 1; i++)
			{	
				for(int j = 0; j < 4; j++)
				{
					//f = first, s = second, t = third
					String fElement = this.board[i][j];
					String sElement = this.board[i + 1][j];
					String tElement = this.board[i + 2][j];

					//if three in a row, won
					if(fElement.equals(sElement) && fElement.equals(tElement))
					{
						if(fElement.equals("X") || fElement.equals("O"))
							return true;
					}
				}
			}

			//check for diagonals downward
			for(int i = 0; i < 1; i++)
			{	
				for(int j = 0; j < 2; j++)
				{
					//f = first, s = second, t = third
					String fElement = this.board[i][j];
					String sElement = this.board[i + 1][j + 1];
					String tElement = this.board[i + 2][j + 2];

					//if three in a row, winner found
					if(fElement.equals(sElement) && fElement.equals(tElement))
					{
						if(fElement.equals("X") || fElement.equals("O"))
							return true;
					}
				}
			}

			//check for diagonals upward
			for(int i = 2; i > 1; i--)
			{	
				for(int j = 0; j < 2; j++)
				{
					//f = first, s = second, t = third
					String fElement = this.board[i][j];
					String sElement = this.board[i - 1][j + 1];
					String tElement = this.board[i - 2][j + 2];

					//if three in a row found, winner found
					if(fElement.equals(sElement) && fElement.equals(tElement))
					{
						if(fElement.equals("X") || fElement.equals("O"))
							return true;
					}
				}
			}

			//no winner found
			return false;
		}

		/**
		 * The purpose of this method is to create all possible moves
		 * no parameters
		 * @return arraylist of all nodes
		 */

		private ArrayList <Node> getSucc()
		{
			ArrayList <Node> succ = new ArrayList <Node> ();

			//if a board is already won or drawn, there are no successors
			if(win() || draw())
			{
				return succ;
			}

			//find out who's move it is
			String move = "O";

			if(this.turn == -1)
			{	
				move = "X";
			}

			//for each square on the board
			//if that space is empty, move there
			for(int i = 0; i < 3; i++)
			{	
				for(int j = 0; j < 4; j++)
				{
					if(this.board[i][j].equals("_"))
					{	
						succ.add(new Node(this, i, j));
					}	
				}
			}

			return succ;
		}

		/**
		 * The purpose of this method is to return the current turn
		 * no parameters
		 * @return current turn
		 */
		private int getTurn()
		{
			return this.turn;
		}

		/**
		 * The purpose of this method is to return whether the game is a draw or not
		 * no parameters
		 * @return true if draw, false otherwise
		 */

		private boolean draw()
		{
			//if someone's won, there can't possibly be a draw
			if(win())
				return false;

			//if there are no empty spots on a board, it is a draw
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					if(this.board[i][j].equals("_"))
					{
						return false;
					}
				}
			}
			
			//no empty spots found, so it is a draw
			return true;
		}

		/**
		 * The purpose of this method is to return the winning result
		 * AKA opposite of players turn
		 * no parameters
		 * @return winning result
		 */

		private int getWinner()
		{
			if(draw())
				return 0;

			//find out if X won or O won
			if(this.turn == -1)
			{
				return 1;
			}

			else
				return -1;
		}

		/**
		 * The purpose of this method is to print out the right output
		 * with to string
		 * no parameters
		 * @return string as:
		 *                    00 01 02 03
		 *                    10 11 12 13
		 *                    20 21 22 23
		 */

		public String toString()
		{
			String output = "";

			//for each square on the board
			//if that space is empty, move there
			for(int i = 0; i < 3; i++)
			{	
				String row = "";

				for(int j = 0; j < 4; j++)
				{
					row += this.board[i][j] + " ";	
				}

				if(i != 2)
					row += "\n";
				output += row;
			}

			return output;
		}
	}

	/**
	 * The purpose of this method is to get the maximum theoretical value 
	 * @param s - current node
	 * @param alpha - the best max can do on a path
	 * @param beta - the best min can do on a path
	 * @param print - whether to print or not
	 * @return the maximum game value for this node
	 */

	public static int max(Node s, int alpha, int beta, boolean print)
	{
		//if there are no successors, it is the leaf node
		//return that node and terminate
		if(s.getSucc().size() == 0)
		{
			//print should probably be a function
			//every time something terminates or every time something is created
			//it is best to print it out so you know what's going on
			//but make sure it has a pretty format or you'll be going like "WTH is going on here"
			//Note to self: Check out java's logging class - probably better than printing to console
			//printing to console usually causes users to complain.

			if(print)
			{
				System.out.println(s.toString());
				System.out.print("alpha: " + String.valueOf(alpha));
				System.out.print(" beta: " + String.valueOf(beta) + "\n");
			}    

			return s.getWinner();
		}

		//get successors for current node
		//for max speed, probably best to put this above checking for leaf
		//awwww well, I'm lazy
		ArrayList <Node> successors = s.getSucc();

		//for each node that is a successor of the current node
		for(Node inSucc :  successors)
		{
			//we get the best approximation of how well we can do
			alpha = Math.max(alpha, min(inSucc, alpha, beta, print));

			//this path isn't good enough
			//no matter what we do on this branch, the min player wins
			//so we will ignore this branch
			//aka pruning
			//Note: the nodes that are pruned will not show up in the console
			if(alpha >= beta)
			{
				if(print)
				{
					System.out.println(s.toString());
					System.out.print("alpha: " + String.valueOf(alpha));
					System.out.print(" beta: " + String.valueOf(beta) + "\n");
					return beta;
				}    
			}
		}

		if(print)
		{
			//print this node's status
			System.out.println(s.toString());
			System.out.print("alpha: " + String.valueOf(alpha));
			System.out.print(" beta: " + String.valueOf(beta) + "\n");
		}    

		return alpha;
	}

	/**
	 * The purpose of this method is to get the minimum theoretical value 
	 * @param s - current node
	 * @param alpha - current alpha value
	 * @param beta - current beta value
	 * @param print - whether to print or not
	 * @return minimum theoretical value
	 */

	public static int min(Node s, int alpha, int beta, boolean print)
	{
		///if there are no successors, it is the leaf node
		//return that node and terminate
		if(s.getSucc().size() == 0)
		{
			//print out state
			if(print)
			{
				System.out.println(s.toString());
				System.out.print("alpha: " + String.valueOf(alpha));
				System.out.print(" beta: " + String.valueOf(beta) + "\n");
			}    

			return s.getWinner();
		}

		//get all successors
		ArrayList <Node> successors = s.getSucc();

		//for each successor, calculate the theoretical value
		for(Node inSucc :  successors)
		{
			//we want the minimum value of all max
			beta = Math.min(beta, max(inSucc, alpha, beta, print));
			
			//if alpha is >= than beta, we want to prune
			if(alpha >= beta)
			{
				//print out state
				if(print)
				{
					System.out.println(s.toString());
					System.out.print("alpha: " + String.valueOf(alpha));
					System.out.print(" beta: " + String.valueOf(beta) + "\n");
				}    

				//return prunes the tree 
				return alpha;
			}
		}

		//print out state
		if(print)
		{
			System.out.println(s.toString());
			System.out.print("alpha: " + String.valueOf(alpha));
			System.out.print(" beta: " + String.valueOf(beta) + "\n");	
		}

		//return our theoretical value
		return beta;
	}

	/**
	 * The purpose of this method is to get the node with the
	 * best move
	 * @param s - Current state
	 * @param alpha - current alpha value
	 * @param beta - current beta value
	 * @param print - whether to print output or not
	 * @return the state with the best move
	 */

	public static Node move(Node s, int alpha, int beta, boolean print)
	{
		//this will be the move we make
		Node returnNode = null;

		//if there are no successors, we were given an already complete board
		if(s.getSucc().size() == 0)
		{
			//error handling
			System.out.println("No move available");
			System.exit(0);
		}

		ArrayList <Node> successors = s.getSucc();

		//find the best move for X
		for(Node inSucc :  successors)
		{
			int maxMoveValue = max(inSucc, alpha, beta, print);

			if(beta > maxMoveValue)
			{	
				returnNode = inSucc;
				beta = maxMoveValue;
			}
		}

		//print out value
		if(print)
		{
			System.out.println(s.toString());
			System.out.print("alpha: " + String.valueOf(alpha));
			System.out.print(" beta: " + String.valueOf(beta) + "\n");	
		}

		//return the best move
		return returnNode;
	}

	public static void main(String [] args)
	{
		//args should have 13 parameters
		if(args.length != 13)
		{
			System.out.println("USAGE: input needs 13 parameters");
			System.exit(0);
		}

		String [] input = new String [12];

		String print = args[12];

		if(!(print.equals("N") || print.equals("Y")))
		{
			System.out.println("USAGE: last parameter must be N or Y");
			System.exit(0);
		}

		for(int i = 0; i < 12; i++)
		{	
			input[i] = args[i];
		}

		Node start = new Node(input);


		//called first call
		//only on that call, we will do something extra
		//System.out.println("\nredo");
		Node returnNode = move(start, -2, 2, print.equals("Y"));
		System.out.println("SOLUTION");
		System.out.println(returnNode);
	}
}
