package de.hsMannheim.informatik.oot.ss17.ttt.model;

import de.hsMannheim.informatik.oot.ss17.ttt.view.GameBoardPrinter;

public class Game {
	
	private Player[] player;
	private int currentPlayerID;
	private GameBoardPrinter printer;
	private GameBoard board;
	
	public Game(Player firstPlayer, Player secondPlayer, GameBoard board) {
		this.player = new Player[2];
		this.player[0] = firstPlayer;
		this.player[1] = secondPlayer;
		
		this.board = board;
		
		this.printer = new GameBoardPrinter(board);
	}
	
	public void play() {
		
		boolean gameEnded = false;
		do {
			printer.print();
			
			executeNextTurn();
			
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
	
	private void executeNextTurn() {
		GameTurn nextTurn = getCurrentPlayer().getNextTurn(board);
		
		GameToken token;
		if(currentPlayerID == 1) {
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
		// TODO computeGameResult()
		return GameResult.NONE;
	}
}
