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
 * @version Feb 11, 2014
 *******************************************************************/
public class Bishop extends ChessPiece {

	private Player plr;
	
	/****************************************************************
	 * Constructor for Bishop.
	 * 
	 * @param player the Player who owns this piece.
	 ***************************************************************/
	protected Bishop(Player player) {
		super(player);
		
		plr = player;
	}

	@Override
	public String type() {
		return "Bishop";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		
		if (!super.isValidMove(move, board)) {
			return false;
		}
		
		int fR = move.fromRow, fC = move.fromColumn;
		int tR = move.toRow, tC = move.toColumn;	
		
		// The difference in the to row and the from row
		int rowDiff = tR - fR;
		
		// The difference in the to column and the from column
		int colDiff = tC - fC;
		
		// Negative: Up, Positive: Down
		int rowDir = (rowDiff > 0) ? 1 : -1;
		
		// Negative: Left, Positive: Right
		int colDir = (colDiff > 0) ? 1 : -1;
		
		/* Ensures the Bishop is moving diagonally */
		if (rowDiff * rowDir != colDiff * colDir) { return false; }
		
		for (int i = 1; i < rowDiff * rowDir; i++) {
			
			if (board.pieceAt(fR + i * rowDir, fC + i * colDir) != null) {
				return false;
			}
		}

		IChessPiece piece = board.pieceAt(tR, tC);
		
		/* If the Rook is attempting to move onto another piece 
		 * and if it is, checks if that piece is friendly*/
		if (piece != null && piece.player() == plr) {
			return false;
		}
		
		return true;
	}
}
