/**
 * @autor Sebastian J. Vogt
 */

package de.hsMannheim.informatik.oot.ss17.ttt.test.modelTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsMannheim.informatik.oot.ss17.ttt.model.GameBoardSize;

public class GameBoardSizeTest {

	public static final int LOWESTCOLUMN = 6;
	public static final int LOWESTROW = 7;
	public static final int BIGGESTCOLUMN = 50;
	public static final int BIGGESTROW = 50;
	
	GameBoardSize minAllowed;
	GameBoardSize maxAllowed;	
	
	@Before
	public void setUp() throws IllegalArgumentException {
		minAllowed = new GameBoardSize(LOWESTROW,LOWESTCOLUMN);
		maxAllowed = new GameBoardSize(BIGGESTROW,BIGGESTCOLUMN);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGameBoardSize1() {
		GameBoardSize fiveSeven = new GameBoardSize(LOWESTROW -1,LOWESTCOLUMN);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGameBoardSize2() {
		GameBoardSize sixSix = new GameBoardSize(LOWESTROW,LOWESTCOLUMN -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGameBoardSize3() {
		GameBoardSize fiftyFiftyone = new GameBoardSize(BIGGESTROW,BIGGESTCOLUMN+1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGameBoardSize4() {
		GameBoardSize fiftyoneFifty = new GameBoardSize(BIGGESTROW +1,BIGGESTCOLUMN);
	}

	@Test
	public void testGetRows() {
		assertEquals(LOWESTROW, minAllowed.getRows());
		assertEquals(BIGGESTROW, maxAllowed.getRows());		
	}
	
	@Test
	public void testGetColumns() {
		assertEquals(LOWESTCOLUMN, minAllowed.getColumns());
		assertEquals(BIGGESTCOLUMN, maxAllowed.getColumns());
	}

}
