package de.hsMannheim.informatik.oot.ss17.ttt.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTurnTest {

	@Test
	public void createFromBoardSizeAndField() {
		GameBoardSize size = new GameBoardSize(10, 10);
		
		GameTurn northAtFirstLine = new GameTurn(size, 1);
		assertEquals(CompassDirection.NORTH, northAtFirstLine.getDirection());
		assertEquals(1, northAtFirstLine.getLine());
		
		GameTurn eastAtFirstLine = new GameTurn(size, 11);
		assertEquals(CompassDirection.EAST, eastAtFirstLine.getDirection());
		assertEquals(1, eastAtFirstLine.getLine());
		
		GameTurn southAtFirstLine = new GameTurn(size, 21);
		assertEquals(CompassDirection.SOUTH, southAtFirstLine.getDirection());
		assertEquals(1, southAtFirstLine.getLine());
		
		GameTurn westAtFirstLine = new GameTurn(size, 31);
		assertEquals(CompassDirection.WEST, westAtFirstLine.getDirection());
		assertEquals(1, westAtFirstLine.getLine());
		
		GameTurn northAtLastLine = new GameTurn(size, 10);
		assertEquals(CompassDirection.NORTH, northAtLastLine.getDirection());
		assertEquals(10, northAtLastLine.getLine());
		
		GameTurn eastAtLastLine = new GameTurn(size, 20);
		assertEquals(CompassDirection.EAST, eastAtLastLine.getDirection());
		assertEquals(10, eastAtLastLine.getLine());
		
		GameTurn southAtLastLine = new GameTurn(size, 30);
		assertEquals(CompassDirection.SOUTH, southAtLastLine.getDirection());
		assertEquals(10, southAtLastLine.getLine());
		
		GameTurn westAtLastLine = new GameTurn(size, 40);
		assertEquals(CompassDirection.WEST, westAtLastLine.getDirection());
		assertEquals(10, westAtLastLine.getLine());
	}
	
	@Test
	public void convertToField() {
GameBoardSize size = new GameBoardSize(10, 10);
		
		GameTurn northAtFirstLine = new GameTurn(size, 1);
		assertEquals(1, northAtFirstLine.getField(size));
		
		GameTurn eastAtFirstLine = new GameTurn(size, 11);
		assertEquals(11, eastAtFirstLine.getField(size));
		
		GameTurn southAtFirstLine = new GameTurn(size, 21);
		assertEquals(21, southAtFirstLine.getField(size));
		
		GameTurn westAtFirstLine = new GameTurn(size, 31);
		assertEquals(31, westAtFirstLine.getField(size));
		
		GameTurn northAtLastLine = new GameTurn(size, 10);
		assertEquals(10, northAtLastLine.getField(size));
		
		GameTurn eastAtLastLine = new GameTurn(size, 20);
		assertEquals(20, eastAtLastLine.getField(size));
		
		GameTurn southAtLastLine = new GameTurn(size, 30);
		assertEquals(30, southAtLastLine.getField(size));
		
		GameTurn westAtLastLine = new GameTurn(size, 40);
		assertEquals(40, westAtLastLine.getField(size));
	}

}
