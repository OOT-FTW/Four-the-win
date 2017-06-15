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
		this.currentPlayerID = board.getStartPlayerNumber() - 1;
		
		this.printer = new GameBoardPrinter(board);
	}
	
	public void play() {
		
		boolean gameEnded = false;
		do {
			printer.print();
			
			boolean validInput = false;
			System.out.println("Spieler \"" + getCurrentPlayer().getName() + "\" ist am Zug.");
			if(getCurrentPlayer() instanceof AIPlayerHard ||
				getCurrentPlayer() instanceof AIPlayerNormal ||
				getCurrentPlayer() instanceof AIPlayerEasy) {
				System.out.println(" Bitte auf den Zug des Computers warten.");
			}
			else {
				System.out.println(" Geben Sie dazu die Nummer des Feldes an, in das eingeworfen werden soll.");
			}
			
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
		
		executeNextTurn(nextTurn);
	}
	
	public void executeNextTurn(GameTurn nextTurn) throws InvalidTurnException {
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
		return board.computeGameResult();
	}
	
}
