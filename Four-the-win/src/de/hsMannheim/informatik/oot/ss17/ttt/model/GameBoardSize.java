package de.hsMannheim.informatik.oot.ss17.ttt.model;

/**
 * This class implements the size of a game board, containing a
 * width (number of columns) and a height (number of rows).
 */
public class GameBoardSize {

	private int columns, rows;
	
	public GameBoardSize(int rows, int columns){
		if(Math.min(rows, columns) < 6 || Math.max(columns, rows) < 7) {
			throw new IllegalArgumentException("Invalid board size: " + rows + "x" + columns);
		}
		this.columns = columns;
		this.rows = rows;
	}
	
	/**
	 * Returns the width (number of columns) of the board
	 * @return the number of columns
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * Returns the height (number of rows) of the board
	 * @return the number of rows
	 */
	public int getRows() {
		return rows;
	}
	
}
