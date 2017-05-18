package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.util.Scanner;

/**
 * This class implements a local player that can read a game turn only from the console.
 */
public class LocalPlayer implements Player {

	private String name;
	
	/**
	 * Creates a new local player that can only read inputs from the console.
	 * @param name Name of the player.
	 */
	public LocalPlayer(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public GameTurn getNextTurn(GameBoard board) throws InvalidTurnException {
		// Read next turn from console
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();
		
		return checkInputAndCreateGameTurn(board, userInput);
	}
	
	private GameTurn checkInputAndCreateGameTurn(GameBoard board, String userInput) throws InvalidTurnException {
		if(!userInput.matches("^[0-9]*$")) {
			throw new InvalidTurnException("Die Eingabe hat ein ungültiges Format.");
		}
		
		int input = 0;
		try {
			input = Integer.parseInt(userInput);
		}
		catch(NumberFormatException e) {
			throw new InvalidTurnException("Die Eingabe hat ein ungültiges Format.");
		}
		
		return createGameTurn(board, input);
	}
	
	private GameTurn createGameTurn(GameBoard board, int input) throws InvalidTurnException {
		GameTurn turn;
		
		if(input > 0 && input <= board.getColumns()) {
			turn = new GameTurn(CompassDirection.NORTH, input);
		}
		else if(input > board.getColumns() && input <= board.getColumns() + board.getRows()) {
			turn = new GameTurn(CompassDirection.EAST, input - board.getColumns());
		}
		else if(input > board.getColumns() + board.getRows() && input <= 2 * board.getColumns() + board.getRows()) {
			turn = new GameTurn(CompassDirection.SOUTH, board.getColumns() - (input - board.getColumns() - board.getRows()) + 1);
		}
		else if(input > 2 * board.getColumns() + board.getRows() && input <= 2 * board.getColumns() + 2 * board.getRows()) {
			turn = new GameTurn(CompassDirection.WEST, board.getRows() - (input - 2 * board.getColumns() - board.getRows()) + 1);
		}
		else {
			throw new InvalidTurnException("Die Eingabe ist ungültig. Sie muss zwischen 1 und " + (2 * board.getColumns() + 2 * board.getRows()) + " (inklusive) liegen.");
		}

		if(turn == null || !board.canInsert(turn.getDirection(), turn.getLine())) {
			throw new InvalidTurnException("Die Eingabe ist ungültig, da der Stein an dieser Stelle nicht eingefügt werden kann.");
		}
		else {
			return turn;
		}
	}
	
}
