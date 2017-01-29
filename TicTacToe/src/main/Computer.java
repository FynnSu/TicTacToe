package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Computer implements moveListener{
	
	int[][] board;
	int[] configs;
	int[] previousLosses;
	int[] previousWins;
	Game game;
	ObjectInputStream oisWins;
	ObjectOutputStream oosWins;
	ObjectInputStream oisLosses;
	ObjectOutputStream oosLosses;

	public Computer() {
		board = new int[3][3];
		configs = new int[9];
		previousLosses = new int[19683];
		previousWins = new int[19683];
		read();
	}
	
	public void reset() {
		board = new int[3][3];
		configs = new int[9];
	}
	
	public void write() {
		try {
			oosWins = new ObjectOutputStream(new FileOutputStream("ticTacToeWins.dat"));
			oosLosses = new ObjectOutputStream(new FileOutputStream("ticTacToeLosses.dat"));
		} catch (IOException e) {
				System.out.println(e.getMessage());
		} 
		
		try {
			oosWins.writeObject(previousWins);
			oosLosses.writeObject(previousLosses);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {oosWins.close(); oosLosses.close(); } catch (IOException e) { System.out.println(e.getMessage());}
		}
	}
	
	public void read() {
		try {
			oisWins = new ObjectInputStream(new FileInputStream("ticTacToeWins.dat"));
			oisLosses = new ObjectInputStream(new FileInputStream("ticTacToeLosses.dat"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			previousWins = (int[]) oisWins.readObject();
			previousLosses = (int[]) oisLosses.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setGame(Game newGame) {
		game = newGame;
		configs = new int[9];
	}
	
	public void addMove(int col, int row) {
		board[col][row] = 2; //2 is a user move
		setBoardValue();
		checkWin();
	}
	
	public void checkWin() {
		int result = game.checkWin();
		if (result == 0)
			return;
		setWinLoss(result != 1);
		reset();
	}
	
	public void makeMove() {
		int bestCol = 0, bestRow = 0, bestLossValue = Integer.MAX_VALUE, bestWinValue = 0;
		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < 3; row++) {
				if (board[col][row] == 0){
					int index = getBoardValue(col,row);
					if (previousLosses[index] < bestLossValue){
						bestLossValue = previousLosses[index];
						bestCol = col;
						bestRow = row;
						bestWinValue = previousWins[index];
					} else if (previousLosses[index] == bestLossValue && previousWins[index] > bestWinValue) {
						bestCol = col;
						bestRow = row;
						bestWinValue = previousWins[index];
					}
				}
					
			}
		}
		board[bestCol][bestRow] = 1;
		game.move(bestCol, bestRow);
		checkWin();
	}
	
	public void setWinLoss(boolean result) {
		if (result) { //computer win
			for (int i = 0; i < 9; i++) {
				previousWins[configs[1]]++;
			}
		} else {
			for (int i = 0; i < 9; i++) {
				previousLosses[configs[1]]++;
			}
		}
		write();
	}
	
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
		System.out.println(index);
	}
	
	/**
	 * gets board value for a particular move
	 * @param x
	 * @param y
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

	@Override
	public void moveMade(int col, int row) {
		addMove(col, row);
	}
	
	
}
