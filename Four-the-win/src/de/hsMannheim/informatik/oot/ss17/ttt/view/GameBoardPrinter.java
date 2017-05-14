package de.hsMannheim.informatik.oot.ss17.ttt.view;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class GameBoardPrinter {

	private static final int offset = 2;
	private static final char symbolFirstPlayer = 'O', symbolSecondPlayer = 'X';
	private GameBoard board;
	
	/**
	 * Creates a new GameBoardPrinter for a specific board.
	 * @param board
	 */
	public GameBoardPrinter(GameBoard board) {
		this.board = board;
	}
	
	/**
	 * Prints the current state of the board on the console and displays the input
	 * possibilities like ordered by the customer.
	 */
	public void print() {
		if(board == null) {
			// TODO log event
			System.out.println("Field not initialized.");
		}		
		
		int columns = board.getColumns();
		int rows = board.getRows();
		
		if(Math.min(rows, columns) < 6 || Math.max(rows, columns) < 7) {
			// TODO log event
			System.out.println("Field not properly initialized.");;
		}

		System.out.println();
		System.out.println("Spielfeld:");
		System.out.println();
		
		// Print north (N)
		for(int i = 0; i < ((2 * offset) + columns) * 3; i++) {
			if((i - offset) == (columns + 1 + columns / 2)) {
				System.out.print("N");
			}
			else {
				System.out.print(" ");
			}
			
		}
		System.out.println(); // End of line
		
		// Print upper column count
		System.out.print("  + ");
		for(int i = 1; i <= columns; i++) {
			if(i < 10) {
				System.out.print(" " + i + " ");
			}
			else {
				System.out.print(i + " ");
			}
		}
		System.out.print("+  ");
		System.out.println(); // End of line
		
		// Print field
		for(int row = 0; row < rows; row++) {
			if(row == (rows / 2)) {
				System.out.print("W ");
			}
			else {
				System.out.print("  ");
			}
			
			System.out.print(((rows - row) + 2 * columns + rows) + " ");
			for(int column = 0; column < columns; column++) {
				System.out.print(" ");
				if(board.getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
					System.out.print(symbolFirstPlayer);
				}
				else if (board.getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
					System.out.print(symbolSecondPlayer);
				}
				else {
					System.out.print(" ");
				}
				System.out.print(" ");
			}
			
			System.out.print(row + 1 + columns);
			
			if(row == (rows / 2)) {
				System.out.print(" O");
			}
			else {
				System.out.print("  ");
			}

			System.out.println();
			System.out.println(); // End of line
		}
		
		// Print lower column count
		System.out.print("  + ");
		for(int column = 1; column <= columns; column++) {
			if(((columns - column) + columns + rows + 1) < 10) {
				System.out.print(" " + ((columns - column) + columns + rows + 1) + " ");
			}
			else {
				System.out.print(((columns - column) + columns + rows + 1) + " ");
			}
		}
		System.out.print("+  ");
		System.out.println(); // End of line
		
		// Print south (S)
		for(int i = 0; i < ((2 * offset) + columns) * 2; i++) {
			if((i - offset) == (columns + 1 + columns / 2)) {
				System.out.print("S");
			}
			else {
				System.out.print(" ");
			}

		}
		System.out.println(""); // End of line
	}

	/**
	 * Prints on the console that the game has ended. If one player won the winner is displayed,
	 * otherwise the end in a draw is displayed.
	 * @param result The result of the game.
	 * @param winner The player that won the game.
	 */
	public void printWinner(GameResult result, Player winner) {
		System.out.println("Das Spiel ist beendet.");
		if(result == null) {
			System.out.println("Es konnte kein Gewinner festgestellt werden.");
		}
		else if(result.equals(GameResult.DRAW)) {
			System.out.println("Das Spiel ist unentschieden ausgegangen.");
		}
		else if(result.equals(GameResult.NONE)) {
			System.out.println("Kein Spieler hat gewonnen.");
		}
		else if(result.equals(GameResult.FIRST_PLAYER_WON) || result.equals(GameResult.SECOND_PLAYER_WON)) {
			System.out.println("Spieler \"" + winner.getName() + "\" hat gewonnen.");
		}
	}
}
