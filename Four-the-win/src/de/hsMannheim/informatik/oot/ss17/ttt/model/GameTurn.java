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
	
	public GameTurn(GameBoardSize size, int field) {
		CompassDirection[] allDirections = {
				CompassDirection.NORTH, 
				CompassDirection.EAST, 
				CompassDirection.SOUTH, 
				CompassDirection.WEST
			};
		int[] startFieldAtDirection = {
				0,
				size.getColumns(),
				size.getColumns() + size.getRows(),
				size.getColumns() + size.getRows() + size.getColumns(),
			};
		int[] endFieldAtDirection = {
			size.getColumns(),
			size.getColumns() + size.getRows(),
			size.getColumns() + size.getRows() + size.getColumns(),
			size.getColumns() + size.getRows() + size.getColumns() + size.getRows(),
		};
		for(int index = 0; index < allDirections.length; index++) {
			CompassDirection direction = allDirections[index];
			int startField = startFieldAtDirection[index];
			int endField = endFieldAtDirection[index];
			if(field <= endField) {
				this.direction = direction;
				this.line = field - startField;
				break;
			}
		}
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
	
	public int getField(GameBoardSize size) {
		switch (this.direction) {
		case NORTH:
			return this.getLine();
		case EAST:
			return this.getLine() + size.getColumns();
		case SOUTH:
			return this.getLine() + size.getColumns() + size.getRows();
		case WEST:
			return this.getLine() + size.getColumns() + size.getRows() + size.getColumns();
		}
		assert(false);
		return 0;
	}
}
