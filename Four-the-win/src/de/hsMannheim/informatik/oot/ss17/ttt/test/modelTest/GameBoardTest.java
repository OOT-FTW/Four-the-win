/**
 * @autor Sebastian J. Vogt
 */

package de.hsMannheim.informatik.oot.ss17.ttt.test.modelTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsMannheim.informatik.oot.ss17.ttt.model.*;

public class GameBoardTest {
	
	public static final int LOWESTCOLUMN = 7;
	public static final int LOWESTROW = 6;
	public static final int BIGGESTCOLUMN = 50;
	public static final int BIGGESTROW = 50;

	GameBoard small;
	GameBoard big;
	GameBoard load;
	
	@Before
	public void setUp() throws IllegalArgumentException, InvalidBoardException, InvalidTurnException {
		small = new GameBoard(new GameBoardSize(LOWESTROW,LOWESTCOLUMN));
		big = new GameBoard(new GameBoardSize(BIGGESTROW,BIGGESTCOLUMN));
		load = new GameBoard("ftw.txt");
		
		for(int row = 0; row < small.getRows(); row++){
			if(row%2 == 0){
				small.insertToken(CompassDirection.NORTH, 2, GameToken.FIRST_PLAYER);
			}
			else{
				small.insertToken(CompassDirection.NORTH, 2, GameToken.SECOND_PLAYER);
			}
		}
		
		for(int columns = 0; columns < big.getColumns(); columns++){
			if(columns %2 == 0){
				big.insertToken(CompassDirection.EAST, 3, GameToken.FIRST_PLAYER);
			}
			else{
				big.insertToken(CompassDirection.EAST, 3, GameToken.SECOND_PLAYER);
			}
		}
	}

	@Test(expected = InvalidBoardException.class)
	public void testGameBoardString() throws InvalidBoardException {
		GameBoard stringFail = new GameBoard("fail");
	}

	@Test
	public void testCanInsert() {
		assertTrue(small.canInsert(CompassDirection.NORTH, 1));
		assertTrue(small.canInsert(CompassDirection.EAST, 1));
		assertTrue(small.canInsert(CompassDirection.SOUTH, LOWESTCOLUMN));
		assertTrue(small.canInsert(CompassDirection.WEST, LOWESTROW));
		assertTrue(big.canInsert(CompassDirection.NORTH, 1));
		assertTrue(big.canInsert(CompassDirection.EAST, 1));
		assertTrue(big.canInsert(CompassDirection.SOUTH, BIGGESTCOLUMN));
		assertTrue(big.canInsert(CompassDirection.WEST, BIGGESTROW));
	}
	
	public void testCanInsertFalse1(){
		assertFalse(small.canInsert(CompassDirection.NORTH, 2));
	}

	public void testCanInsertFalse2(){
		assertFalse(small.canInsert(CompassDirection.SOUTH, 2));
	}
	
	public void testCanInsertFalse3(){
		assertFalse(big.canInsert(CompassDirection.EAST, 3));
	}

	public void testCanInsertFalse4(){
		assertFalse(big.canInsert(CompassDirection.WEST, 3));
	}
	
	

	@Test(expected = InvalidTurnException.class)
	public void testInsertToken1() throws InvalidTurnException{
		small.insertToken(CompassDirection.NORTH, 2, GameToken.FIRST_PLAYER);
	}
	
	@Test(expected = InvalidTurnException.class)
	public void testInsertToken2() throws InvalidTurnException{
		big.insertToken(CompassDirection.EAST, 3, GameToken.FIRST_PLAYER);
	}

	@Test
	public void testGetTokenAt() {
		assertEquals(GameToken.FIRST_PLAYER, small.getTokenAt(small.getRows() -1, 1));
		assertEquals(GameToken.SECOND_PLAYER, big.getTokenAt(2, BIGGESTCOLUMN -1));	
	}

	@Test
	public void testGetRows() {
		assertEquals(LOWESTROW, small.getRows());
		assertEquals(BIGGESTROW, big.getRows());
	}

	@Test
	public void testGetColumns() {
		assertEquals(LOWESTCOLUMN, small.getColumns());
		assertEquals(BIGGESTCOLUMN, big.getColumns());
	}

	@Test
	public void testGetCurrentPlayerNumber() throws InvalidTurnException {
		assertEquals(1, big.getCurrentPlayerNumber());
		assertEquals(1, small.getCurrentPlayerNumber());
		small.insertToken(CompassDirection.WEST, 3, GameToken.FIRST_PLAYER);
		assertEquals(2, small.getCurrentPlayerNumber());
	}

	@Test
	public void testContainsAnyNonNoneToken() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIsFull() {
		//fail("Not yet implemented");
	}

}
