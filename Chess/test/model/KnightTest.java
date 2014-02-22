package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KnightTest extends ChessPieceTest {

	@Override
	protected final IChessPiece make(final Player p) {
		return new Knight(p);
	}

	@Override
	protected final Move getValidMove(final int row, final int col) {
		int newRow = row + 2;
		int newCol = col + 1;
		
		return new Move(row, col, newRow, newCol);
	}
	
	@Override 
	public final void canCaputre() throws Throwable {
		board.set(piece, 4, 4);
		board.set(new Pawn(piece.player().next()), 2, 3);
		assertTrue(piece.isValidMove(new Move(4, 4, 2, 3), board));
	}
	
	@Test
	public final void canMoveOverPieces() throws Exception {
		board.set(piece, 4, 4);
		board.set(new Pawn(Player.WHITE), 3, 3);
		board.set(new Pawn(Player.BLACK), 3, 4);
		board.set(new Pawn(Player.WHITE), 3, 5);
		assertTrue(piece.isValidMove(new Move(4, 4, 2, 3), board));
	}
	
	@Test
	public final void cantMoveInRow() throws Exception {
		board.set(piece, 4, 4);
		assertFalse(piece.isValidMove(new Move(4, 4, 4, 3), board));
	}

	@Test
	public final void cantMoveInCol() throws Exception {
		board.set(piece, 4, 4);
		assertFalse(piece.isValidMove(new Move(4, 4, 3, 4), board));
	}
}
