package de.hsMannheim.informatik.oot.ss17.ttt.controller;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class StarterSimple {
	public static void main(String[] args) {
		System.out.println("FOUR THE WIN TEST 8x8");
		new Game(new LocalPlayer("Player1"), new LocalPlayer("Player2"), new GameBoard(new GameBoardSize(8, 8))).play();;
	}
}
