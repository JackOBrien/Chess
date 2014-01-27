package model;

/**
 * Class that represents a chess piece
 *
 * @author YOUR NAME(S)
 */
public abstract class ChessPiece implements IChessPiece {
	
	private Player owner;

	protected ChessPiece(Player color) {
		this.owner = color;
	}

	public Player player() {
		return owner;
	}

	public abstract String type();

	public boolean isValidMove(Move move, IChessBoard board) {
		// TODO
		return false;
	}
}
