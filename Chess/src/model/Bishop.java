package model;

/**
 * A Bishop in a game of chess
 * 
 * @author YOUR NAME(S)
 */
public class Bishop extends ChessPiece {

	/**
	 * Constructs a new Bishop object
	 * 
	 * @param color the player that owns this piece
	 */
	protected Bishop(Player color) {
		super(color);
	}

	@Override
	public String type() {
		return "Bishop";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
		
		// Bishop can move diagonally only just as many spaces as desired
		// without jumping over other chess pieces
		
		// TODO
		return false;
	}
}
