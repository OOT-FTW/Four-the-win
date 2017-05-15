package de.hsMannheim.informatik.oot.ss17.ttt.test;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class TestMain {
	public static void main(String[] args) {
		System.out.println("FOUR THE WIN TEST 6x7");
		new Game(new LocalPlayer("Player1"), new LocalPlayer("Player2"), new GameBoard(new GameBoardSize(6, 7))).play();;
	}
}
