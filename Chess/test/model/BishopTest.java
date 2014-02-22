package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
		getBoard().set(getPiece(), 5, 5);
		assertTrue(getPiece().isValidMove(new Move(5, 5, 3, 7), getBoard()));
		
	}
	
	@Test
	public final void canMoveLeft() throws Exception {
		getBoard().set(getPiece(), 0, 7);
		assertTrue(getPiece().isValidMove(new Move(0, 7, 7, 0), getBoard()));
		
	}
	
	@Test
	public final void cantMoveIfPieceInWay1() throws Exception {
		getBoard().set(getPiece(), 4, 4);
		getBoard().set(new Pawn(getPiece().player().next()), 2, 2);
		assertFalse(getPiece().isValidMove(new Move(4, 4, 1, 1), getBoard()));
	}
	@Test
	public final void canMoveIfPieceInWay2() throws Exception {
		getBoard().set(getPiece(), 4, 4);
		getBoard().set(new Pawn(getPiece().player()), 5, 3);
		assertTrue(getPiece().isValidMove(new Move(4, 4, 3, 3), getBoard()));
	}
	@Test
	public final void cantMoveIfPieceInWay3() throws Exception {
		getBoard().set(getPiece(), 4, 4);
		getBoard().set(new Pawn(getPiece().player().next()), 2, 6);
		assertFalse(getPiece().isValidMove(new Move(4, 4, 1, 7), getBoard()));
	}
	@Test
	public final void cantMoveIfPieceInWay4() throws Exception {
		getBoard().set(getPiece(), 4, 4);
		getBoard().set(new Pawn(getPiece().player().next()), 4, 5);
		assertTrue(getPiece().isValidMove(new Move(4, 4, 5, 5), getBoard()));
	}
	
	@Override
	public final void canCaputre() throws Throwable {
		getBoard().set(getPiece(), 7, 0);
		getBoard().set(make(getPiece().player().next()), 5, 2);
		assertTrue(getPiece().isValidMove(new Move(7, 0, 5, 2), getBoard()));
	}
	
	@Test
	public final void cantCaputre() throws Throwable {
		getBoard().set(getPiece(), 7, 0);
		getBoard().set(make(getPiece().player()), 5, 2);
		assertFalse(getPiece().isValidMove(new Move(7, 0, 5, 2), getBoard()));
	}
}
