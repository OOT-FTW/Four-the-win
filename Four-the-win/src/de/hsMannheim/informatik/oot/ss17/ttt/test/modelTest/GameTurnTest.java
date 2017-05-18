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
		 turnNORTH = new GameTurn(CompassDirection.NORTH,1);
		 turnSOUTH = new GameTurn(CompassDirection.SOUTH,2);
		 turnEAST = new GameTurn(CompassDirection.EAST,3);
		 turnWEST = new GameTurn(CompassDirection.WEST,4);
		 
	}

	@Test
	public void testGetDirection() {
		assertEquals(CompassDirection.NORTH,turnNORTH.getDirection());
		assertEquals( CompassDirection.SOUTH, turnSOUTH.getDirection());
		assertEquals( CompassDirection.EAST, turnEAST.getDirection());
		assertEquals(CompassDirection.WEST, turnWEST.getDirection());
	}

	@Test
	public void testGetLine() {
		assertEquals(1, turnNORTH.getLine());
		assertEquals(2, turnSOUTH.getLine());
		assertEquals(3, turnEAST.getLine());
		assertEquals(4, turnWEST.getLine());
	}

}
