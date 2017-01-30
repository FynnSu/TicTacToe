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
 * Class used for Training AI
 * Repeatedly plays computer against itself
 * @author Fynn
 *
 */
public class TeachAI implements ActionListener{
	
	static int[][] board;
	JButton[] buttons;
	Computer AI1;
	Computer AI2;
	int result = 1;
	boolean compOne;
	Game game;
	
	/**
	 * Creates new instance of TeachAI()
	 * Initializes computers and board
	 * Calls setUp()
	 */
	public TeachAI() {
		AI1 = new Computer(1);
		AI2 = new Computer(2);
		board = new int[3][3];
		setUp();
	}
	
	/**
	 * Starts game
	 * Creates new TeachAI() instance
	 * @param args
	 */
	public static void main(String[] args) {
		new TeachAI();
	}
	
	/**
	 * Sets up the user interface
	 * displays UI
	 */
	public void setUp() {
		
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
	}
	
	/**
	 * Displays current board on UI
	 * @param tttBoard current config
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
	 * Allows move by move viewing of Computer games or can be looped 
	 * for continuous moves
	 * resets game when game finished
	 * makes correct computer move
	 * switches computer
	 * prints out game results
	 * calls displayBoard()
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		while(true) {
		if (result != 0) {
			game = new Game();
			AI1.reset();
			AI2.reset();
			AI1.setGame(game);
			AI2.setGame(game);
			compOne = true;
		}
		if (compOne) {
			AI1.makeMove();
		} else {
			AI2.makeMove();
		}
		compOne = !compOne;
		result = game.checkWin();
		if (result == 1) {
			System.out.println("Comp One Won!  ***");
		} else if (result == -1) {
			System.out.println("Comp Two Won!      ***");
		} else if (result == 4) {
			System.out.println("It was a Tie!          ***");
		}
		displayBoard(game.board);
		}
	}
	
	/**
	 * Resets UI
	 */
	public void reset() {
		for (JButton b : buttons) {
			b.setText("0");
		}
	}

}
