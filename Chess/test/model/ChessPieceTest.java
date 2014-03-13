package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/********************************************************************
 * Test the {@code ChessPiece} class.
 *
 * @author Zachary Kurmas
 *******************************************************************/
// Created  12/8/12 at 9:28 PM
// (C) Zachary Kurmas 2012

public abstract class ChessPieceTest {

	private static final int BOARD_SIZE = 8;

	private ChessBoard board;
	private IChessPiece piece;

	public final ChessBoard getBoard() {
		return board;
	}

	public final IChessPiece getPiece() {
		return piece;
	}

	@Before
	public final void makeBoard() {
		// Don't put any pieces on the board.
		board = new ChessBoard(BOARD_SIZE, false);
		piece = make();
	}

	protected final IChessPiece make() {
		return make(Player.WHITE);
	}

	protected abstract IChessPiece make(Player p);

	protected abstract Move getValidMove(int row, int col);

	@Test
	public final void complainsIfTargetOccupiedBySamePlayer() throws Throwable {
		Move move = getValidMove(2, 4);
		board.set(make(), move.toRow(), move.toColumn());
		board.set(piece, move.fromRow(), move.fromColumn());
		assertFalse("ChessPiece Test 3", piece.isValidMove(move, board));
	}

	@Test
	public void canCaputre() throws Throwable { }

	@Test
	public final void hasATypeString() throws Throwable {
		assertNotNull(piece.type());
	}

	@Test (expected = IllegalArgumentException.class)
	public final void complainsIfImproperFromLocation() throws Throwable {
		Move m = new Move(4, 4, 4, 5);
		piece.isValidMove(m, board);
	}

}
