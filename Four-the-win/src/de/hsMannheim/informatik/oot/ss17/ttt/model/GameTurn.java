package de.hsMannheim.informatik.oot.ss17.ttt.model;

/**
 * This class implements a single turn, where a direction and a line are used
 * to describe the input position.
 */
public class GameTurn {

	private CompassDirection direction;
	private int line;
	
	public GameTurn(CompassDirection direction, int line) {
		this.direction = direction;
		this.line = line;
	}
	
	/**
	 * Returns the direction in which the piece should be inserted.
	 * @return the direction
	 */
	public CompassDirection getDirection() {
		return direction;
	}
	
	/**
	 * Returns the line (row or column) the piece should be inserted in.
	 * The numbers start in the top left corner and vary from the user interface representation.
	 * @return the line the piece should be inserted.
	 */
	public int getLine() {
		return line;
	}
}
