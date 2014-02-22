package model;


/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A Knight in a game of Chess. Moves in an "L" shape.
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 20, 2014
 *******************************************************************/
public class Knight extends ChessPiece {


	/****************************************************************
	 * Constructor for night.
	 * 
	 * @param plr owner of the Knight
	 ***************************************************************/
	public Knight(final Player plr) {
		super(plr);
	}

	@Override
	public final String type() {
		return "Knight";
	}

	@Override
	public final boolean isValidMove(final Move move, final IChessBoard board) {
		
		if (!super.isValidMove(move, board)) {
			return false;
		}
		
		// Relative valid positions
		final int[] validRow = {1, 2, 2, 1, -1, -2, -2, -1};
		final int[] validCol = {-2, -1, 1, 2, 2, 1, -1, -2};
			
		int fR = move.getFromRow(), fC = move.getFromColumn();
		int tR = move.getToRow(), tC = move.getToColumn();
		
		/* Loops through array of relative valid positions 
		 * and returns true if it finds a match*/
		for (int i = 0; i < validRow.length; i++) {
			if (tR == fR + validRow[i] && tC == fC + validCol[i]) {
				return true;
			}
		}

		return false;
	}

}
