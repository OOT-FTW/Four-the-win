package de.hsMannheim.informatik.oot.ss17.ttt.model;

/**
 * This class contains a representation of the game board with all set tokens.
 */
public class GameBoard {

	private GameToken[][] board;
	private GameBoardSize size;
	private GameToken currentPlayer;
	
	/**
	 * Creates a new GameBoard with a specified size.
	 * @param size The size of the new game board.
	 */
	public GameBoard(GameBoardSize size) {
		this.size = size;
		
		board = new GameToken[size.getHeight()][size.getWidth()];
		
		for(int row = 0; row < size.getHeight(); row++) {
			for(int column = 0; column < size.getWidth(); column++) {
				board[row][column] = GameToken.NONE;
			}
		}
	}
	
	/**
	 * Creates a new GameBoard and restores the situation stored in a file stored at a given path.
	 * @param path Path to the save file.
	 */
	public GameBoard(String path) {
		// TODO read board from file
	}
	
	/**
	 * Stores the current situation of the board in a file at a given path.
	 * If the file is already existing a numbered version is created (e.g. file(1).txt).
	 * @param path Path to the save file.
	 */
	public void save(String path) {
		// TODO save()
	}
	
	/**
	 * Checks if a game turn is valid using the insertion direction and line.
	 * @param direction The direction the piece should be inserted.
	 * @param line The line in which the piece should be inserted.
	 * @return Returns if the turn is valid or not.
	 * 			true = valid, false = not valid
	 */
	public boolean canInsert(CompassDirection direction, int line) {
		if(direction == null) {
			return false;
		}
		
		line--;
		if(direction.equals(CompassDirection.NORTH) || direction.equals(CompassDirection.SOUTH)) {
			if(line >= 0 || line < getColumns()) {
				for(int row = 0; row < getRows(); row++) {
					if(board[row][line].equals(GameToken.NONE)) {
						return true;
					}
				}
			}
		}
		else if(direction.equals(CompassDirection.EAST) || direction.equals(CompassDirection.WEST)) {
			if(line >= 0 || line < getRows()) {
				for(int column = 0; column < getRows(); column++) {
					if(board[column][line].equals(GameToken.NONE)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Inserts a new piece at the board in the specified way.
	 * @param direction The direction the piece should be inserted.
	 * @param line The line in which should be inserted.
	 * @param token The token that should be inserted, representing a special player.
	 * @throws InvalidTurnException Is thrown if the turn is not valid and could not be executed.
	 */
	public void insertToken(CompassDirection direction, int line, GameToken token) throws InvalidTurnException {
		if(direction == null) {
			throw new InvalidTurnException("Invalid turn could not be executed.", direction, line);
		}
		if(!canInsert(direction, line)) {
			throw new InvalidTurnException("Invalid turn could not be executed.", direction, line);
		}
		
		line--;
		if(direction.equals(CompassDirection.NORTH)) {
			int freeIndex = getRows() - 1;
			for(int row = getRows() - 1; row >= 0; row--) {
				if(!board[row][line].equals(GameToken.NONE)) {
					board[freeIndex][line] = board[row][line];
					if(freeIndex != row) {
						board[row][line] = GameToken.NONE;
					}
					freeIndex--;
				}
			}
			board[freeIndex][line] = token;
		}
		else if(direction.equals(CompassDirection.EAST)) {
			int freeIndex = 0;
			for(int column = 0; column < getColumns(); column++) {
				if(!board[line][column].equals(GameToken.NONE)) {
					board[line][freeIndex] = board[line][column];
					if(freeIndex != column) {
						board[line][column] = GameToken.NONE;
					}
					freeIndex++;
				}
			}
			board[line][freeIndex] = token;
		}
		else if(direction.equals(CompassDirection.SOUTH)) {
			int freeIndex = 0;
			for(int row = 0; row < getRows(); row++) {
				
				if(!board[row][line].equals(GameToken.NONE)) {
					board[freeIndex][line] = board[row][line];
					if(freeIndex != row) {
						board[row][line] = GameToken.NONE;
					}
					freeIndex++;
				}
			}
			board[freeIndex][line] = token;
		}
		else if(direction.equals(CompassDirection.WEST)) {
			int freeIndex = getColumns() - 1;
			for(int column = getColumns() - 1; column >= 0; column--) {
				
				if(!board[line][column].equals(GameToken.NONE)) {
					board[line][freeIndex] = board[line][column];
					if(freeIndex != column) {
						board[line][column] = GameToken.NONE;
					}
					freeIndex--;
				}
			}
			board[line][freeIndex] = token;
		}
	}
	
	/**
	 * Returns the game token, representing the game piece of a specific player, at a given position.
	 * @param row The row the piece is in.
	 * @param columnThe column the piece is in.
	 * @return The current game token at this specific position, representing a specific player or empty.
	 */
	public GameToken getTokenAt(int row, int column) {
		return board[row][column];
	}
	
	/**
	 * Returns the number of rows on the game board.
	 * @return The number of rows.
	 */
	public int getRows() {
		return size.getHeight();
	}

	/**
	 * Returns the number of columns on the game board.
	 * @return The number of columns.
	 */
	public int getColumns() {
		return size.getWidth();
	}
	
}
