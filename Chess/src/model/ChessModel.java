package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * ChessModel to handle game logic
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @version Feb 9, 2014
 *******************************************************************/
public class ChessModel implements IChessModel {

	/** The 'real' board with the game pieces */
	private IChessBoard board;
	
	/** A duplicate of board, used to save the state of the
	 * 'real' game board so the state of the game can be reverted*/
	private IChessBoard savedBoard;
	
	/** Stand chess board is 8 by 8 */
	private final int SIZE = 8;

	/** Tells which player is in check */
	private Player playerInCheck;
	
	/****************************************************************
	 * Constructor sets up both the 'real' and test game board
	 ***************************************************************/
	public ChessModel() {
		board = new ChessBoard(SIZE, true);
		saveBoard();
	}
	
	@Override
	public boolean isComplete() {
		
		if (!inCheck()) return false;
		
		// Relative coordinates of all points around a piece
		int[] rowList = { 0,  1,  1, 1, 0, -1, -1, -1};
		int[] colList = {-1, -1,  0, 1, 1,  1,  0, -1};
		
		for (Player p : Player.values()) {
			
			// King location
			int kingRow = board.findKing(p)[0];
			int kingCol = board.findKing(p)[1];
			
			for (int i = 0; i < rowList.length; i++) {
				
				// Creates a move from the King to 1 space away
				// in any direction
				Move m = new Move(kingRow, kingCol, rowList[i], colList[i]);
				
				if (isValidMove(m)) {
					saveBoard();
					move(m);
					
					/* Checks if the king was able to move out of check */
					if (!inCheck()) {
						revertBoard();
						return false;
					}
				}
			}
			
			/* If the check can be blocked by a friendly piece
			 * then the game is not over */
			if (canBlock(p)) {
				return false;
			}
		}

		return true;
	}
	
	/****************************************************************
	 * Helper method for isComplete() to check if the given King
	 * can be protected by a friendly piece by moving to intercept
	 * the check
	 * 
	 * @param plr Player who's King is being protected
	 * @return true if the King can be protected, false otherwise
	 ***************************************************************/
	private boolean canBlock(Player plr) {
		
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				for (int x = 0; x < SIZE; x++) {
					for (int y = 0; y < SIZE; y++) {
						IChessPiece piece = pieceAt(r, c);
						
						if (piece.player() != plr) continue;
						if (x == r && y == c) continue;
						
						Move m = new Move(r, c, x, y);
						
						if (isValidMove(m)) {
							saveBoard();
							move(m);
							
							if (!inCheck()) {
								revertBoard();
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean isValidMove(Move move) {
		IChessPiece piece = pieceAt(move.fromRow, move.fromColumn);
		Player plr = piece.player();
		
		boolean valid = false;
		
		// Saves the current state of the board
		saveBoard();
		
		// Performs the move, regardless of the validity
		move(move);
		
		if (basicallyLegal(piece, move)) {
			
			/* If the game is not put into check, the move is valid */
			if (!inCheck()) {
				valid =  true;
			}

			/* Checks to be sure that the current player 
			 * is not in check, but that their enemy is. */
			else if (plr != playerInCheck) {
				valid =  true;
			}
		}
		
		// Move is not valid, so the board is reset
		revertBoard();
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
	private boolean basicallyLegal(IChessPiece piece, Move m) {
		Player plr = piece.player();
		
		if (plr.equals(currentPlayer()) && piece.isValidMove(m, board)) {
			return true;
		}
		
		return false;
	}

	@Override
	public void move(Move move) {
		board.move(move);
	}

	@Override
	public boolean inCheck() {
		
		Player p = Player.WHITE;
		
		// Location of the white king
		int whiteR = board.findKing(p)[0];
		int whiteC = board.findKing(p)[1];
		
		p.next();
		
		// Location of the black king
		int blackR = board.findKing(p)[0];
		int blackC = board.findKing(p)[1];

		/* Loops through the entire board, and each time
		 * it finds a piece, it tries to move that piece
		 * to it's enemies King. The game is in check if
		 * any piece succeeds */
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				IChessPiece piece = pieceAt(r, c);

				if (piece == null) continue;
				
				Move m;
				
				if (piece.player() == Player.WHITE) {
					m = new Move(r, c, blackR, blackC);
					p.next();
				}
				
				else {
					m = new Move(r, c, whiteR, whiteC);
					p.next();
				}
				
				if (isValidMove(m)) {
					playerInCheck = p;
					return true;
				}
			}
		}

		playerInCheck = null;
		return false;
	}

	@Override
	public Player currentPlayer() {
		return board.getCurrentPlayer();
	}

	@Override
	public IChessPiece pieceAt(int row, int column) {
		return board.pieceAt(row, column);
	}
	
	/****************************************************************
	 * Re-makes the save board using the chess boards copy constructor
	 * thus making it into a copy of the 'real' game board
	 ***************************************************************/
	private void saveBoard() {
		savedBoard = new ChessBoard(board);
	}
	
	/****************************************************************
	 * Re-makes the 'real' game board using the saveBoard's copy
	 * constructor, thus reverting the game to the 'saved' state
	 ***************************************************************/
	private void revertBoard() {
		board = new ChessBoard(savedBoard);
	}
}