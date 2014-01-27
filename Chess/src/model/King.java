package model;

/**
 * The King in a game of chess.
 * 
 * @author YOUR NAME(S)
 */
public class King extends ChessPiece {

	/**
	 * Constructs a new King object
	 * 
	 * @param color the player that owns this piece
	 */
	protected King(Player color) {
		super(color);
	}

	@Override
	public String type() {
		return "King";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
			
		// King can only move one square at a time but he can move
		// forward, backward, left, right, and diagonally.
		
		// TODO
		return false;
	}

}
