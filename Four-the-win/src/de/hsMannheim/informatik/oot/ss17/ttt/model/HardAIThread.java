package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.ArrayList;

/**
 * The current implementation of this class uses an integer array as an
 * internal representation of the game board to increase performance.
 */
public class HardAIThread extends Thread {

	private static final int NONE = 0, FIRST_PLAYER = 1, SECOND_PLAYER = 2;
	private int index, maxLevel;
	private AIPlayerHard ai;
	private int[][] board;
	private GameToken playerToken, maxPlayer, minPlayer;
	private GameTurn initialTurn;
	
	/**
	 * Initialize the depth search of the part of the game tree started by the initial turn.
	 * @param index Index of the initial turn.
	 * @param player The player that executes the depth search and gets the results returned.
	 * @param board The current situation of the game board.
	 * @param playerToken The token of the current player (AI Player)
	 * @param maxLevel The limit of the depth search.
	 * @param initialTurn The game turn that is executed initially.
	 */
	public HardAIThread(int index, AIPlayerHard player, GameBoard board,
						GameToken playerToken, int maxLevel, GameTurn initialTurn) {
		this.index = index;
		this.ai = player;

		// Convert GameBoard object to int[][] array to increase performance
		this.board = new int[board.getRows()][board.getColumns()];
		for(int row = 0; row < board.getRows(); row++) {
			for(int column = 0; column < board.getColumns(); column++) {
				if(board.getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
					this.board[row][column] = FIRST_PLAYER;
				}
				else if(board.getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
					this.board[row][column] = SECOND_PLAYER;
				}
			}
		}
		
		this.playerToken = playerToken;
		this.maxLevel = maxLevel;
		this.initialTurn = initialTurn;
		
		this.maxPlayer = playerToken;
		if(playerToken.equals(GameToken.FIRST_PLAYER)) {
			this.minPlayer = GameToken.SECOND_PLAYER;
		}
		else {
			this.minPlayer = GameToken.FIRST_PLAYER;
		}
	}
	
	/**
	 * Executes the depth search for the best turn and writes the rating back to the AI player.
	 */
	public void run() {
		int rating = 0;
		try {
			// Call recursive depth search to rate the current turn
			rating = rateNextTurn(board, initialTurn, playerToken, 1);
		} catch (InvalidTurnException e) {
			System.out.println("Something went wrong while calculating the next turn.");
			e.printStackTrace();
		}
		
		// "Return" the calculated value to the caller AI
		ai.setTurnRating(index, rating);
	}
	
	/**
	 * Rates the game situation after a specific game turn has been executed.
	 * @param board Game board to rate.
	 * @param turn Next game turn to execute.
	 * @param player The player number of the player that sets the next token.
	 * @param level Recursion level.
	 * @return Returns the rating of the board if the next turn (turn) is executed.
	 * @throws InvalidTurnException If the passed game turn (turn) is invalid.
	 */
	private int rateNextTurn(int[][] board, GameTurn turn, GameToken player, int level) throws InvalidTurnException {
		int token;
		if(player.equals(GameToken.FIRST_PLAYER)) {
			token = FIRST_PLAYER;
		}
		else {
			token = SECOND_PLAYER;
		}
		
		insertToken(board, turn.getDirection(), turn.getLine(), token);
		
		// Check if one player has won
		int boardRating = rateBoard(board);
		if(boardRating == Integer.MAX_VALUE || boardRating == Integer.MIN_VALUE || boardRating == 0) {
			return boardRating;
		}
		
		if(level > maxLevel) {
			// The limit of the depth search is reached and the algorithm rates the unfinished situation
			return rateUnfinishedBoard(board);
		}
		
		GameToken newPlayer;
		if(player.equals(GameToken.FIRST_PLAYER)) {
			newPlayer = GameToken.SECOND_PLAYER;
		}
		else {
			newPlayer = GameToken.FIRST_PLAYER;
		}
		
		GameTurn[] nextTurns = createValidGameTurns(board);
		
		if(nextTurns.length == 0) {
			return boardRating;
		}
		else {
			int extreme;
			if(newPlayer.equals(minPlayer)) {
				extreme = Integer.MAX_VALUE;
			}
			else {
				extreme = Integer.MIN_VALUE;
			}
			
			// Iterate over all valid follow-up turns
			for(int i = 0; i < nextTurns.length; i++) {
				// Copy board array
				int[][] b2 = new int[board.length][board[0].length];
				for(int row = 0; row < board.length; row++) {
					for(int column = 0; column < board[0].length; column++) {
						b2[row][column] = board[row][column];
					}
				}
				
				// recursive call for all valid follow-up turns
				int z = rateNextTurn(b2, nextTurns[i], newPlayer, level + 1);
				
				// Find best / worst turn (regarding the current target: min / max)
				if(newPlayer.equals(minPlayer)) {
					// Return minimum value
					if(z == Integer.MIN_VALUE) {
						return Integer.MIN_VALUE;
					}
					else {
						if(z < extreme) {
							extreme = z;
						}
					}
				}
				else {
					// Return maximum value
					if(z == Integer.MAX_VALUE) {
						return Integer.MAX_VALUE;
					}
					else {
						if(z > extreme) {
							extreme = z;
						}
					}
				}
			}
			
			return extreme;
		}
	}
	
	/**
	 * Calculated the game result and returns if a player won or a draw occurred.
	 * @param board The board to rate.
	 * @return 0 = draw, MIN / MAX_VALUE if a player won and -1 if the game has not ended yet.
	 */
	private int rateBoard(int[][] board) {
		switch(computeGameResult(board)) {
		case FIRST_PLAYER_WON: if(maxPlayer.equals(GameToken.SECOND_PLAYER))
									return Integer.MIN_VALUE;
								else
									return Integer.MAX_VALUE;
		case SECOND_PLAYER_WON: if(maxPlayer.equals(GameToken.SECOND_PLAYER))
									return Integer.MAX_VALUE;
								else
									return Integer.MIN_VALUE;
		case DRAW: return 0;
		default: return -1;
		}
	}
	
	/**
	 * Rates a field on the game board by adding the fitting weight to a rating.
	 * @param board The board the field is on.
	 * @param row The row the field is in.
	 * @param column The column the field is in.
	 * @param previous The value of the previous field.
	 * @param number The number of equal field in a row.
	 * @param scores An array where the scores of both players are stored.
	 * @param weights An array with the corresponding weights for different numbers of tokens in a row.
	 * @return
	 */
	private int rateUnfinishedField(int[][] board, int row, int column, int previous, int number, int[] scores, int[] weights) {
		if(board[row][column] != previous) {
			number = 1;
			previous = board[row][column];
			
			scores[board[row][column]] += weights[0];
		}
		else {
			if(number == 1 || number == 2) {
				scores[board[row][column]] -= weights[number - 1];
				scores[board[row][column]] += weights[number];
			}
			number++;
		}
		
		return number;
	}
	
	/**
	 * Uses a rating algorithm to rate a situation on the board to find the best next turn.
	 * @param board The board to rate.
	 * @return The rating of the rating function.
	 */
	private int rateUnfinishedBoard(int[][] board) {
		int winRating = rateBoard(board);
		if(winRating == Integer.MAX_VALUE || winRating == Integer.MIN_VALUE || winRating == 0) {
			return winRating;
		}
		
		int[] scores = new int[3];
		
		final int singleWeight = 10, doubleWeight = 100, tripleWeight = 10000;
		int[] weights = new int[3];
		weights[0] = singleWeight;
		weights[1] = doubleWeight;
		weights[2] = tripleWeight;
		
		
		int previous, number;
		
		// Check rows
		for(int row = 0; row < board.length; row++) {
			number = 1;
			previous = -1;
			
			for(int column = 0; column < board[0].length; column++) {
				number = rateUnfinishedField(board, row, column, previous, number, scores, weights);
				previous = board[row][column];
			}
		}
		
		// Check columns
		for(int column = 0; column < board[0].length; column++) {
			number = 1;
			previous = -1;
			
			for(int row = 0; row < board.length; row++) {
				number = rateUnfinishedField(board, row, column, previous, number, scores, weights);
				previous = board[row][column];
			}
		}
		
		// Check diagonals (\)
		for(int row = 0; row < board.length; row++) { // counter <  && counter < board[0].length
			number = 1;
			previous = -1;
			
			for(int counter = 0; row + counter < board.length && counter < board[0].length; counter++) {
				number = rateUnfinishedField(board, row + counter, counter, previous, number, scores, weights);
				previous = board[row + counter][counter];
			}
		}
		
		for(int column = 0; column < board[0].length; column++) {
			number = 1;
			previous = -1;
			
			for(int counter = 0; column + counter < board[0].length && counter < board.length; counter++) {
				number = rateUnfinishedField(board, counter, column + counter, previous, number, scores, weights);
				previous = board[counter][column + counter];
			}
		}
		
		// (/)
		for(int row = 0; row < board.length; row++) {
			number = 1;
			previous = -1;
			
			for(int counter = 0; row - counter >= 0 && counter < board[0].length; counter++) {
				number = rateUnfinishedField(board, row - counter, counter, previous, number, scores, weights);
				previous = board[row - counter][counter];
			}
		}
		
		for(int column = 0; column < board[0].length; column++) {
			number = 1;
			previous = -1;
			
			for(int counter = 0; column + counter < board[0].length && board.length - 1 - counter >= 0; counter++) {
				number = rateUnfinishedField(board, board.length - 1 - counter, column + counter, previous, number, scores, weights);
				previous = board[board.length - 1 - counter][column + counter];
			}
		}
		
			
		if(maxPlayer.equals(GameToken.SECOND_PLAYER)) {
			return scores[2] - scores[1];
		}
		else {
			return scores[1] - scores[2];
		}
	}
	
	
	// Board utilities
	
	/**
	 * Creates an array containing all valid turns that could be executed on a specific board.
	 * @param board The board to find all valid turns for.
	 * @return All valid turns, empty array if there are no more valid turns.
	 */
	private GameTurn[] createValidGameTurns(int[][] board) {
		ArrayList<GameTurn> turns = new ArrayList<GameTurn>();
		
		// Direction: North / South
		for(int line = 1; line <= board[0].length; line++) {
			if(canInsert(board, CompassDirection.NORTH, line)) {
				turns.add(new GameTurn(CompassDirection.NORTH, line));
			}
			if(canInsert(board, CompassDirection.SOUTH, line)) {
				turns.add(new GameTurn(CompassDirection.SOUTH, line));
			}
		}
		
		// Direction: EAST / WEST
		for(int line = 1; line <= board.length; line++) {
			if(canInsert(board, CompassDirection.EAST, line)) {
				turns.add(new GameTurn(CompassDirection.EAST, line));
			}
			if(canInsert(board, CompassDirection.WEST, line)) {
				turns.add(new GameTurn(CompassDirection.WEST, line));
			}
		}
		
		
		GameTurn[] validTurns = new GameTurn[turns.size()];
		for(int i = 0; i < turns.size(); i++) {
			validTurns[i] = turns.get(i);
		}
		return validTurns;
	}
	
	/**
	 * Inserts a new game token to a game board in the specified way.
	 * @param board Board to insert in.
	 * @param direction The direction to insert from.
	 * @param line The line number to insert in.
	 * @param token The token to insert.
	 * @throws InvalidTurnException if the turn is not valid on the current board.
	 */
	private void insertToken(int[][] board, CompassDirection direction, int line, int token) throws InvalidTurnException {
		line--;
		if(direction.equals(CompassDirection.NORTH)) {
			int freeIndex = board.length - 1;
			for(int row = board.length - 1; row >= 0; row--) {
				if(board[row][line] != NONE) {
					board[freeIndex][line] = board[row][line];
					if(freeIndex != row) {
						board[row][line] = NONE;
					}
					freeIndex--;
				}
			}
			board[freeIndex][line] = token;
		}
		else if(direction.equals(CompassDirection.EAST)) {
			int freeIndex = 0;
			for(int column = 0; column < board[0].length; column++) {
				if(board[line][column] != NONE) {
					board[line][freeIndex] = board[line][column];
					if(freeIndex != column) {
						board[line][column] = NONE;
					}
					freeIndex++;
				}
			}
			board[line][freeIndex] = token;
		}
		else if(direction.equals(CompassDirection.SOUTH)) {
			int freeIndex = 0;
			for(int row = 0; row < board.length; row++) {
				
				if(board[row][line] != NONE) {
					board[freeIndex][line] = board[row][line];
					if(freeIndex != row) {
						board[row][line] = NONE;
					}
					freeIndex++;
				}
			}
			board[freeIndex][line] = token;
		}
		else if(direction.equals(CompassDirection.WEST)) {
			int freeIndex = board[0].length - 1;
			for(int column = board[0].length - 1; column >= 0; column--) {
				
				if(board[line][column] != NONE) {
					board[line][freeIndex] = board[line][column];
					if(freeIndex != column) {
						board[line][column] = NONE;
					}
					freeIndex--;
				}
			}
			board[line][freeIndex] = token;
		}
	}

	/**
	 * Checks if a given game board is full (no free places).
	 * @param board Game board to check.
	 * @return true = board is full, false = board is not full.
	 */
	private boolean isFull(int[][] board) {
		for(int row = 0; row < board.length; row++) {
			for(int column = 0; column < board[0].length; column++) {
				if(board[row][column] == NONE) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if a specific turn could be executed.
	 * @param board Board to insert the token.
	 * @param direction In which direction the token is inserted.
	 * @param line in which line the token is inserted.
	 * @return Returns if the turn could be executed.
	 * 			true = could be executed, false = could not be executed.
	 */
	private boolean canInsert(int[][] board, CompassDirection direction, int line) {
		line--;
		if(direction.equals(CompassDirection.NORTH) || direction.equals(CompassDirection.SOUTH)) {
			if(line >= 0 || line < board[0].length) {
				for(int row = 0; row < board.length; row++) {
					if(board[row][line] == NONE) {
						return true;
					}
				}
			}
		}
		else if(direction.equals(CompassDirection.EAST) || direction.equals(CompassDirection.WEST)) {
			if(line >= 0 || line < board.length) {
				for(int column = 0; column < board[0].length; column++) {
					if(board[line][column] == NONE) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the game has ended and if a player won.
	 * @param board The game board to check for the situation.
	 * @return Game result (the winner, draw, no game result.
	 */
	private GameResult computeGameResult(int[][] board) {
		final int tokensToWin = 4;
		
		boolean player1HasWon = false, player2HasWon = false;
		int previous;
		int number;
		
		// Check rows
		for(int row = 0; row < board.length; row++) {
			number = 1;
			previous = -1;
			
			for(int column = 0; column < board[0].length; column++) {
				if(board[row][column] != previous) {
					number = 1;
					previous = board[row][column];
				}
				else {
					if(board[row][column] == FIRST_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board[row][column] == SECOND_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
			}
		}
		
		if(player1HasWon && player2HasWon) {
			return GameResult.DRAW;
		}
		
		// Check columns
		for(int column = 0; column < board[0].length; column++) {
			number = 1;
			previous = -1;
			
			for(int row = 0; row < board.length; row++) {
				if(board[row][column] != previous) {
					number = 1;
					previous = board[row][column];
				}
				else {
					if(board[row][column] == FIRST_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board[row][column] == SECOND_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
			}
		}
		
		if(player1HasWon && player2HasWon) {
			return GameResult.DRAW;
		}
		
		// Check diagonals \
		for(int row = 0; row < board.length; row++) {
			number = 1;
			previous = -1;
			
			int counter = 0;
			while(row + counter < board.length && counter < board[0].length) {
				if(board[row + counter][counter] != previous) {
					number = 1;
					previous = board[row + counter][counter];
				}
				else {
					if(board[row + counter][counter] == FIRST_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board[row + counter][counter] == SECOND_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		for(int column = 0; column < board[0].length; column++) {
			number = 1;
			previous = -1;
			
			int counter = 0;
			while(column + counter < board[0].length && counter < board.length) {
				if(board[counter][column + counter] != previous) {
					number = 1;
					previous = board[counter][column + counter];
				}
				else {
					if(board[counter][column + counter] == FIRST_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board[counter][column + counter] == SECOND_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		
		if(player1HasWon && player2HasWon) {
			return GameResult.DRAW;
		}
		
		// Check diagonals /
		for(int row = 0; row < board.length; row++) {
			number = 1;
			previous = -1;
			
			int counter = 0;
			while(row - counter >= 0 && counter < board[0].length) {
				if(board[row - counter][counter] != previous) {
					number = 1;
					previous = board[row - counter][counter];
				}
				else {
					if(board[row - counter][counter] == FIRST_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board[row - counter][counter] == SECOND_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		for(int column = 0; column < board[0].length; column++) {
			number = 1;
			previous = -1;
			
			int counter = 0;
			while(column + counter < board[0].length && board.length - 1 - counter >= 0) {
				if(board[board.length - 1 - counter][column + counter] != previous) {
					number = 1;
					previous = board[board.length - 1 - counter][column + counter];
				}
				else {
					if(board[board.length - 1 - counter][column + counter] == FIRST_PLAYER) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board[board.length - 1 - counter][column + counter] == SECOND_PLAYER) {
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
		
		if(isFull(board)) {
			return GameResult.DRAW;
		}
		
		return GameResult.NONE;
	}
	
}
