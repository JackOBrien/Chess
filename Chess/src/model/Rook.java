package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A Rook in a game of chess. 
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 11, 2014
 *******************************************************************/
public class Rook extends ChessPiece {

	/** Tells if the piece has moved of not. */
	private boolean hasMoved;
	
	/** Tells the pieces initial position. */
	private int initialRow, initialCol;
	
	/** The player who owns this piece. */
	private Player plr;
	
	/****************************************************************
	 * Constructor for Rook.
	 * 
	 * @param player the Player who owns this piece
	 ***************************************************************/
	public Rook(final Player player) {
		super(player);
		
		plr = player;
		hasMoved = false;
		initialRow = -1;
		initialCol = -1;
	}

	@Override
	public final String type() {
		return "Rook";
	}

	@Override
	public final boolean isValidMove(final Move move, final IChessBoard board) {
		
		if (!super.isValidMove(move, board)) {
			return false;
		}
			
		int fR = move.fromRow, fC = move.fromColumn;
		int tR = move.toRow, tC = move.toColumn;		
		
		checkifMoved(fR, fC);
		
		/* Ensures that only the row OR the column is changing */
		if (fR != tR && fC != tC) { return false; }
		
		// Decides if the Rook is attempting to move vertically
		boolean vertical = fR != tR ? true : false;
		
		// Initializes variables as if the rook is moving horizontally
		int x = tR; // Row location to search for pieces
		int y = fC; // Column location to search for pieces
		int dif = tC - fC; // How far the piece moved
		
		/* Assigns appropriate variables depending on whether the 
		 * piece is moving vertically or horizontally  */
		if (vertical) {
			x = fR;
			y = tC;
			dif = tR - fR;
		}
		
		// Negative: Up/Left, Positive: Down/Right
		int direction = (dif > 0) ? 1 : -1;
		
		// Parameters for pieceAt() in the loop bellow
		int row = x, col = y;
		
		/* Scans the area between the to and from location looking for
		 * any pieces. If it finds a piece, the move is not valid */
		for (int i = 1; i < dif * direction; i++) {
			if (vertical) {
				row = x + i * direction;
			} else {
				col = y + i * direction;
			}
			
			// Checks if the rook "jumped" over another piece
			if (board.pieceAt(row, col) != null) {
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

	/****************************************************************
	 * Checks if the Rook has moved on the board before. 
	 * 
	 * @param fR the row location of the Rook.
	 * @param fC the column location of the Rook.
	 ***************************************************************/
	private void checkifMoved(final int fR, final int fC) {
		
		/* Once the piece has moved, hasMoved will always be true */
		if (!hasMoved) {
		
			/* Checks if the initial location is still -1, as set 
			 * by the constructor. */
			if (initialRow == -1 && initialCol == -1) {
				initialRow = fR;
				initialCol = fC;
				hasMoved = false;
				
			/* Checks if the initial location is the same as it was 
			 * when this piece last checked for a valid move*/	
			} else if (initialRow == fR && initialCol == fC) {
				hasMoved = false;
			} else {
				hasMoved = true;
			}
		}
	}
	
	/********************************************************
	 * Returns if the piece has moved or not.
	 * 
	 * @return  true if it moved, false otherwise.
	 *******************************************************/
	public final boolean hasMoved() {
		return hasMoved;
	}
}
