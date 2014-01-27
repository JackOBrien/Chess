package model;

/**
 * A pawn in a game of chess
 * 
 * @author YOUR NAME(S)
 */
public class Pawn extends ChessPiece {

	private int direction;
	private int startingRow;

	/**
	 * Constructs a new Pawn object
	 * 
	 * @param color the player that owns this piece.
	 */
	public Pawn(Player color) {
		super(color);
		direction = (this.player() == Player.WHITE ? 1 : -1);
		startingRow = (this.player() == Player.WHITE ? 1 : 6);
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

		// Pawn can only move one square at at time.
		// In their first move, they can move two squares if they want.
		// Pawn can only move straight forward.
		// Pawn can only capture one of the enemy by approaching diagonally
		
		// TODO
		return false;
	}

}
