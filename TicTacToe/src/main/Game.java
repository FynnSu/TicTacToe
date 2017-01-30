package main;

/**
 * Handles Tic Tac Toe Game
 * User inputs move as x,y coordinates
 * if win returns 1 for first player, -1 for second player
 * if tie returns 4
 * @author Fynn
 *
 */
public class Game {
	
	private moveListener listenerOne, listenerTwo;
	
	int[][] board;
	private int current;
	private int moves;
	
	public Game() {
		board = new int[3][3];
		current = 1;
		moves = 0;
	}
	
	/**
	 * Sets game listener for computer one
	 * @param listener to be set
	 */
	public void setListenerOne(moveListener listener) {
		this.listenerOne = listener;
	}
	
	/**
	 * Sets game listener for computer two
	 * @param listener to be set
	 */
	public void setListenerTwo(moveListener listener) {
		this.listenerTwo = listener;
	}
	
	/**
	 * Sets move on board to specified entry
	 * Calls correct computer listener
	 * @param comp user/computer called from (calls other listener)
	 * @param col to be set (enter valid column)
	 * @param row to be set (enter valid row)
	 */
	public void move(int comp, int col, int row) {
		board[col][row] = current;
		if (comp == 1)
			listenerTwo.moveMade(col, row);
//		if (comp == 2) 							//comment out if user == 1
//			listenerOne.moveMade(col, row);
		moves++;
		switchCurrent();
	}

	
	/**
	 * Switches current player
	 */
	private void switchCurrent() {
		current = (current == 1) ? -1 : 1;
	}
	
	/**
	 * Checks to see if the game has been won or tied. 
	 * @return int value (1 for userOne win, -1 for userTwo win, 4 for tie, 0 for no result)
	 */
	public int checkWin() {
		if (moves == 9) {
			return 4; //tie
		}
		
		//userOne = 1, userTwo = -1
		//if three numbers in a row add up to 3 or -3 then userOne or userTwo has won
		
		int[] options = new int[8];
		options[0] = board[0][0] + board[0][1] + board[0][2]; //col 1
		options[1] = board[1][0] + board[1][1] + board[1][2]; //col 2
		options[2] = board[2][0] + board[2][1] + board[2][2]; //col 3
		options[3] = board[0][0] + board[1][0] + board[2][0]; //row 1
		options[4] = board[0][1] + board[1][1] + board[2][1]; //row 2
		options[5] = board[0][2] + board[1][2] + board[2][2]; //row 3
		options[6] = board[0][0] + board[1][1] + board[2][2]; //diag 1
		options[7] = board[2][0] + board[1][1] + board[0][2]; // diag 2
		
		for (int sum : options) {
			if (Math.abs(sum) == 3) {
				return sum / 3;
			}
		}
		return 0;
		
	}

}
