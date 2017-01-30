package main;

/**
 * Notifies computer classes of move made
 * @author Fynn
 *
 */
public interface moveListener {

	/**
	 * A move has been made
	 * @param col of move 
	 * @param row of move
	 */
	public void moveMade(int col, int row);
	
}
