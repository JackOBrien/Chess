package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A Bishop in a game of chess.
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 20, 2014
 *******************************************************************/
public class Bishop extends ChessPiece {

	/****************************************************************
	 * Constructor for Bishop.
	 * 
	 * @param player the Player who owns this piece.
	 ***************************************************************/
	public Bishop(final Player player) {
		super(player);
	}

	@Override
	public final String type() {
		return "Bishop";
	}

	@Override
	public final boolean isValidMove(final Move move, final IChessBoard board) {
		
		if (!super.isValidMove(move, board)) {
			return false;
		}
		
		/* Ensures the piece is moving perpendicular and isn't jumping 
		 * over any pieces */
		if (!movingDiagonally(move, board)) { return false; }
		
		return true;
	}
	
	/****************************************************************
	 * Tells if the piece is moving diagonally.
	 * 
	 * @param m the proposed move.
	 * @param board the board the move is being attempted on.
	 * @return true if the piece is moving diagonally, false otherwise.
	 ***************************************************************/
	protected static boolean movingDiagonally(final Move m,
			final IChessBoard board) {
		int fR = m.fromRow, fC = m.fromColumn;
		
		// The difference in the to row and the from row
		int rowDiff = m.toRow - fR;

		// The difference in the to column and the from column
		int colDiff = m.toColumn - fC;

		// Negative: Up, Positive: Down
		int rowDir = (rowDiff > 0) ? 1 : -1;

		// Negative: Left, Positive: Right
		int colDir = (colDiff > 0) ? 1 : -1;

		/* Ensures the Bishop is moving diagonally */
		if (rowDiff * rowDir != colDiff * colDir) { return false; }

		/* Loops along the path of the piece to see if it "jumped"
		 * over a piece. If it did, the move is invalid */
		for (int i = 1; i < rowDiff * rowDir; i++) {

			if (board.pieceAt(fR + i * rowDir, fC + i * colDir) != null) {
				return false;
			}
		}
		
		return true;
	}
}
