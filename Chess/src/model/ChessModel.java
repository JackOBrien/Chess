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
		// TODO
		return false;
	}

	@Override
	public boolean isValidMove(Move move) {
		IChessPiece piece = pieceAt(move.fromRow, move.fromColumn);
		Player plr = piece.player();
		
		if (basicallyLegal(piece, move)) {
			
			// Saves the current state of the board
			saveBoard();
			
			// Performs the move, regardless of the validity
			board.move(move);
			
			/* Checks if the game is in check before the 
			 * move takes place */
			if (inCheck()) {
				/* The attempted move MUST take the game
				 * out of check for the move to be valid */
			}
			
			/* The game is not in check before the move
			 * takes place */
			else {
				
				// Makes sure the game still isn't in check, and
				// returns true if that's the case.
				if (!inCheck()) return true;
				
				// The game is now in check.
				// Checks to be sure that the current player 
				// is not in check, but that their enemy is.
				if (plr != playerInCheck) {
					return true;
				}
			}
		}
		
		return false;
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
		// TODO
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

}
