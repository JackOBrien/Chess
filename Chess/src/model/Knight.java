package model;

/**
 * The Knight in a game of chess
 * 
 * @author YOUR NAME(S)
 */
public class Knight extends ChessPiece {

	/**
	 * Constructs a new Knight object
	 * 
	 * @param color the player that owns this piece
	 */
	protected Knight(Player color) {
		super(color);
	}

	@Override
	public String type() {
		return "Knight";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
			
		// Knight can jump over another chess piece or pieces
		// Can move two squares either forward, backward, left, or right
		// and then left or right one square.

		// TODO
		return false;
	}

}
