package de.hsMannheim.informatik.oot.ss17.ttt.model;

/**
 * This class implements the size of a game board, containing a
 * width (number of columns) and a height (number of rows).
 */
public class GameBoardSize {

	private int width, height;
	
	public GameBoardSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the width (number of columns) of the board
	 * @return the number of columns
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height (number of rows) of the board
	 * @return the number of rows
	 */
	public int getHeight() {
		return height;
	}
	
}
