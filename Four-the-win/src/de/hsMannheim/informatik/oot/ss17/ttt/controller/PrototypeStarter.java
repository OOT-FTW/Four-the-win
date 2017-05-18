package de.hsMannheim.informatik.oot.ss17.ttt.controller;

import java.util.Scanner;
import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class PrototypeStarter {
	private static final int defaultWidth = 7, defaultHeight = 6;
	private static final int minWidth = 7, minHeight = 6;
	private static final int maxWidth = 50, maxHeight = 50;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private int width = defaultWidth, height = defaultHeight;
	
	public PrototypeStarter() {
		// Game started
		
		// Main menu
		System.out.println("FOUR THE WIN - Prototype");
		System.out.println();
		
		// Initiate field
		readFieldMeasurements();
		
		Game game = new Game(new LocalPlayer("Player1"), new LocalPlayer("Player2"), new GameBoard(new GameBoardSize(width, height)));
		game.play();
	}
	
	private void readFieldMeasurements() {
		System.out.println("Geben Sie die Spielfeldgröße im folgenden Format an: BreitexHöhe.");
		System.out.println("Breite und Höhe müssen ganze Zahlen sein.");
		System.out.println("Die Maximalgröße ist " + maxWidth + "x" + maxHeight + ", die Minimalgröße ist " + minWidth + "x" + minHeight + ".");
		
		boolean correctInput;
		
		do {
			correctInput = true;
			String value = scanner.nextLine();
			
			if(!value.matches("^[0-9]+x[0-9]+$")) {
				System.out.println("Die Eingabe hat ein ungültiges Format. Versuchen Sie es erneut.");
				correctInput = false;
				continue;
			}
			
			String[] parts = value.split("x");

			int newWidth = Integer.parseInt(parts[0]);
			int newHeight = Integer.parseInt(parts[1]);
			
			if(Math.min(newWidth, newHeight) < minHeight || Math.max(newWidth, newHeight) < minWidth || newWidth > maxWidth || newHeight > maxHeight) {
				System.out.println("Die Feldgröße muss zwischen " + minWidth + "x" + minHeight + " und " + maxWidth + "x" + maxHeight + " liegen.");
				correctInput = false;
				continue;
			}
			else {
				width = newWidth;
				height = newHeight;
			}
		}
		while(!correctInput);
	}
	
	
	
	
	
	public static void main(String[] args) {
		new PrototypeStarter();
	}
}
