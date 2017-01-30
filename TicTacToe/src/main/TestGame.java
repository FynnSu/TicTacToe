package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * TestGame Class for playing against Computer AI
 * Uses Game class for tttGame control
 * Provides simple user interface for easier playing
 * @author Fynn
 *
 */
public class TestGame implements ActionListener{
	
	Game game;
	Computer AI;
	JButton[] buttons;
	boolean userTurn = true;
	
	/**
	 * Initializes game
	 * @param args
	 */
	public static void main(String[] args) {
		new TestGame();
	}
	
	/**
	 * Creates new Game instance and runs it
	 * initializes computer with version 2
	 * Creates UI
	 * sets game to computer
	 */
	public TestGame() {
		AI = new Computer(2);
		
		JFrame frame = new JFrame("Tic Tac Toe");
		JPanel contentPane = new JPanel();
		buttons = new JButton[9];
		
		for(int x = 0; x < buttons.length; x++) {
			JButton b = new JButton("0");
			buttons[x] = b;
			b.setFont(new Font("Calibri", Font.PLAIN, 50));
			b.setActionCommand(Integer.toString(x));
			b.addActionListener(this);
			b.setBackground(Color.WHITE);
			contentPane.add(b);
		}
		
		contentPane.setLayout(new GridLayout(3,3));
		
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setVisible(true);
		
		game = new Game();
		AI.setGame(game);
	}
	
	
	/**
	 * Displays the inputed tttBoard on UI buttons
	 * @param tttBoard current board
	 */
	public void displayBoard(int[][] tttBoard) {
		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < 3; row++) {
				buttons[row * 3 + col].setText(Integer.toString(tttBoard[col][row]));
			}
		}
	}


	/**
	 * Called on action (click)
	 * Resets game if game is finished and prints out result
	 * Otherwise enters user move or comp move dependent on userTurn
	 * switches userTurn
	 * calls displayBoard()
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int result = game.checkWin();
		if (result != 0) {
			reset();
			displayBoard(game.board);
			userTurn = true;
			if (result == 1) {
				System.out.println("Player One Won!  ***");
			} else if (result == -1) {
				System.out.println("Comp Two Won!      ***");
			} else if (result == 4) {
				System.out.println("It was a Tie!          ***");
			}
		} else {
			if (userTurn) {
			int value = Integer.parseInt(e.getActionCommand());
			int col = value % 3;
			int row = value / 3;
			
			game.move(1, col, row);
			} else {
				AI.makeMove();
			}
			userTurn = !userTurn;
			displayBoard(game.board);
		}
	}
	
	/**
	 * Resets game
	 * clears UI
	 * Initializes new game and set AI to it
	 */
	public void reset() {
		for (JButton b : buttons) {
			b.setText("0");
		}
		game = new Game();
		AI.setGame(game);
	}

}
