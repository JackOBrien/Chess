package model;

/**
 * A Rook in a game of chess
 * 
 * @author YOUR NAME(S)
 */
public class Rook extends ChessPiece {

	/**
	 * Constructor a new Rook object
	 * 
	 * @param color the Player that owns this piece.
	 */
	public Rook(Player color) {
		super(color);
	}

	@Override
	public String type() {
		return "Rook";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
			
		// Rook can move forward, backward, left, and right as many spaces
		// as desired without jumping over other chess pieces; can't move diagonally
		
		// TODO
		return false;
	}
}
