package model;

import java.lang.Math;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A Pawn in a game of chess. 
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 20, 2014
 *******************************************************************/
public class Pawn extends ChessPiece {

	/** The direction this pawn is moving (-1 up, 1 down) */
	private int direction;
	
	/** The row this pawn starts on */
	public int startingRow;
	
	/** The number of moves that have taken place the last time this
	 * pawn moved on the board */
	private int gamePosition;
	
	/** Tells if this pawn moved twice in one turn */
	private boolean movedTwice;
	
	/** Keeps track of what this pawn's last turn was */
	private Move lastMove;

	/****************************************************************
	 * Constructor for Pawn.
	 * 
	 * @param player the Player who owns this piece
	 ***************************************************************/
	public Pawn(Player color) {
		super(color);
		direction = (player() == Player.WHITE ? -1 : 1);
		startingRow = (player() == Player.WHITE ? 6 : 1);
		gamePosition = 0;
		movedTwice = false;
		lastMove = null;
	}

	@Override
	public String type() {
		return "Pawn";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		
		if (!super.isValidMove(move, board)) {
			return false;
		}
		
		/* Checks for for en Passant move */
		if (canEnPassant(move, board)) { return true; }
				
		int fR = move.getFromRow(), fC = move.getFromColumn();
		int tR = move.getToRow(), tC = move.getToColumn();
		
		/* Ensures the pawn doesn't move backwards */
		if ((tR - fR) * direction < 0) return false;
		
		/* Check if the piece is not attacking */
		if (fC == tC) {	
			return isMovingForward(move, board);
		}

		/* Checks if the piece is attacking */
		if (board.pieceAt(move.getToRow(), move.getToColumn()) != null) {
			if (isAttacking(move, board)) {
				lastMove = move;
				return true;
			}
		}
		
		return false;
	}

	/****************************************************************
	 * Tells if the pawn's destination is null and it is moving 
	 * to one of it's two forward corners
	 * 
	 * @param m the move being attempted
	 * @param b the board the move takes place on
	 * @return true if the piece is attacking, false otehrwise
	 ***************************************************************/
	public boolean isAttacking(Move m, IChessBoard b) {
			int rowDist = m.getToRow() - m.getFromRow();
			int colDist = Math.abs(m.getFromColumn() - m.getToColumn());

			// Piece must move up one row and over one column
			return (rowDist == direction && colDist == 1);
	}
	
	/****************************************************************
	 * Tells if the piece is making a legal, non-attacking move.
	 * 
	 * @param m move being checked.
	 * @param b board the move is being attempted on.
	 * @return true if the piece makes a legal forward move.
	 ***************************************************************/
	private boolean isMovingForward(Move m, IChessBoard board) {
		int fR = m.getFromRow(), fC = m.getFromColumn();
		int tR = m.getToRow(), tC = m.getToColumn();

		/* Ensures the pawn does not attack forward */
		if (board.pieceAt(tR, tC) != null) { return false; }

		/* Move is valid if the pawn only moves forward one */
		if (tR == fR + direction) { 
			lastMove = m;
			return true; 
		}

		/* Ensures the spot in front of the pawn is clear */
		if (board.pieceAt(fR + direction, fC) != null) { return false; }

		/* Checks if the piece is still in its starting location */
		if (fR == startingRow) {

			/* Checks if the piece is trying to move forward twice */
			if (tR == fR + direction * 2) {
				gamePosition = board.getNumMoves();
				movedTwice = true;
				return true;
			}
		}		

		return false;
	}
	
	/****************************************************************
	 * Tells if the piece can perform the special move en Passant
	 * 
	 * @param m the move being attempted
	 * @param b the board the move is being attempted on
	 * @return true if the piece may en Passant, false otehrwise
	 ***************************************************************/
	private boolean canEnPassant(Move m, IChessBoard b) {
		int fR = m.getFromRow(), tC = m.getToColumn();
		
		/* Ensures the piece is attacking */
		if (!isAttacking(m, b)) { return false; }
		
		IChessPiece attacked = b.pieceAt(fR, tC);
		Pawn victim = null;
		
		/* Checks for a pawn in the proper position */
		if (attacked != null && attacked.is(type())) {
			victim = (Pawn) attacked;
		} else { return false; }
		
		/* Ensures this move is taking place immediately after the victim pawn
		 * moved into the proper position */
		if (b.getNumMoves() != victim.getNumMoves() + 1) { return false; }
		
		/* If the victim pawn moved two squares at once, this move is valid */
		return victim.movedTwice;
	}
	
	/****************************************************************
	 * Returns the number of moves there were on the game board when this
	 * pawn last moved
	 * 
	 * @return number of moves when this piece last moved
	 ***************************************************************/
	public int getNumMoves() {
		return gamePosition;
	}
	
	/****************************************************************
	 * Tells if the pawn may promote
	 * 
	 * @param row the row location of the pawn
	 * @return true if the pawn may promote, false otherwise.
	 ***************************************************************/
	public boolean mayPromote() {
		
		if (lastMove == null) { return false; }
		
		if (player() == Player.WHITE) {
			return lastMove.getToRow() == 0;
		} else {
			return lastMove.getToRow() == 7;
		}
	}
}
