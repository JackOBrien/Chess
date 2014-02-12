package model;


/**
 * A Queen in a game of chess
 * 
 * @author YOUR NAME(S)
 */
public class Queen extends ChessPiece {
	
	/**
	 * Constructs a new Queen object
	 * 
	 * @param color the player that owns this piece
	 */
	protected Queen(Player color) {
		super(color);
	}

	@Override
	public String type() {
		return "Queen";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
			
		/* Checks if the Queen is either moving like a Rook, or if
		 * it is moving like a Bishop.  */
		if (Rook.movingDiagonally(move, board) ||
				Bishop.movingPerpendicular(move, board)) {
			return true;
		}
		
		return false;
	}

}
