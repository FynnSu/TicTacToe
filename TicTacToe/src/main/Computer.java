package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Computer Class for Tic Tac Toe AI
 * Plays tic tac toe by looking at previous game results and choosing
 * the best move option
 * @author Fynn
 *
 */
public class Computer implements moveListener{
	
	int[][] board;
	int[] configs;
	int[] previous;
	int version;
	Game game;

	/**
	 * Create computer object with specified version 
	 * initializes arrays and calls read()
	 * @param version for computer (1 or 2)
	 */
	public Computer(int version) {
		this.version = version;
		board = new int[3][3];
		configs = new int[9];
		previous = new int[19683];
		read();
	}
	
	/**
	 * Resets the matrix board and array configs to empty
	 */
	public void reset() {
		board = new int[3][3];
		configs = new int[9];
	}
	
	/**
	 * Opens an object output stream and writes the previous array to file
	 */
	public void write() {
		ObjectOutputStream oosLosses = null;
		try {
			oosLosses = new ObjectOutputStream(new FileOutputStream("tttPreviousComp" + Integer.toString(version) + ".dat"));
		} catch (IOException e) {
				System.out.println(e.getMessage());
		} 
		
		try {
			oosLosses.writeObject(previous);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {oosLosses.close(); } catch (IOException e) { System.out.println(e.getMessage());}
		}
	}
	
	/**
	 * Opens an object input stream and reads from file
	 * Stores data in previous array
	 */
	public void read() {
		ObjectInputStream oisLosses = null;
		try {
			oisLosses = new ObjectInputStream(new FileInputStream("tttPreviousComp" + Integer.toString(version) + ".dat"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			previous = (int[]) oisLosses.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Calls reset() and then sets the newGame to computer
	 * sets game listener depending on computer version
	 * @param newGame to be set
	 */
	public void setGame(Game newGame) {
		reset();
		game = newGame;
		if (version == 1) {
			game.setListenerOne(this);
		} else {
			game.setListenerTwo(this);
		}
	}
	
	/**
	 * Add new external move to board
	 * Calls setBoardValue() and CheckWin()
	 * @param col for move
	 * @param row for move
	 */
	public void addMove(int col, int row) {
		board[col][row] = 2; //2 is a user move
		setBoardValue();
		checkWin();
	}
	
	/**
	 * Checks win using game.checkWin() method
	 * Returns if no result
	 * Calls setWinLoss with correct boolean value
	 * dependent on computer version
	 */
	public void checkWin() {
		int result = game.checkWin();
		if (result == 0)
			return;
		if (version == 1) {
			setWinLoss(result == 1);
		} else if (version == 2) {
			setWinLoss(result == -1);
		}
	}
	
	/**
	 * Makes a computer move
	 * Goes through each possible move and finds the one with the least losses 
	 * stored in history
	 * Makes that move and calls game.move()
	 * Calls setBoardValue() and checkWin()
	 */
	public void makeMove() {
		int bestCol = 0, bestRow = 0, bestLossValue = Integer.MAX_VALUE;
		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < 3; row++) {
				if (board[col][row] == 0){
					int index = getBoardValue(col,row);
					if (previous[index] < bestLossValue){
						bestLossValue = previous[index];
						bestCol = col;
						bestRow = row;
					}
				}
					
			}
		}
		board[bestCol][bestRow] = 1;
		game.move(version, bestCol, bestRow);
		setBoardValue();
		checkWin();
	}
	
	/**
	 * Increments counter for all previous configs if game was a loss or tie
	 * Calls write()
	 * @param result of game (true if computer won) false otherwise
	 */
	public void setWinLoss(boolean result) {
		if (!result) { //computer loses or ties
			for (int i = 0; i < 9; i++) {
				previous[configs[i]]++;
			}
		}
		write();
	}
	
	/**
	 * Sets the current board value to first open spot in configs array
	 * Board value stored using base 3 values for each possible configuration
	 */
	public void setBoardValue() {
		int index = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				index += board[col][row]* Math.pow(3, 8-(3 * row + col));
			}
		}
		
		int current = 0;
		while (current < 9 && configs[current] != 0) 
			current++;
		configs[current] = index;
	}
	
	/**
	 * Gets board value for a specific move
	 * Similar to setBoardValue() except gets board values for unmade moves
	 * @param col of move
	 * @param row of move
	 * @return int value of board configuration
	 */
	public int getBoardValue(int col, int row) {
		int index = 3 * row + col;
		
		int value = 0;
		for (int rowLoc = 0; rowLoc < 3; rowLoc++) {
			for (int colLoc = 0; colLoc < 3; colLoc++) {
				value += board[colLoc][rowLoc]* Math.pow(3, 8-(3 * rowLoc + colLoc));
			}
		}
		value += Math.pow(3, 8-index);
		return value;
	}

	/**
	 * Interface listener for game moves
	 * Called when other player/computer makes move
	 * calls addMove()
	 */
	@Override
	public void moveMade(int col, int row) {
		addMove(col, row);
	}
	
}
