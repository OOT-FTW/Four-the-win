package de.hsMannheim.informatik.oot.ss17.ttt.controller;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class StarterSimple {
	public static void main(String[] args) {
		System.out.println("FOUR THE WIN TEST");
		
		final int rows = 10, columns = 10;
		try {
			startEasyAI(rows, columns);
			
			// Alternative Startmoeglichkeiten
//			startWithFile("ftw.txt");				// Beispiel Datei auf GitHub: ftw.txt
//			startPvP(rows, columns);
//			startEasyAI(rows, columns);
//			startHardAI(rows, columns);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Die Spielfeldgröße ist nicht gültig.");
			System.out.println(e.getMessage());
		}
	}
	
	private static void startPvP(int rows, int columns) {
		new Game(new LocalPlayer("Player1", 1), new LocalPlayer("Player2", 2), new GameBoard(new GameBoardSize(rows, columns))).play();
	}
	
	private static void startHardAI(int rows, int columns) {
		new Game(new LocalPlayer("Player1", 1), new AIPlayerHard(2, "KI"), new GameBoard(new GameBoardSize(rows, columns))).play();
	}
	
	private static void startEasyAI(int rows, int columns) {
		new Game(new LocalPlayer("Player1", 1), new AIPlayerEasy("KI"), new GameBoard(new GameBoardSize(rows, columns))).play();
	}
	
	private static void startWithFile(String path) {
		try {
			new Game(new LocalPlayer("Player1", 1), new LocalPlayer("Player2", 2), new GameBoard(path)).play();
		}
		catch (InvalidBoardException e) {
			System.out.println("Beim Einlesen der Datei ist ein Fehler aufgetreten.");
		}
	}
}
