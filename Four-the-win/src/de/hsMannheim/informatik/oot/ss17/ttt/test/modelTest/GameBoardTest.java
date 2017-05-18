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
	public void setUp() throws IllegalArgumentException, InvalidBoardException {
		small = new GameBoard(new GameBoardSize(LOWESTROW,LOWESTCOLUMN));
		big = new GameBoard(new GameBoardSize(BIGGESTROW,BIGGESTCOLUMN));
		load = new GameBoard("ftw.txt");
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

	@Test
	public void testInsertToken() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTokenAt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRows() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColumns() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentPlayerNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsAnyNonNoneToken() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFull() {
		fail("Not yet implemented");
	}

}
