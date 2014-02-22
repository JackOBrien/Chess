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
	 * @param fR the fromRow to set.
	 ***************************************************************/
	public final void setFromRow(final int fR) {
		this.fromRow = fR;
	}

	/****************************************************************
	 * @return the fromColumn.
	 ***************************************************************/
	public final int getFromColumn() {
		return fromColumn;
	}

	/****************************************************************
	 * @param fC the fromColumn to set.
	 ***************************************************************/
	public final void setFromColumn(final int fC) {
		this.fromColumn = fC;
	}

	/****************************************************************
	 * @return the toRow.
	 ***************************************************************/
	public final int getToRow() {
		return toRow;
	}

	/****************************************************************
	 * @param tR the toRow to set.
	 ***************************************************************/
	public final void setToRow(final int tR) {
		this.toRow = tR;
	}

	/****************************************************************
	 * @return the toColumn.
	 ***************************************************************/
	public final int getToColumn() {
		return toColumn;
	}

	/****************************************************************
	 * @param tC the toColumn to set.
	 ***************************************************************/
	public final void setToColumn(final int tC) {
		this.toColumn = tC;
	}
}
