package de.hsMannheim.informatik.oot.ss17.ttt.controller;

import java.io.IOException;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class StarterSimple {
	public static void main(String[] args) {
		System.out.println("FOUR THE WIN TEST");
		
		final int rows = 50, columns = 50;
		startWithSize(rows, columns);	// Alternative 1
		
//		startWithFile("ftw.txt");	// Alternative 2
									// Beispiel Datei auf GitHub: ftw.txt
	}
	
	private static void startWithSize(int rows, int columns) {
		try {
			new Game(new LocalPlayer("Player1"), new LocalPlayer("Player2"), new GameBoard(new GameBoardSize(rows, columns))).play();
		}
		catch(IllegalArgumentException e) {
			System.out.println("Die Spielfeldgröße ist nicht gültig.");
			System.out.println(e.getMessage());
		}
	}
	
	private static void startWithFile(String path) {
		try {
			new Game(new LocalPlayer("Player1"), new LocalPlayer("Player2"), new GameBoard(path)).play();
		}
		catch (InvalidBoardException e) {
			System.out.println("Beim Einlesen der Datei ist ein Fehler aufgetreten.");
		}
	}
}
