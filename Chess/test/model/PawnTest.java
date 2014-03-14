package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public abstract class PawnTest extends ChessPieceTest {

	private Player plr;
	
	public PawnTest(final Player pPlayer) {
		this.plr = pPlayer;
	}

	protected abstract int direction();
	
	@Override
	protected final model.IChessPiece make(final Player p) {
		return new Pawn(plr);
	}

	@Override
	protected final Move getValidMove(final int row, final int col) {
		int newRow = row + direction();
		
		return new Move(row, col, newRow, col);
	}
	
	@Test
	public final void canMoveInRow() throws Exception {
	   getBoard().set(getPiece(), 4, 4);
	   Move m = new Move(4, 4, 4 + direction(), 4);
	   assertTrue(getPiece().isValidMove(m, getBoard()));
	}
	
	@Test
	public final void canMoveTwice() throws Exception {
		int row = ((Pawn) getPiece()).getStartingRow();
		
		getBoard().set(getPiece(), row, 4);
		assertTrue(getPiece().isValidMove(new Move(row, 4, row 
				+ (direction() * 2), 4), getBoard()));
	}
	
	@Test
	public final void canNotMoveTwice() throws Exception {
		getBoard().set(getPiece(), 4, 4);
		assertTrue(getPiece().isValidMove(new Move(4, 4, 4 + direction(), 
				4), getBoard()));
		getBoard().unset(4, 4);
		getBoard().set(getPiece(), 4 + direction(), 4);
		assertFalse(getPiece().isValidMove(new Move(4 + direction(), 4
				, 4 + (direction() * 3), 4), getBoard()));
		assertTrue(getPiece().isValidMove(new Move(4 + direction(), 4, 
				4 + (direction() * 2), 4), getBoard()));
	}
	
	@Test
	public final void canNotMoveTwice2() throws Exception {
		Pawn p = (Pawn) getPiece();
		int row = ((Pawn) getPiece()).getStartingRow();
 
		getBoard().set(getPiece(), row, 2);
		Move m = new Move(row, 2, row + direction(), 2);
		assertTrue(p.isValidMove(m, getBoard()));
		getBoard().move(m);
		m = new Move(row + direction(), 2, row + direction() * 3, 2);
		assertFalse(p.isValidMove(m, getBoard()));
	}
	
	@Override 
	public final void canCaputre() throws Throwable {
		getBoard().set(getPiece(), 4, 4);
		getBoard().set(new Pawn(plr.next()), 4 + direction(), 3);
		assertTrue(getPiece().isValidMove(new Move(4, 4, 4 + direction(), 
				3), getBoard()));
	}
	
	@Test 
	public final void cantMoveAnywhereElse() throws Throwable {
		getBoard().set(getPiece(), 4, 4);
		
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (r == 4 + direction() && c == 4 
						|| r == (4 + direction() * 2) && c == 4) {
					continue;
				} else {
					assertFalse(getPiece().isValidMove(new Move(4, 4, r, c), 
							getBoard()));
				}
			}
		}
	}
	
	@Test
	public final void cantPromoteFromStart() throws Throwable {
		Pawn p = (Pawn) getPiece();
		
		getBoard().set(p, p.getStartingRow(), 4);
		assertFalse(p.mayPromote());
	}
	
	@Test
	public final void cantPromoteMiddle() throws Throwable {
		Pawn p = (Pawn) getPiece();
		
		getBoard().set(p, 4, 4);
		assertTrue(p.isValidMove(new Move(4, 4, 4 + direction(), 4),
				getBoard()));
		assertFalse(p.mayPromote());
	}
	
	@Test 
	public final void canPromote() throws Throwable {
		Pawn p = (Pawn) getPiece();
		if (plr == Player.WHITE) {
			getBoard().set(getPiece(), 1, 0);
			assertTrue(p.isValidMove(new Move(1, 0, 0, 0), getBoard()));
			getBoard().move(new Move(1, 0, 0, 0));
		} else {
			getBoard().set(p, 6, 0);
			assertTrue(p.isValidMove(new Move(6, 0, 7, 0), getBoard()));
			getBoard().move(new Move(6, 0, 7, 0));
		}
		assertTrue(p.mayPromote());
	}
	
	@Test 
	public final void canPromoteAttack() throws Throwable {
		Pawn p = (Pawn) getPiece();
		if (plr == Player.WHITE) {
			getBoard().set(getPiece(), 1, 0);
			getBoard().set(new Knight(Player.BLACK), 0, 1);
			assertTrue(p.isValidMove(new Move(1, 0, 0, 1), getBoard()));
			getBoard().move(new Move(1, 0, 0, 1));
		} else {
			getBoard().set(p, 6, 0);
			getBoard().set(new Bishop(Player.WHITE), 7, 1);
			assertTrue(p.isValidMove(new Move(6, 0, 7, 1), getBoard()));
			getBoard().move(new Move(6, 0, 7, 1));
		}
		assertTrue(p.mayPromote());
	}
	
	@Test
	public final void canEnPassant() throws Throwable {
		IChessPiece pawn = new Pawn(plr.next());
		if (plr == Player.WHITE) {
			getBoard().set(getPiece(), 3, 3);
			getBoard().set(pawn, 1, 2);
			assertTrue(pawn.isValidMove(new Move(1, 2, 3, 2), getBoard()));
			getBoard().move(new Move(1, 2, 3, 2));
			assertTrue(getPiece().isValidMove(new Move(3, 3, 2, 2),
					getBoard()));
			assertNotNull(getBoard().pieceAt(3, 3));
			getBoard().move(new Move(3, 3, 2, 2));
			assertNull(getBoard().pieceAt(3, 3));
		} else {
			getBoard().set(getPiece(), 4, 3);
			getBoard().set(pawn, 6, 2);
			pawn.isValidMove(new Move(6, 2, 4, 2), getBoard());
			getBoard().move(new Move(6, 2, 4, 2));
			assertTrue(getPiece().isValidMove(new Move(4, 3, 5, 2),
					getBoard()));
			assertNotNull(getBoard().pieceAt(4, 3));
			getBoard().move(new Move(4, 3, 5, 2));
			assertNull(getBoard().pieceAt(4, 3));
		}
	}
	
	@Test
	public final void cantEnPassant1() throws Throwable {
		IChessPiece otherPiece = new Pawn(Player.BLACK);
		getBoard().set(otherPiece, 5, 6);
		
		IChessPiece pawn = new Pawn(plr.next());
		if (plr == Player.WHITE) {
			getBoard().set(getPiece(), 3, 3);
			getBoard().set(pawn, 1, 2);
			pawn.isValidMove(new Move(1, 2, 3, 2), getBoard());
			getBoard().move(new Move(1, 2, 3, 2));
			getBoard().move(new Move(5, 5, 5, 6));
			assertFalse(getPiece().isValidMove(new Move(3, 3, 2, 2),
					getBoard()));
		} else {
			getBoard().set(getPiece(), 4, 3);
			getBoard().set(pawn, 6, 2);
			pawn.isValidMove(new Move(6, 2, 4, 2), getBoard());
			getBoard().move(new Move(6, 2, 4, 2));
			getBoard().move(new Move(5, 5, 5, 6));
			assertFalse(getPiece().isValidMove(new Move(4, 3, 5, 2),
					getBoard()));
			}
	}
		
		@Test
		public final void cantEnPassant2() throws Throwable {
			IChessPiece pawn = new Pawn(plr.next());
			if (plr == Player.WHITE) {
				getBoard().set(getPiece(), 3, 3);
				getBoard().set(pawn, 1, 2);
				pawn.isValidMove(new Move(1, 2, 2, 2), getBoard());
				getBoard().move(new Move(1, 2, 2, 2));
				pawn.isValidMove(new Move(2, 2, 3, 2), getBoard());
				getBoard().move(new Move(2, 2, 3, 2));
				getBoard().move(new Move(5, 5, 5, 6));
				assertFalse(getPiece().isValidMove(new Move(3, 3, 2, 2),
						getBoard()));
			} else {
				getBoard().set(getPiece(), 4, 3);
				getBoard().set(pawn, 6, 2);
				pawn.isValidMove(new Move(6, 2, 5, 2), getBoard());
				getBoard().move(new Move(6, 2, 5, 2));
				pawn.isValidMove(new Move(5, 2, 4, 2), getBoard());
				getBoard().move(new Move(5, 2, 4, 2));
				getBoard().move(new Move(5, 5, 5, 6));
				assertFalse(getPiece().isValidMove(new Move(4, 3, 5, 2),
						getBoard()));
			}
	}
		
		@Test
		public final void cantJump() throws Exception {
			int row = ((Pawn) getPiece()).getStartingRow();
			getBoard().set(getPiece(), row, 4);
			getBoard().set(new Rook(plr.next()), row + direction(), 4);
			assertFalse(getPiece().isValidMove(new Move(row, 4, row
					+ direction() * 2, 4), getBoard()));
		}

}