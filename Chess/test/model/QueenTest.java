package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueenTest extends ChessPieceTest {

	@Override
	protected final IChessPiece make(final Player p) {
		return new Queen(p);
	}

	@Override
	protected final Move getValidMove(final int row, final int col) {
		int newRow = row + 1;

	    return new Move(row, col, newRow, col);
	}

	@Test
	public final void canMoveInRow() throws Exception {
		getBoard().set(getPiece(), 0, 0);
		assertTrue(getPiece().isValidMove(new Move(0, 0, 6, 0), getBoard()));
	}
	
	@Test
	public final void canMoveInColumn() throws Exception {
		getBoard().set(getPiece(), 0, 0);
		assertTrue(getPiece().isValidMove(new Move(0, 0, 0, 7), getBoard()));
	}
	
	@Test
	public final void canMoveDiag() throws Exception {
		getBoard().set(getPiece(), 0, 0);
		assertTrue(getPiece().isValidMove(new Move(0, 0, 7, 7), getBoard()));
	}
	
	@Test
	public final void cantMoveElse() throws Exception {
		getBoard().set(getPiece(), 4, 4);
		
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (r == 4 && c != 4 
						|| r != 4 && c == 4) {
					assertTrue(getPiece().isValidMove(new Move(4, 4, r, c), 
							getBoard()));
				}
			}
		}
		
		assertFalse(getPiece().isValidMove(new Move(4, 4, 5, 6), getBoard()));
		assertFalse(getPiece().isValidMove(new Move(4, 4, 2, 3), getBoard()));
	}	
	
	@Test
	public final void cannotMoveIfWhitePieceInTheWay() throws Throwable { 
		getBoard().set(getPiece(), 7, 0);
	 	getBoard().set(new Pawn(Player.WHITE), 5, 0);
		assertFalse(getPiece().isValidMove(new Move(7, 0, 4, 0), getBoard()));
	}
	
	@Test
	public final void cannotMoveIfBlackPieceInTheWay() throws Throwable { 
		getBoard().set(getPiece(), 7, 0);
	 	getBoard().set(new Pawn(Player.BLACK), 5, 0);
		assertFalse(getPiece().isValidMove(new Move(7, 0, 4, 0), getBoard()));
	}
	
	@Test
	public final void cannotMoveIfPieceInTheWayDiag1() throws Throwable { 
		getBoard().set(getPiece(), 7, 0);
	 	getBoard().set(new Pawn(Player.BLACK), 5, 2);
		assertFalse(getPiece().isValidMove(new Move(7, 0, 4, 3), getBoard()));
	}
	
	@Test
	public final void cannotMoveIfPieceInTheWayDiag2() throws Throwable { 
		getBoard().set(getPiece(), 3, 5);
	 	getBoard().set(new Pawn(Player.WHITE), 5, 3);
		assertFalse(getPiece().isValidMove(new Move(3, 5, 7, 1), getBoard()));
	}

}
