package model;

public class ChessModel implements IChessModel {

	// add instance and/or class variables as needed
	
	// add other (public or private) methods as needed
	
	@Override
	public boolean isComplete() {
		// TODO
		return false;
	}

	@Override
	public boolean isValidMove(Move move) {
		// TODO
		return false;
	}

	@Override
	public void move(Move move) {
		// TODO
	}

	@Override
	public boolean inCheck() {
		// TODO
		return false;
	}

	@Override
	public Player currentPlayer() {
		// TODO
		return null;
	}

	@Override
	public IChessPiece pieceAt(int row, int column) {
		// TODO
		return null;
	}

}
