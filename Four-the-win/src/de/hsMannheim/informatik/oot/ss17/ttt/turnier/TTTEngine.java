package de.hsMannheim.informatik.oot.ss17.ttt.turnier;

import de.hsMannheim.informatik.oot.ss17.ttt.model.AIPlayerHard;
import de.hsMannheim.informatik.oot.ss17.ttt.model.GameBoard;
import de.hsMannheim.informatik.oot.ss17.ttt.model.GameBoardSize;
import de.hsMannheim.informatik.oot.ss17.ttt.model.GameResult;
import de.hsMannheim.informatik.oot.ss17.ttt.model.GameToken;
import de.hsMannheim.informatik.oot.ss17.ttt.model.GameTurn;
import de.hsMannheim.informatik.oot.ss17.ttt.model.InvalidTurnException;
import de.hsMannheim.informatik.oot.ss17.ttt.model.Player;
import de.hsMannheim.informatik.oot.ss17.ttt.view.GameBoardPrinter;
import ftw.turnier.Engine;

public class TTTEngine implements Engine {

	private Player tttPlayer;
	
	private GameBoard gameBoard;
	private GameBoardSize gameSize;
	private GameBoardPrinter printer;
	
	private GameToken tokenEnemy = GameToken.SECOND_PLAYER;
	private GameToken tokenMe = GameToken.FIRST_PLAYER;
	
	private boolean isFirst;
	
	public TTTEngine() {
		gameSize = new GameBoardSize(10,10);
		tttPlayer = new AIPlayerHard(2, "TTT-KI");
	}
	
	@Override
	public boolean isRunning() {
		GameResult result = gameBoard.computeGameResult();
		return result == GameResult.NONE;
	}

	@Override
	public int getMatchResult() {
		GameResult result = gameBoard.computeGameResult();
		if(result == GameResult.DRAW) {
			return 0;
		} else if(result == GameResult.FIRST_PLAYER_WON) {
			if(isFirst)
				return -1;
			else 
				return 1;
		} else if(result == GameResult.SECOND_PLAYER_WON) {
			if(isFirst)
				return 1;
			else 
				return -1;
		}
		
		return 0;
	}

	@Override
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst; 
		
		if(isFirst) {
			tokenMe = GameToken.SECOND_PLAYER;
			tokenEnemy = GameToken.FIRST_PLAYER;
		} else {
			tokenEnemy = GameToken.SECOND_PLAYER;
			tokenMe = GameToken.FIRST_PLAYER;
		}
		
		//Reset engine
		gameBoard = new GameBoard(gameSize);
		printer = new GameBoardPrinter(gameBoard);
	}

	@Override
	public int getMove() {
		try {
			GameTurn nextGameTurn = tttPlayer.getNextTurn(gameBoard);
			gameBoard.insertToken(nextGameTurn, tokenMe);
			return nextGameTurn.getField(gameSize);
		} catch (InvalidTurnException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean sendMove(int field) {
		GameTurn gameTurn = new GameTurn(gameSize, field);
		
		if(gameBoard.canInsert(gameTurn)) {
			try {
				gameBoard.insertToken(gameTurn, tokenEnemy);
			} catch (InvalidTurnException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void printBoard() {
		printer.print();		
	}

}
