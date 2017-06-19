package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.io.*;

/**
 * This class contains a representation of the game board with all set tokens.
 */
public class GameBoard {

	private GameToken[][] board;
	private GameBoardSize size;
//	private GameToken currentPlayer;
	private int startPlayerNumber = 1;
	
	/**
	 * Creates a new GameBoard with a specified size.
	 * @param size The size of the new game board.
	 */
	public GameBoard(GameBoardSize size) {
		this.size = size;
		
		board = new GameToken[size.getRows()][size.getColumns()];
		
		for(int row = 0; row < size.getRows(); row++) {
			for(int column = 0; column < size.getColumns(); column++) {
				board[row][column] = GameToken.NONE;
			}
		}
	}
	
	/**
	 * Creates a new GameBoard and restores the situation stored in a file stored at a given path.
	 * @param path Path to the save file.
	 */
	public GameBoard(String path) throws InvalidBoardException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			
			int rows = 0, columns = 0;
			String inputString;
			
			inputString = reader.readLine();
			
			String[] split = inputString.split(" ");
			if(split.length >= 2) {
				try {
					rows = Integer.parseInt(split[split.length - 2]);
					columns = Integer.parseInt(split[split.length - 1]);
				}
				catch(NumberFormatException e) {
					throw new InvalidBoardException("An error occured while reading the file. The board size could not be read.");
				}
			}
			else {
				throw new InvalidBoardException("An error occured while reading the file. The board size could not be read.");
			}
			
			if(Math.min(rows, columns) < 6 || Math.max(rows, columns) < 7) {
				throw new InvalidBoardException("An error occured while reading the file. The board size is too small.");
			}
			
			size = new GameBoardSize(rows, columns);
			board = new GameToken[size.getRows()][size.getColumns()];
			
			// Read empty line
			reader.readLine();
			
			// Read current player
			String value;
			value = reader.readLine();
			
			String[] parts = value.split("Player number: ");
			if(parts.length > 0) {
				value = parts[parts.length - 1];
			}
			else {
				throw new InvalidBoardException("An error occured while reading the file. The file might be corrupted.");
			}

			if(value.equals("1")) {
				startPlayerNumber = 1;
			}
			else if(value.equals("2")) {
				startPlayerNumber = 2;
			}
			else {
				throw new InvalidBoardException("An error occured while reading the file. The file might be corrupted. Invalid player numer.");
			}
			
			// Read empty line
			reader.readLine();
			
			// Read board tokens
			for(int row = 0; row < getRows(); row++) {
				String line = "";
				if(!reader.ready()) {
					throw new InvalidBoardException("An error occured while reading the file. The file might be corrupted.");
				}
				
				line = reader.readLine();
				
				String[] splitLine = line.split(" ");
				if(line.length() != getColumns() * 2 || splitLine.length != getColumns()) {
					throw new InvalidBoardException("An error occured while reading the file. The file might be corrupted.");
				}
				
				for(int column = 0; column < getColumns(); column++) {
					int tokenValue = -1;
					try{
						tokenValue = Integer.parseInt(splitLine[column]);
					}
					catch(NumberFormatException e1) {
						throw new InvalidBoardException("An error occured while reading the file. The file might be corrupted. Error while parsing game tokens.");
					}
					
					if(tokenValue < 0 || tokenValue > 2) {
						throw new InvalidBoardException("An error occured while reading the file. The file might be corrupted. Error while parsing game tokens.");
					}
					
					GameToken token = null;
					if(tokenValue == 0) {
						token = GameToken.NONE;
					}
					else if(tokenValue == 1) {
						token = GameToken.FIRST_PLAYER;
					}
					else if(tokenValue == 2) {
						token = GameToken.SECOND_PLAYER;
					}
					
					setTokenAT(row, column, token);
				}
			}
			
			// Close file
			reader.close();
		}
		catch(IOException e) {
			throw new InvalidBoardException("An error occured while reading the file.");
		}
		catch(InvalidBoardException e) {
			throw new InvalidBoardException(e.getMessage());
		}
		finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new InvalidBoardException("An error occured while reading the file.");
			}
		}
	}
	
	/**
	 * Stores the current situation of the board in a file at a given path.
	 * If the file is already existing a numbered version is created (e.g. file(1).txt).
	 * @param path Path to the save file.
	 */
	public void save(String path, int playerNumber) throws IOException {
		File saveFile = new File(path + "ftw.txt");
		int fileCounter = 0;
		while(saveFile.exists()) {
			fileCounter++;
			saveFile = new File(path + "ftw(" + fileCounter + ").txt");
		}
		saveFile.createNewFile();
		
		PrintWriter writer = new PrintWriter(new FileWriter(saveFile));
		
		// Save board measurements
		writer.println("Board Size: " + getRows() + " " + getColumns());
		writer.println();
		
		// Save current player information
		writer.println("Player number: " + playerNumber);
		writer.println();
		
		// Save board
		for(int row = 0; row < getRows(); row++) {
			for(int column = 0; column < getColumns(); column++) {
				int value;
				if(getTokenAt(row, column).equals(GameToken.NONE)) {
					value = 0;
				}
				else if(getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
					value = 1;
				}
				else if(getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
					value = 2;
				}
				else {
					value = 3;
				}
				
				writer.print(value + " ");
			}
			
			writer.println();
		}

		writer.println();
		writer.println();
		writer.println();

		writer.println("FTW - save file");
		writer.println("Board size: rows columns");
		writer.println("Player number: x");
		writer.println("	x = 1 => Player 1");
		writer.println("	x = 2 => Player 2");
		writer.println("Game tokens:");
		writer.println("	0 = empty");
		writer.println("	1 = Player 1");
		writer.println("	2 = Player2");
		
		writer.close();
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
				for(int column = 0; column < getColumns(); column++) {
					if(board[line][column].equals(GameToken.NONE)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Returns if a game turn is valid in the current situation of the board.
	 * @param turn The game turn to check.
	 * @return true = turn valid, false = turn not valid.
	 */
	public boolean canInsert(GameTurn turn) {
		return canInsert(turn.getDirection(), turn.getLine());
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
	
	public void insertToken(GameTurn turn, GameToken token) throws InvalidTurnException {
		insertToken(turn.getDirection(), turn.getLine(), token);
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
	
	private void setTokenAT(int row, int column, GameToken token) {
		board[row][column] = token;
	}
	
	/**
	 * Returns the number of rows on the game board.
	 * @return The number of rows.
	 */
	public int getRows() {
		return size.getRows();
	}

	/**
	 * Returns the number of columns on the game board.
	 * @return The number of columns.
	 */
	public int getColumns() {
		return size.getColumns();
	}
	
	public int getStartPlayerNumber() {
		return startPlayerNumber;
	}

	/**
	 * Returns if any token is set on the current board.
	 * @return true = there is a token set, false = all fields on the board are free.
	 */
	public boolean containsAnyNonNoneToken() {
		boolean nonNone = false;
		for(int row = 0; row < getRows(); row++) {
			for(int column = 0; column < getColumns(); column++) {
				if(!getTokenAt(row, column).equals(GameToken.NONE)) {
					nonNone = true;
				}
			}
		}
		
		return nonNone;
	}
	
	/**
	 * Returns if all fields of the board are filled with a player token and there are
	 * no free places to insert another token.
	 * @return true = board is full, false = board is not full.
	 */
	public boolean isFull() {
		boolean boardFull = true;
		for(int row = 0; row < getRows(); row++) {
			for(int column = 0; column < getColumns(); column++) {
				if(getTokenAt(row, column).equals(GameToken.NONE)) {
					boardFull = false;
				}
			}
		}
		
		return boardFull;
	}
	
	public GameResult computeGameResult() {
		final int tokensToWin = 4;
		
		boolean player1HasWon = false, player2HasWon = false;
		GameToken previous;
		int number;
		
		// Check rows
		for(int row = 0; row < getRows(); row++) {
			number = 1;
			previous = null;
			
			for(int column = 0; column < getColumns(); column++) {
				if(!getTokenAt(row, column).equals(previous)) {
					number = 1;
					previous = getTokenAt(row, column);
				}
				else {
					if(getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
			}
		}
		
		// Check columns
		for(int column = 0; column < getColumns(); column++) {
			number = 1;
			previous = null;
			
			for(int row = 0; row < getRows(); row++) {
				if(!getTokenAt(row, column).equals(previous)) {
					number = 1;
					previous = getTokenAt(row, column);
				}
				else {
					if(getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
			}
		}
		
		// Check diagonals
		// \
		for(int row = 0; row < getRows(); row++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(row + counter < getRows() && counter < getColumns()) {
				if(!getTokenAt(row + counter, counter).equals(previous)) {
					number = 1;
					previous = getTokenAt(row + counter, counter);
				}
				else {
					if(getTokenAt(row + counter, counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(getTokenAt(row + counter, counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		for(int column = 0; column < getColumns(); column++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(column + counter < getColumns() && counter < getRows()) {
				if(!getTokenAt(counter, column + counter).equals(previous)) {
					number = 1;
					previous = getTokenAt(counter, column + counter);
				}
				else {
					if(getTokenAt(counter, column + counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(getTokenAt(counter, column + counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		
		
		for(int row = 0; row < getRows(); row++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(row - counter >= 0 && counter < getColumns()) {
				if(!getTokenAt(row - counter, counter).equals(previous)) {
					number = 1;
					previous = getTokenAt(row - counter, counter);
				}
				else {
					if(getTokenAt(row - counter, counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(getTokenAt(row - counter, counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		for(int column = 0; column < getColumns(); column++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(column + counter < getColumns() && getRows() - 1 - counter >= 0) { // TODO -1?
				if(!getTokenAt(getRows() - 1 - counter, column + counter).equals(previous)) {
					number = 1;
					previous = getTokenAt(getRows() - 1 - counter, column + counter);
				}
				else {
					if(getTokenAt(getRows() - 1 - counter, column + counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(getTokenAt(getRows() - 1 - counter, column + counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
			
		// Check for a draw
		if(player1HasWon && player2HasWon) {
			return GameResult.DRAW;
		}
		else if(player1HasWon && !player2HasWon) {
			return GameResult.FIRST_PLAYER_WON;
		}
		else if(!player1HasWon && player2HasWon) {
			return GameResult.SECOND_PLAYER_WON;
		}
		
		if(isFull()) {
			return GameResult.DRAW;
		}
		
		return GameResult.NONE;
	}
	
	public GameTurn createGameTurn(int input) throws InvalidTurnException {
		GameTurn turn;
		
		if(input > 0 && input <= this.getColumns()) {
			turn = new GameTurn(CompassDirection.NORTH, input);
		}
		else if(input > this.getColumns() && input <= this.getColumns() + this.getRows()) {
			turn = new GameTurn(CompassDirection.EAST, input - this.getColumns());
		}
		else if(input > this.getColumns() + this.getRows() && input <= 2 * this.getColumns() + this.getRows()) {
			turn = new GameTurn(CompassDirection.SOUTH, this.getColumns() - (input - this.getColumns() - this.getRows()) + 1);
		}
		else if(input > 2 * this.getColumns() + this.getRows() && input <= 2 * this.getColumns() + 2 * this.getRows()) {
			turn = new GameTurn(CompassDirection.WEST, this.getRows() - (input - 2 * this.getColumns() - this.getRows()) + 1);
		}
		else {
			throw new InvalidTurnException("Die Eingabe ist ungültig. Sie muss zwischen 1 und " + (2 * this.getColumns() + 2 * this.getRows()) + " (inklusive) liegen.");
		}

		if(turn == null || !this.canInsert(turn.getDirection(), turn.getLine())) {
			throw new InvalidTurnException("Die Eingabe ist ungültig, da der Stein an dieser Stelle nicht eingefügt werden kann.");
		}
		else {
			return turn;
		}
	}
	
}
