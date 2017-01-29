package main;

import java.util.Scanner;

public class TestGame {

	public static void main(String[] args) {
		Computer AI = new Computer();
		Scanner input = new Scanner(System.in);
		
		while(true) {
			Game game = new Game();
			AI.setGame(game);
			
		}
	}

}
