package de.hsMannheim.informatik.oot.ss17.ttt.test.controllerTest;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class KIGameBoardSizeTest {
	public static void main(String[] args) {
		System.out.println("FOUR THE WIN TEST");
		
		final int bigRows = 50, bigColumns = 50;
		final int midRows = 25, midColumns = 25;
		final int smallRows = 11, smallCloumns = 13;
		
		GameBoard bigGame = new GameBoard(new GameBoardSize(bigRows, bigColumns));
		GameBoard midGame = new GameBoard(new GameBoardSize(midRows, midColumns));
		GameBoard smallGame = new GameBoard(new GameBoardSize(smallRows, smallCloumns));
		
		//Small Game
		long time = System.currentTimeMillis();
		simulateThreeDifficultKiTurn(smallGame);
		time = System.currentTimeMillis()-time;
		System.out.println("FINISHED SMALL GAME in: " + ((int)(time/1000)) + " seconds");
		
		//Mid Game
		time = System.currentTimeMillis();
		simulateThreeDifficultKiTurn(midGame);
		time = System.currentTimeMillis()-time;
		System.out.println("FINISHED MID GAME in: " + ((int)(time/1000)) + " seconds");
		
		//Big Game
		time = System.currentTimeMillis();
		simulateThreeDifficultKiTurn(bigGame);
		time = System.currentTimeMillis()-time;
		System.out.println("FINISHED BIG GAME in: " + ((int)(time/1000)) + " seconds");
		
	}
	
	/**
	 * die Simulation stellt den minimalen aufwand dar, der für die KI mit Tiefensuche 3. Ordnung benötigt wird
	 * hierbei wird jeder Mögliche Spielzug der nächsten drei Runden, das heißt der nächsten 6 Züge betrachtet.
	 * In dieser simulation wird nur der minimale Aufwand betrieben, das heißt, die Züge werde betrachtet, 
	 * und auf dem Spielfeld wird jedes einzelne Feld einmal verglichen. Bei der richtigen KI wird das noch etwas mehr Aufwand.
	 * Jedoch sieht man hierbei schon, für welche Spielgrössen, diese einfache Simulation schon viel zu lange braucht.
	 * @param testGameBoard
	 */
	public static void simulateThreeDifficultKiTurn(GameBoard testGameBoard){
		simulateThreeDifficultKiTurn(testGameBoard, 0);
	}
	
	public static void simulateThreeDifficultKiTurn(GameBoard testGameBoard, int counter){
		//NORTH
		for(int spalte = 1; spalte <= testGameBoard.getColumns(); spalte++){
			if(testGameBoard.canInsert(CompassDirection.NORTH, spalte)){
				for(int zeile = 1; zeile <= testGameBoard.getRows(); zeile++){
					testGameBoard.getTokenAt(zeile-1, spalte-1);
					if(counter == 5){
						simulateBoardRating(testGameBoard);
					}
				}
			}
			if(counter < 6){
				simulateThreeDifficultKiTurn(testGameBoard, counter + 1);
			}	
		}	
		//EAST
		for(int zeile = 1; zeile <= testGameBoard.getRows(); zeile++){
			if(testGameBoard.canInsert(CompassDirection.EAST, zeile)){
				for(int spalte = 1; spalte <= testGameBoard.getColumns(); spalte++){
					testGameBoard.getTokenAt(zeile-1, spalte-1);
					if(counter == 5){
						simulateBoardRating(testGameBoard);
					}
				}
			}
			if(counter < 6){
				simulateThreeDifficultKiTurn(testGameBoard, counter + 1);
			}	
		}
		//South
				for(int spalte = 1; spalte <= testGameBoard.getColumns(); spalte++){
					if(testGameBoard.canInsert(CompassDirection.SOUTH, spalte)){
						for(int zeile = 1; zeile <= testGameBoard.getRows(); zeile++){
							testGameBoard.getTokenAt(zeile-1, spalte-1);
							if(counter == 5){
								simulateBoardRating(testGameBoard);
							}
						}
					}
					if(counter < 6){
						simulateThreeDifficultKiTurn(testGameBoard, counter + 1);
					}	
				}	
		//West
		for(int zeile = 1; zeile <= testGameBoard.getRows(); zeile++){
			if(testGameBoard.canInsert(CompassDirection.WEST, zeile)){
				for(int spalte = 1; spalte <= testGameBoard.getColumns(); spalte++){
					testGameBoard.getTokenAt(zeile-1, spalte-1);
					if(counter == 5){
						simulateBoardRating(testGameBoard);
					}
				}
			}
			if(counter < 6){
				simulateThreeDifficultKiTurn(testGameBoard, counter + 1);
			}	
		}
		
	}	

	public static void simulateBoardRating(GameBoard testGameBoard){
		for (int row = 0; row < testGameBoard.getRows(); row++){
			for(int column = 0; column < testGameBoard.getColumns(); column++){
				GameToken testToken = testGameBoard.getTokenAt(row, column);
				testToken.compareTo(testToken);
			}
		}
	}
}