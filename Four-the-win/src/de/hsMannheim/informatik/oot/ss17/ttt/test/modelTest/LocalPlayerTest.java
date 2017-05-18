package de.hsMannheim.informatik.oot.ss17.ttt.test.modelTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsMannheim.informatik.oot.ss17.ttt.model.GameBoard;
import de.hsMannheim.informatik.oot.ss17.ttt.model.LocalPlayer;

public class LocalPlayerTest {

	LocalPlayer johannes;
	LocalPlayer pascal;
	LocalPlayer hohannes;
	
	GameBoard testBoard;
	
	
	@Before
	public void setUp() throws Exception {
		LocalPlayer johannes = new LocalPlayer("Johannes");
		LocalPlayer pascal = new LocalPlayer("Pascal");
		LocalPlayer hohannes = new LocalPlayer("Hohannes");
		
		GameBoard testBoard = new GameBoard("ftw.txt");
	}


	@Test
	public void testGetName() {
		assertEquals("Johannes", johannes.getName());
		assertEquals("Pascal", pascal.getName());
		assertEquals("Hohannes", hohannes.getName());
	}

	@Test(expected = Exception.class)
	public void testGetNextTurn() {
		
	}

}
