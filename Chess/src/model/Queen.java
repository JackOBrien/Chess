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
			
		// Queen can move vertically, horizontally, and diagonally
		// as many spaces desired without jumping over other chess pieces
		
		// TODO
		return false;
	}

}
