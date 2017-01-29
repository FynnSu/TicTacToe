package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TestGame implements ActionListener{

	static Computer computer;
	static Game game;
	private String[][] tttBoard = new String[3][3];
	
	JFrame frame = new JFrame("Tic Tac Toe");
	JPanel contentPane = new JPanel();
	JButton play = new JButton("Play"),
			quit = new JButton("Quit");
	
	JButton[] buttons = new JButton[9];
	JLabel player = new JLabel("  X goes first"),
			winner = new JLabel(" "),
			score = new JLabel(" ");

	JTextArea spaceHolder = new JTextArea("");
	
	int moves;
	int xWins, oWins, tie;
	String 	player1 = "X",
			player2 = "O",
			current;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
	
	private static void printBoard(int board[][]) {
		System.out.println("" + board[0][0] + board[1][0] + board[2][0]);
		System.out.println("" + board[0][1] + board[1][1] + board[2][1]);
		System.out.println("" + board[0][2] + board[1][2] + board[2][2]);
	}
	
	/**
	* constructor
	* pre: none
	* post: ttBoard has been initialized.
	* player1 is X and player2 is O
	*/
	public TestGame() {
		computer = new Computer();
		game = new Game();
		computer.setGame(game);
		for(int row = 0; row < tttBoard.length; row++) {
			for (int col = 0; col < tttBoard[0].length; col++) {
				tttBoard[row][col] = "";
			}
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout(new GridLayout(5, 4));
		
		player.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		player.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		player.setFont(new Font("Calibri", Font.BOLD, 100));
		contentPane.add(player);
		
		spaceHolder.setEditable(false);
		spaceHolder.setBackground(Color.LIGHT_GRAY);
		contentPane.add(spaceHolder);
		
		winner.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		winner.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		winner.setFont(new Font("Calibri", Font.ITALIC, 65));
		contentPane.add(winner);
		
		for (int x = 0; x < buttons.length; x++) {
			JButton b = new JButton("");
			buttons[x] = b;
		}
		setupButtons();
		
		setupButton(play, Color.GREEN);
		
		score.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		score.setFont(new Font("Calibri", Font.BOLD, 65));
		contentPane.add(score);
		
		
		setupButton(quit, Color.GREEN);
		
		frame.setContentPane(contentPane);
	
		frame.pack();
		frame.setVisible(true);
		
		enableButtons(false);
		
	}
	
	private void setupButtons() {
		for (int x = 0; x < buttons.length; x++) {
			JButton b = buttons[x];
			b.setActionCommand(Integer.toString(x));
			b.addActionListener(this);
			b.setFont(new Font("Calibri", Font.BOLD, 50));
			b.setBackground(Color.WHITE);
			contentPane.add(b);
		}
	}
		
	private void setupButton(JButton b, Color c) {
		b.setActionCommand(b.getText());
		b.addActionListener(this);
		b.setFont(new Font("Calibri", Font.BOLD, 50));
		b.setBackground(c);
		contentPane.add(b);
	}
		
	/**
	* pre: none;
	* post: a mark has been made in an emptyt tic-tac-toe board square.
	*/
	private boolean makeMove(int x, int y) {
		boolean valid = false;		
		if (tttBoard[x][y].equals("")){
			tttBoard[x][y] = "" + current + "";
			displayBoard();
			moves++;
			valid = true;
		}
		move(x, y);
		
		return (valid);
	}
	
	
	public void displayBoard(){
		int current = 0;
		for (String[] row : tttBoard) {
			for (String marker: row) {
				buttons[current].setText(marker);
				current++;
			}
		}
		
		score.setText("     X: " + xWins + "     O: " + oWins + "     Tie: " + tie);
	}
	
	public void enableButtons(boolean b) {
		for (JButton button: buttons) {
			button.setEnabled(b);
		}
	}
	
	public void move(int col, int row) {
		int result = game.move(col, row);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		
		if (eventName.equals("0")) {
			move(0, 0);
			makeMove(0, 0);
		} else if (eventName.equals("1")) {
			move(0, 1);
			makeMove(0, 1);
		} else if (eventName.equals("2")) {
			move(0, 2);
			makeMove(0, 2);
		} else if (eventName.equals("3")) {
			move(1, 0);
			makeMove(1, 0);
		} else if (eventName.equals("4")) {
			move(1, 1);
			makeMove(1, 1);
		} else if (eventName.equals("5")) {
			move(1, 2);
			makeMove(1, 2);
		} else if (eventName.equals("6")) {
			move(2, 0);
			makeMove(2, 0);
		} else if (eventName.equals("7")) {
			move(2, 1);
			makeMove(2, 1);
		} else if (eventName.equals("8")) {
			move(2, 2);
			makeMove(2, 2);
		} else if (eventName.equals("Play")) {
			enableButtons(true);
			displayBoard();
			current = player1;
			player.setVisible(true);
			play.setText("Play Again");
			play.setActionCommand("Play Again");
		} else if (eventName.equals("Play Again")) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					tttBoard[x][y] = "";
				}
			}
			enableButtons(true);
			displayBoard();
			current = player1;
			player.setVisible(true);
			displayBoard();
			moves = 0;
			winner.setText(" ");
			player.setText("        " + current + "'s turn");
		} else if (eventName.equals("Quit")) {
			System.exit(0);
		}
		displayBoard();
		printBoard(game.board);
	}
	
	private void switchPlayer() {
		if(current.equals(player1)) {
			current = player2;
		}else {
			current = player1;
		}
		player.setText("        " + current + "'s turn");
	}
	
	public static void runGUI() {
		JFrame.setDefaultLookAndFeelDecorated(false);
		new TestGame();
	}

}
