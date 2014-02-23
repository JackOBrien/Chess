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
 * @version Feb 24, 2014
 *******************************************************************/
public class Move {

	/** The row coordinate of the pieces current location. */
	private int fromRow;

	/** The column coordinate of the pieces current location. */
	private int fromColumn;

	/** The row coordinate of the pieces destination. */
	private int toRow;

	/** The column coordinate of the pieces destination. */
	private int toColumn;

	/****************************************************************
	 * Constructor for the Move object.
	 * 
	 * @param fR current row location.
	 * @param fC current column location.
	 * @param tR destination row location.
	 * @param tC destination column location.
	 ***************************************************************/
	public Move(final int fR, final int fC, final int tR, final int tC) {
		fromRow = fR;
		fromColumn = fC;
		toRow = tR;
		toColumn = tC;
	}

	/****************************************************************
	 * @return the fromRow.
	 ***************************************************************/
	public final int getFromRow() {
		return fromRow;
	}

	/****************************************************************
	 * @return the fromColumn.
	 ***************************************************************/
	public final int getFromColumn() {
		return fromColumn;
	}

	/****************************************************************
	 * @return the toRow.
	 ***************************************************************/
	public final int getToRow() {
		return toRow;
	}

	/****************************************************************
	 * @return the toColumn.
	 ***************************************************************/
	public final int getToColumn() {
		return toColumn;
	}
}
