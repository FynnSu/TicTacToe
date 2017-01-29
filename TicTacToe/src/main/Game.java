package main;

/**
 * Handles Tic Tac Toe Game
 * User inputs move as x,y coordinates
 * if place is already taken returns 5
 * if win returns 1 for first player, -1 for second player
 * if tie returns 4
 * @author Fynn
 *
 */
public class Game {
	
	private moveListener listener;
	
	int[][] board;
	private int current;
	private int moves;
	
	public Game() {
		board = new int[3][3];
		current = 1;
		moves = 0;
	}
	
	public void setListener(moveListener listener) {
		this.listener = listener;
	}
	
	public int move(int col, int row) {
		if (board[col][row] != 0)
			return 5;
		board[col][row] = current;
		listener.moveMade(col, row);
		moves++;
		switchCurrent();
		return (moves < 5) ? 0 : checkWin();
	}

	
	private void switchCurrent() {
		current = (current == 1) ? -1 : 1;
	}
	
	public int checkWin() {
		if (moves == 9) {
			return 4;
		}
		
		int col1 = board[0][0] + board[0][1] + board[0][2];
		int col2 = board[1][0] + board[1][1] + board[1][2];
		int col3 = board[2][0] + board[2][1] + board[2][2];
		int row1 = board[0][0] + board[1][0] + board[2][0];
		int row2 = board[0][1] + board[1][1] + board[2][1];
		int row3 = board[0][2] + board[1][2] + board[2][2];
		int diag1 = board[0][0] + board[1][1] + board[2][2];
		int diag2 = board[2][0] + board[1][1] + board[0][2];
		
		if (Math.abs(col1) == 3) {
			return col1 / 3;
		} else if (Math.abs(col2) == 3) {
			return col2 / 3;
		} else if (Math.abs(col3) == 3) {
			return col3 / 3;
		} else if (Math.abs(row1) == 3) {
			return row1 / 3;
		} else if (Math.abs(row2) == 3) {
			return row2 / 3;
		} else if (Math.abs(row3) == 3) {
			return row3 / 3;
		} else if (Math.abs(diag1) == 3) {
			return diag1 / 3;
		} else if (Math.abs(diag2) == 3) {
			return diag2 / 3;
		}
		return 0;
		
	}

}
