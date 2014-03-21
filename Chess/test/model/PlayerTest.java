package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Tests the player to help out EclEmma
 *
 * @author John O'Brien
 * @version Feb 19, 2014
 *******************************************************************/
public class PlayerTest {

	@Test
	public final void testEnum() {
		Player.valueOf(Player.WHITE.toString());
	}

	@Test
	public final void testBoolean() throws Throwable {
		assertTrue(Player.WHITE.isWhite());
		assertFalse(Player.BLACK.isWhite());
	}
}
