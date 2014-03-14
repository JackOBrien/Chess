package model;


/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A Queen of a chess game. 
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @author Zachary Kurmas
 * @version Feb 24, 2014
 *******************************************************************/
public class Queen extends ChessPiece {
	
	/****************************************************************
	 * Constructor for Queen.
	 * 
	 * @param color owner of the Queen
	 ***************************************************************/
	public Queen(final Player color) {
		super(color);
	}

	@Override
	public final String type() {
		return "Queen";
	}

	@Override
	public final boolean isValidMove(final Move move, final IChessBoard board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
			
		/* Checks if the Queen is either moving like a Rook, or if
		 * it is moving like a Bishop.  */
		if (Rook.movingPerpendicular(move, board) 
				|| Bishop.movingDiagonally(move, board)) {
			return true;
		}
		
		return false;
	}

}
