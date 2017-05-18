package de.hsMannheim.informatik.oot.ss17.ttt.model;

public class InvalidTurnException extends Exception {

	private CompassDirection direction;
	private int line;
	
	public InvalidTurnException(String message, CompassDirection direction, int line) {
		super(message);
		this.direction = direction;
		this.line = line;
	}
	
	public InvalidTurnException(String message) {
		super(message);
		this.line = -1;
	}
	
	/**
	 * Returns the direction of the invalid turn.
	 * @return direction
	 */
	public CompassDirection getDirection() {
		return direction;
	}
	
	/**
	 * Returns the line of the invalid turn (row/column, starting at the top left corner).
	 * @return line
	 */
	public int getLine() {
		return line;
	}
}
