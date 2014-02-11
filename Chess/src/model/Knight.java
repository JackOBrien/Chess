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
 * @version Feb 10, 2014
 *******************************************************************/
public class Knight extends ChessPiece {


	/****************************************************************
	 * Constructor for night
	 * 
	 * @param plr owner of the Knight
	 ***************************************************************/
	protected Knight(Player plr) {
		super(plr);
	}

	@Override
	public String type() {
		return "Knight";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		
		if (!super.isValidMove(move, board))
			return false;
		
		// Relative valid positions
		int[] validRow = {1, 2, 2, 1, -1, -2, -2, -1};
		int[] ValidCol = {-2, -1, 1, 2, 2, 1, -1, -2};
			
		int fR = move.fromRow, fC = move.fromColumn;
		int tR = move.toRow, tC = move.toColumn;
		
		/* Loops through array of relative valid positions 
		 * and returns true if it finds a match*/
		for (int i = 0; i < validRow.length; i++) {
			if (tR == fR + validRow[i] && tC == fC + ValidCol[i]) {
				return true;
			}
		}

		return false;
	}

}
