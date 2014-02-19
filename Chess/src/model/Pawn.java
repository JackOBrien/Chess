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
 * @version Feb 16, 2014
 *******************************************************************/
public class Pawn extends ChessPiece {

	private int direction;
	
	public int startingRow;
	
	private int gamePosition;
	
	private boolean movedTwice;

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
		
		int fR = move.fromRow, fC = move.fromColumn;
		int tR = move.toRow, tC = move.toColumn;
		
		/* Ensures the pawn doesn't move backwards */
		if ((tR - fR) * direction < 0) return false;
		
		/* Check if the piece is not attacking */
		if (fC == tC) {

			/* Ensures the pawn does not attack forward */
			if (board.pieceAt(tR, tC) != null) { return false; }
			
			/* Move is valid if the pawn only moves forward one */
			if (tR == fR + direction) { return true; }
			
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
		} 

		if (board.pieceAt(move.toRow, move.toColumn) != null) {
			return isAttacking(move, board);
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
	private boolean isAttacking(Move m, IChessBoard b) {
			int rowDist = m.toRow - m.fromRow;
			int colDist = Math.abs(m.fromColumn - m.toColumn);

			// Piece must move up one row and over one column
			return (rowDist == direction && colDist == 1);
	}
	
	private boolean canEnPassant(Move m, IChessBoard b) {
		int fR = m.fromRow, tC = m.toColumn;
		
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
}
