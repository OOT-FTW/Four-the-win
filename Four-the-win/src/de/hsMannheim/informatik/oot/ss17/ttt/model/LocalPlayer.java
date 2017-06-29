package de.hsMannheim.informatik.oot.ss17.ttt.model;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements a local player that can read a game turn only from the console.
 */
public class LocalPlayer implements Player {

	private String name;
	private int number;
	
	/**
	 * Creates a new local player that can only read inputs from the console.
	 * @param name Name of the player.
	 */
	public LocalPlayer(String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}

	@Override
	public GameTurn getNextTurn(GameBoard board) throws InvalidTurnException {
		// Read next turn from console
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.nextLine();
		
		return checkInputAndCreateGameTurn(board, userInput);
	}
	
	private GameTurn checkInputAndCreateGameTurn(GameBoard board, String userInput) throws InvalidTurnException {
		Scanner scanner = new Scanner(System.in);
		if(userInput.equals("/save")) {
			System.out.println("Den Pfad angeben, in dem das Spiel gespeichert werden soll: ");
			
			
			String path = scanner.nextLine();
			try {
				board.save(path, number);
			} catch (IOException e) {
				System.out.println("Das Spiel konnte nicht gespeichert werden.");
			}
		}
		
		if(!userInput.matches("^[0-9]*$")) {
			throw new InvalidTurnException("Die Eingabe hat ein ungültiges Format.");
		}
		else if(userInput.equals("/quit")) {
			System.out.println("Auf Wiedersehen.");
			System.exit(0);
		}
		
		userInput = scanner.nextLine();
		
		int input = 0;
		try {
			input = Integer.parseInt(userInput);
		}
		catch(NumberFormatException e) {
			throw new InvalidTurnException("Die Eingabe hat ein ungültiges Format.");
		}
		
		return board.createGameTurn(input);
	}
	
//	private GameTurn createGameTurn(GameBoard board, int input) throws InvalidTurnException {
//		GameTurn turn;
//		
//		if(input > 0 && input <= board.getColumns()) {
//			turn = new GameTurn(CompassDirection.NORTH, input);
//		}
//		else if(input > board.getColumns() && input <= board.getColumns() + board.getRows()) {
//			turn = new GameTurn(CompassDirection.EAST, input - board.getColumns());
//		}
//		else if(input > board.getColumns() + board.getRows() && input <= 2 * board.getColumns() + board.getRows()) {
//			turn = new GameTurn(CompassDirection.SOUTH, board.getColumns() - (input - board.getColumns() - board.getRows()) + 1);
//		}
//		else if(input > 2 * board.getColumns() + board.getRows() && input <= 2 * board.getColumns() + 2 * board.getRows()) {
//			turn = new GameTurn(CompassDirection.WEST, board.getRows() - (input - 2 * board.getColumns() - board.getRows()) + 1);
//		}
//		else {
//			throw new InvalidTurnException("Die Eingabe ist ungültig. Sie muss zwischen 1 und " + (2 * board.getColumns() + 2 * board.getRows()) + " (inklusive) liegen.");
//		}
//
//		if(turn == null || !board.canInsert(turn.getDirection(), turn.getLine())) {
//			throw new InvalidTurnException("Die Eingabe ist ungültig, da der Stein an dieser Stelle nicht eingefügt werden kann.");
//		}
//		else {
//			return turn;
//		}
//	}
	
}
