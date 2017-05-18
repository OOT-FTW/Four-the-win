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
	public GameTurn getNextTurn(GameBoard board) {
		// Read next turn from console
		
		System.out.println("Spieler \"" + name + "\" ist am Zug. Geben Sie dazu die Nummer des Feldes an, in das eingeworfen werden soll.");
		
		boolean correctInput;
		
		Scanner scanner = new Scanner(System.in);
		
		GameTurn turn = null;
		boolean fail;
		
		do {
			correctInput = true;
			fail = false;
			
			String value = scanner.nextLine();
			
			if(!value.matches("^[0-9]*$")) {
				fail = true;
			}
			
			int input = 0;
			try {
				input = Integer.parseInt(value);
			}
			catch(NumberFormatException e) {
				fail = true;
			}
			
			if(fail) {
				System.out.println("Die Eingabe hat ein ungültiges Format. Versuchen Sie es erneut.");
				correctInput = false;
				continue;
			}

			
			if(input > 0 && input <= board.getColumns()) {
				turn =  new GameTurn(CompassDirection.NORTH, input);
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
				System.out.println("Die Eingabe ist ungültig. Sie muss zwischen 1 und " + (2 * board.getColumns() + 2 * board.getRows()) + " (inklusive) liegen. Versuchen Sie es erneut.");
				correctInput = false;
				continue;
			}

			if(turn == null || !board.canInsert(turn.getDirection(), turn.getLine())) {
				System.out.println("Die Eingabe ist ungültig, da der Stein an dieser Stelle nicht eingefügt werden kann. Versuchen Sie es erneut.");
				correctInput = false;
				continue;
			}
		} while(!correctInput);
		
		return turn;
	}

}
