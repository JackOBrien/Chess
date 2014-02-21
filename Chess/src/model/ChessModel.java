package model;

/********************************************************************
 * CIS 350 - 01.
 * Chess
 *
 * ChessModel to handle game logic
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 20, 2014
 *******************************************************************/
public class ChessModel implements IChessModel {

	/** The chess board with the game pieces. */
	private IChessBoard board;

	/** Stand chess board is 8 by 8. */
	private final int size = 8;

	/** Tells which player is in check. */
	private Player playerInCheck;

	/****************************************************************
	 * Constructor sets up both the 'real' and test game board.
	 ***************************************************************/
	public ChessModel() {
		board = new ChessBoard(size, true);
	}

	@Override
	public final boolean isComplete() {
		
		if (!inCheck()) {
			return false;
		}

		// Relative coordinates of all points around a piece
		int[] rowList = { 0,  1,  1, 1, 0, -1, -1, -1};
		int[] colList = {-1, -1,  0, 1, 1,  1,  0, -1};

		for (Player p : Player.values()) {

			// King location
			int kingRow = board.findKing(p)[0];
			int kingCol = board.findKing(p)[1];

			for (int i = 0; i < rowList.length; i++) {

				int toRow = kingRow + rowList[i];
				int toCol = kingCol + colList[i]; 
				
				// Creates a move from the King to 1 space away
				// in any direction
				Move m = new Move(kingRow, kingCol, 
						toRow, toCol);

				if (pieceAt(kingRow, kingCol).isValidMove(m, board)) {
					IChessBoard current = saveBoard();
					move(m);

					/* Checks if the king was able to move out of check */
					if (!inCheck()) {
						revertBoard(current);
						return false;
					}
					revertBoard(current);
				}
			}
		}
		
		// If the check can be blocked by a friendly piece
		// then the game is not over 
		return !canBlock(playerInCheck);
	}

	/****************************************************************
	 * Helper method for isComplete() to check if the given King
	 * can be protected by a friendly piece by moving to intercept
	 * the check.
	 * 
	 * @param plr Player who's King is being protected
	 * @return true if the King can be protected, false otherwise
	 ***************************************************************/
	private boolean canBlock(final Player plr) {
		
		/* Loops through board checking if each piece is
		 * capable of blocking the check */
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				IChessPiece piece = pieceAt(r, c);

				if (piece == null) { continue; }
				
				/* If the piece found belongs to the other player */
				if (piece.player() != plr) {
					continue;
				}

				/* Checks if the piece can stop the check */
				if (canStopCheck(r, c, plr)) { return true; }
			}
		}
		return false;
	}

	/****************************************************************
	 * Helper method to check if a particular piece is able
	 * to move anywhere that can take its team out of check.
	 * 
	 * @param r row location of the piece
	 * @param c column location of the piece
	 * @param plr Player that the piece belongs to
	 * @return true if the piece can stop the check, false otherwise
	 ***************************************************************/
	private boolean canStopCheck(final int r, final int c, final Player plr) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				if (x == r && y == c) {
					continue;
				}

				Move m = new Move(r, c, x, y);
				
				if (isValidMove(m)) {
					IChessBoard current = saveBoard();
					move(m);
					
					if (!inCheck()) {
						revertBoard(current);
						return true;
					} else {
						revertBoard(current);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public final boolean isValidMove(final Move move) {
		IChessPiece piece = pieceAt(move.fromRow, move.fromColumn);
		
		if (piece == null) { return false; }
		
		/* Checks if the piece thinks the move is legal */
		if (!basicallyLegal(piece, move)) { return false; }
		
		/* Kings cant castle in check */
		if (tryingToCastelInCheck(move)) { return false; }
		
		Player plr = piece.player();

		boolean valid = false;

		// Saves the current state of the board
		IChessBoard current = saveBoard();

		// Performs the move, regardless of the validity
		move(move);

		/* If the game is not put into check, the move is valid */
		if (!inCheck()) {
			valid =  true;

		/* Kings can't put each other in check */
		} else if (piece.is("King")) { 
			valid = !scanAround(board.findKing(plr.next()), move);
			
			/* Kings can't move and remain in check */
			if (plr == playerInCheck) { valid = false; }
			
		/* Checks to be sure that the current player 
		 * is not in check, but that their enemy is. */
		} else if (plr != playerInCheck) {
			valid =  true;
		}

		// The board is reset
		revertBoard(current);
		return valid;
	}

	/****************************************************************
	 * Helper method to tell if a move that's being attempted is
	 * "basically legal", meaning that the piece itself thinks the 
	 * move is valid, and the piece belongs to the current player.
	 * 
	 * @param piece the piece attempting the move.
	 * @param m the move being attempted.
	 * @return true if the move is "basically legal", false otherwise.
	 ***************************************************************/
	private boolean basicallyLegal(final IChessPiece piece, final Move m) {		
		Player plr = piece.player();

		
		if (plr.equals(currentPlayer()) && piece.isValidMove(m, board)) {
			return true;
		}
		
		return false;
	}
	
	/****************************************************************
	 * Scans around a piece and tells if the to location of the given 
	 * moves attempts to move into it's immediate vicinity.
	 * 
	 * @param location the coordinates of the piece to scan around.
	 * @param m the move being attempted.
	 * @return true if move attempts to move into the piece being 
	 * scanned's vicinity, false otherwise.
	 ***************************************************************/
	private boolean scanAround(final int[] location, final Move m) {
		// Location of the enemy king
		int rowE = location[0];
		int colE = location[1];
		
		// Location of the current king
		int tR = m.toRow, tC = m.toColumn;
		
		// Relative coordinates of all points around a piece
		int[] rowList = { 0,  1,  1, 1, 0, -1, -1, -1};
		int[] colList = {-1, -1,  0, 1, 1,  1,  0, -1};
		
		for (int i = 0; i < rowList.length; i++) {
			int row = rowE + rowList[i];
			int col = colE + colList[i];
			
			if (tR == row && tC == col) { return true; }

		}
		
		return false;
	}

	/****************************************************************
	 * Tells if a king tries to castle while in check.
	 * 
	 * @param m the proposed move.
	 * @return true if a king tries to castle while in check.
	 ***************************************************************/
	private boolean tryingToCastelInCheck(final Move m) {
		IChessPiece piece = pieceAt(m.fromRow, m.fromColumn);
		
		/* Only Kings can Castle */
		/* If the game isn't in check, the king is ok to castle */
		if (!piece.is("King") || !inCheck()) { return false; }
		
		/* If this king isn't in check then it's free to go! */
		if (piece.player() != playerInCheck) { return false; }
		
		int distance = Math.abs(m.toColumn - m.fromColumn);
		
		/* Ensures the king is in the proper position and is
		 * trying to castle. If so, the move is invalid */
		if (distance == 2) { return true; }
		
		return false;
	}
	
	@Override
	public final void move(final Move move) {
		board.move(move);
	}

	@Override
	public final boolean inCheck() {

		Player p = Player.WHITE;

		// Location of the white king
		int whiteR = board.findKing(p)[0];
		int whiteC = board.findKing(p)[1];

		p = p.next();

		// Location of the black king
		int blackR = board.findKing(p)[0];
		int blackC = board.findKing(p)[1];

		/* Loops through the entire board, and each time
		 * it finds a piece, it tries to move that piece
		 * to it's enemies King. The game is in check if
		 * any piece succeeds */
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				IChessPiece piece = pieceAt(r, c);

				if (piece == null) {
					continue;
				}

				Move m;

				if (piece.player() == Player.WHITE) {
					m = new Move(r, c, blackR, blackC);
					p = Player.BLACK;
				} else {
					m = new Move(r, c, whiteR, whiteC);
					p = Player.WHITE;
				}
				
				if (piece.isValidMove(m, board)) {
					playerInCheck = p;
					return true;
				}
			}
		}

		playerInCheck = null;
		return false;
	}

	@Override
	public final Player currentPlayer() {
		return board.getCurrentPlayer();
	}

	@Override
	public final IChessPiece pieceAt(final int row, final int column) {
		return board.pieceAt(row, column);
	}

	/****************************************************************
	 * Returns a deep copy of the current game board to be saved
	 * so that the game can later be reverted.
	 * 
	 * @return a deep copy of the current game board.
	 ***************************************************************/
	private IChessBoard saveBoard() {
		return new ChessBoard((ChessBoard) board);
	}
	
	/****************************************************************
	 * Reverts the current game board to the version that is passed
	 * as a parameter.
	 * 
	 * @param b older version of the chess board to be reverted to.
	 ***************************************************************/
	private void revertBoard(final IChessBoard b) {
		board = new ChessBoard((ChessBoard) b);
	}
	
	@Override
	public final IChessBoard getBoard() {
		return board;
	}
}