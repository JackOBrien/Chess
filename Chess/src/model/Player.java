package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A player of a chess game. 
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @author Zachary Kurmas
 * @version Feb 24, 2014
 *******************************************************************/
public enum Player {

	/** The player who's pieces are black. */
	BLACK, 

	/** The player who's pieces are white. */
	WHITE;

	/****************************************************************
	 * Return the Player whose turn is next.
	 * 
	 * @return the Player whose turn is next.
	 ***************************************************************/
	public Player next() {
		return this == BLACK ? WHITE : BLACK;
	}
	
	/****************************************************************
	 * Tells if this player is white or not.
	 * 
	 * @return true if the player is white, false otherwise.
	 ***************************************************************/
	public boolean isWhite() {
		return this == WHITE;
	}
}