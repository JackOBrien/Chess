package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Packages the four components of a move into a single object.
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @author Zachary Kurmas
 * @version Feb 20, 2014
 *******************************************************************/
public class Move {

	/** The row coordinate of the pieces current location. */
	public int fromRow;

	/** The column coordinate of the pieces current location. */
	public int fromColumn;

	/** The row coordinate of the pieces destination. */
	public int toRow;

	/** The column coordinate of the pieces destination. */
	public int toColumn;

	/****************************************************************
	 * Constructor for the Move object.
	 * 
	 * @param fromRow current row location.
	 * @param fromColumn current column location.
	 * @param toRow destination row location.
	 * @param toColumn destination column location.
	 ***************************************************************/
	public Move(final int fromRow, final int fromColumn, final int toRow,
			final int toColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}
}
