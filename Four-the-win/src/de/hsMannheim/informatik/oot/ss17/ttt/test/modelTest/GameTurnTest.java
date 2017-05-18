package de.hsMannheim.informatik.oot.ss17.ttt.test.modelTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsMannheim.informatik.oot.ss17.ttt.model.CompassDirection;
import de.hsMannheim.informatik.oot.ss17.ttt.model.GameTurn;

public class GameTurnTest {

	
	GameTurn turnNORTH;
	GameTurn turnSOUTH;
	GameTurn turnEAST;
	GameTurn turnWEST;
	
	
	@Before
	public void setUp() throws Exception {
		 turnNORTH = new GameTurn(CompassDirection.NORTH, 1);
		 turnSOUTH = new GameTurn(CompassDirection.SOUTH, 2);
		 turnEAST = new GameTurn(CompassDirection.EAST, 3);
		 turnWEST = new GameTurn(CompassDirection.WEST, 4);
		 
	}

	@Test
	public void testGetDirection() {
		assertEquals(turnNORTH.getDirection(), CompassDirection.NORTH);
		assertEquals(turnSOUTH.getDirection(), CompassDirection.SOUTH);
		assertEquals(turnEAST.getDirection(), CompassDirection.EAST);
		assertEquals(turnWEST.getDirection(), CompassDirection.WEST);
	}

	@Test
	public void testGetLine() {
		assertEquals(turnNORTH.getLine(), 1);
		assertEquals(turnSOUTH.getLine(), 2);
		assertEquals(turnEAST.getLine(), 3);
		assertEquals(turnWEST.getLine(), 4);
	}

}
