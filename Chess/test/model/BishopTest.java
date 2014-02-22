package model;

import static org.junit.Assert.*;
import model.*;

import org.junit.Test;


public class BishopTest extends ChessPieceTest {

	@Override
	protected final IChessPiece make(final Player p) {
		return new Bishop(p);
	}

	@Override
	protected final Move getValidMove(final int row, final int col) {
		int newRow = row + 1;
	   
	    return new Move(row, col, newRow, col);
	}
	
	@Test
	public final void canMoveRight() throws Exception {
		board.set(piece, 5, 5);
		assertTrue(piece.isValidMove(new Move(5, 5, 3, 7), board));
		
	}
	
	@Test
	public final void canMoveLeft() throws Exception {
		board.set(piece, 0, 7);
		assertTrue(piece.isValidMove(new Move(0, 7, 7, 0), board));
		
	}
	
	@Test
	public final void cantMoveIfPieceInWay1() throws Exception {
		board.set(piece, 4, 4);
		board.set(new Pawn(piece.player().next()), 2, 2);
		assertFalse(piece.isValidMove(new Move(4, 4, 1, 1), board));
	}
	@Test
	public final void canMoveIfPieceInWay2() throws Exception {
		board.set(piece, 4, 4);
		board.set(new Pawn(piece.player()), 5, 3);
		assertTrue(piece.isValidMove(new Move(4, 4, 3, 3), board));
	}
	@Test
	public final void cantMoveIfPieceInWay3() throws Exception {
		board.set(piece, 4, 4);
		board.set(new Pawn(piece.player().next()), 2, 6);
		assertFalse(piece.isValidMove(new Move(4, 4, 1, 7), board));
	}
	@Test
	public final void cantMoveIfPieceInWay4() throws Exception {
		board.set(piece, 4, 4);
		board.set(new Pawn(piece.player().next()), 4, 5);
		assertTrue(piece.isValidMove(new Move(4, 4, 5, 5), board));
	}
	
	@Override
	public final void canCaputre() throws Throwable {
		board.set(piece, 7, 0);
		board.set(make(piece.player().next()), 5, 2);
		assertTrue(piece.isValidMove(new Move(7, 0, 5, 2), board));
	}
	
	@Test
	public final void cantCaputre() throws Throwable {
		board.set(piece, 7, 0);
		board.set(make(piece.player()), 5, 2);
		assertFalse(piece.isValidMove(new Move(7, 0, 5, 2), board));
	}
}
