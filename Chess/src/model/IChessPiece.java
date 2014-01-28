package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Describes a piece for a game of chess (King, Queen, Rook, etc.).
 *
 * @author Zachary Kurmas
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @version Jan 27, 2014
 *******************************************************************/
public interface IChessPiece {

	/****************************************************************
	 * Returns the player that owns this piece.
	 * 
	 * @return the player that owns this piece.
	 ***************************************************************/
	Player player();


	/****************************************************************
	 * Returns the name of this piece's type as a string.
	 * Example: "King"
	 * 
	 * @return the name of this piece's type.
	 ***************************************************************/
	String type();

	/****************************************************************
	 * Returns whether the piece at location [move.fromRow, move.fromColumn]
	 * is allowed to move to location [move.fromRow, move.fromColumn].
	 *
	 * @param move a Move object describing the move to be made.
	 * @param board the chess board in which this piece resides.
	 * @return true if the proposed move is valid, false otherwise.
	 * @throws IndexOutOfBoundsException if either [move.fromRow, 
	 * 			move.fromColumn] or [move.toRow,move.toColumn] don't 
	 * 			represent valid locations on the board.
	 * @throws IllegalArgumentException if this object isn't the piece at
	 *			location [move.fromRow, move.fromColumn].
	 ***************************************************************/
	boolean isValidMove(Move move, IChessBoard board);
}
