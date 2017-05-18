package de.hsMannheim.informatik.oot.ss17.ttt.test.modelTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class LocalPlayerTest {

	LocalPlayer johannes;
	LocalPlayer pascal;
	LocalPlayer hohannes;
	
	GameBoard board;
	
	
	@Before
	public void setUp() throws Exception {
		 johannes = new LocalPlayer("Johannes");
		 pascal = new LocalPlayer("Pascal");
		 hohannes = new LocalPlayer("Hohannes");
		
		 board = new GameBoard("gameBoardTest.txt");
	}
	

	@Test
	public void testGetName() {
		assertEquals("Johannes", johannes.getName());
		assertEquals("Pascal", pascal.getName());
		assertEquals("Hohannes", hohannes.getName());
	}


	
	@Test(expected = InvalidTurnException.class)
	public void testCheckInputAndCreateGameTurnFalse() throws InvalidTurnException {
		johannes.checkInputAndCreateGameTurn(board, "1");
		johannes.checkInputAndCreateGameTurn(board, "1");
	}

	
	

	
	
	@Test(expected = InvalidTurnException.class)
	public void testCreateGameTurn() throws InvalidTurnException {
		johannes.checkInputAndCreateGameTurn(board, "1");
	}
	

}
