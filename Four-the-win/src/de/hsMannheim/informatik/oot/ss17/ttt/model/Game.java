package de.hsMannheim.informatik.oot.ss17.ttt.model;

import de.hsMannheim.informatik.oot.ss17.ttt.view.GameBoardPrinter;

public class Game {
	
	private Player[] player;
	private int currentPlayerID = 0;
	private GameBoardPrinter printer;
	private GameBoard board;
	
	public Game(Player firstPlayer, Player secondPlayer, GameBoard board) {
		this.player = new Player[2];
		this.player[0] = firstPlayer;
		this.player[1] = secondPlayer;
		
		this.board = board;
		this.currentPlayerID = board.getCurrentPlayerNumber() - 1;
		
		this.printer = new GameBoardPrinter(board);
	}
	
	public void play() {
		
		boolean gameEnded = false;
		do {
			printer.print();
			
			boolean validInput = false;
			System.out.println("Spieler \"" + getCurrentPlayer().getName() + "\" ist am Zug."
					+ " Geben Sie dazu die Nummer des Feldes an, in das eingeworfen werden soll.");
			while(!validInput) {
				try {
					executeNextTurn();
					validInput= true;
				} catch (InvalidTurnException e) {
					System.out.println(e.getMessage() + " Versuchen Sie es erneut.");
				}
			}
			
			GameResult status = computeGameResult();
			if(!status.equals(GameResult.NONE)) {
				// Print winner
				printer.printWinner(status, getCurrentPlayer());
				printer.print();
				gameEnded = true;
			}
			else {
				nextPlayer();
			}
		} while(!gameEnded);
	}
	
	private void executeNextTurn() throws InvalidTurnException {
		GameTurn nextTurn = getCurrentPlayer().getNextTurn(board);
		
		GameToken token;
		if(currentPlayerID == 0) {
			token = GameToken.FIRST_PLAYER;
		}
		else {
			token = GameToken.SECOND_PLAYER;
		}
		
		try {
			board.insertToken(nextTurn.getDirection(), nextTurn.getLine(), token);
		} catch (InvalidTurnException e) {
			System.out.println(e.getMessage());
			// TODO Loggen
		}
	}
	
	private void nextPlayer() {
		currentPlayerID++;
		currentPlayerID %= 2;
	}
	
	private Player getCurrentPlayer() {
		return player[currentPlayerID];
	}
	
	private GameResult computeGameResult() {
		final int tokensToWin = 4;
		
		boolean player1HasWon = false, player2HasWon = false;
		GameToken previous;
		int number;
		
		// Check rows
		for(int row = 0; row < board.getRows(); row++) {
			number = 1;
			previous = null;
			
			for(int column = 0; column < board.getColumns(); column++) {
				if(!board.getTokenAt(row, column).equals(previous)) {
					number = 1;
					previous = board.getTokenAt(row, column);
				}
				else {
					if(board.getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board.getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
			}
		}
		
		// Check columns
		for(int column = 0; column < board.getColumns(); column++) {
			number = 1;
			previous = null;
			
			for(int row = 0; row < board.getRows(); row++) {
				if(!board.getTokenAt(row, column).equals(previous)) {
					number = 1;
					previous = board.getTokenAt(row, column);
				}
				else {
					if(board.getTokenAt(row, column).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board.getTokenAt(row, column).equals(GameToken.SECOND_PLAYER)) {
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
		System.out.println();
		for(int row = 0; row < board.getRows(); row++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(row + counter < board.getRows() && counter < board.getColumns()) {
				if(!board.getTokenAt(row + counter, counter).equals(previous)) {
					number = 1;
					previous = board.getTokenAt(row + counter, counter);
				}
				else {
					if(board.getTokenAt(row + counter, counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board.getTokenAt(row + counter, counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		for(int column = 0; column < board.getColumns(); column++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(column + counter < board.getColumns() && counter < board.getRows()) {
				if(!board.getTokenAt(counter, column + counter).equals(previous)) {
					number = 1;
					previous = board.getTokenAt(counter, column + counter);
				}
				else {
					if(board.getTokenAt(counter, column + counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board.getTokenAt(counter, column + counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		
		
		System.out.println();
		for(int row = 0; row < board.getRows(); row++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(row - counter >= 0 && counter < board.getColumns()) {
				if(!board.getTokenAt(row - counter, counter).equals(previous)) {
					number = 1;
					previous = board.getTokenAt(row - counter, counter);
				}
				else {
					if(board.getTokenAt(row - counter, counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board.getTokenAt(row - counter, counter).equals(GameToken.SECOND_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player2HasWon = true;
						}
					}
				}
				
				counter++;
			}
		}
		for(int column = 0; column < board.getColumns(); column++) {
			number = 1;
			previous = null;
			
			int counter = 0;
			while(column + counter < board.getColumns() && board.getRows() - 1 - counter >= 0) { // TODO -1?
				if(!board.getTokenAt(board.getRows() - 1 - counter, column + counter).equals(previous)) {
					number = 1;
					previous = board.getTokenAt(board.getRows() - 1 - counter, column + counter);
				}
				else {
					if(board.getTokenAt(board.getRows() - 1 - counter, column + counter).equals(GameToken.FIRST_PLAYER)) {
						number++;
						if(number >= tokensToWin) {
							player1HasWon = true;
						}
					}
					else if(board.getTokenAt(board.getRows() - 1 - counter, column + counter).equals(GameToken.SECOND_PLAYER)) {
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
		
		if(board.isFull()) {
			return GameResult.DRAW;
		}
		
		return GameResult.NONE;
	}
}
