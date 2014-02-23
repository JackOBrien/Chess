package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KingTest extends ChessPieceTest {

	private Player plr;
	
	@Override
	protected final IChessPiece make(final Player p) {
	    plr = p;  
		return new King(p);
	   }

	   @Override
	   protected final Move getValidMove(final int row, final int col) {
	      int newRow = row + 1;

	      return new Move(row, col, newRow, col);
	   }

	   @Test
	   public final void canMoveInRow() throws Exception {
	      getBoard().set(getPiece(), 1, 1);
	      assertTrue(getPiece().isValidMove(new Move(1, 1, 1, 2), getBoard()));
	   }
	   
	   @Test
	   public final void canMoveInColumn() throws Exception {
	      getBoard().set(getPiece(), 1, 1);
	      assertTrue(getPiece().isValidMove(new Move(1, 1, 2, 1), getBoard()));
	   }
	   
	   @Test
	   public final void canMoveDiag() throws Exception {
		   getBoard().set(getPiece(), 1, 1);
		   assertTrue(getPiece().isValidMove(new Move(1, 1, 2, 2), getBoard()));
	   }
	   
	   @Test
	   public final void canCastleKingSide() throws Exception {
		   getBoard().set(getPiece(), 7, 4);
		   getBoard().set(new Rook(plr), 7, 7);
		   assertTrue(getPiece().isValidMove(new Move(7, 4, 7, 6), getBoard()));
	   }
	   
	   @Test
	   public final void canCastleQueenSide() throws Exception {
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(new Rook(plr), 0, 0);
		   assertTrue(getPiece().isValidMove(new Move(0, 4, 0, 2), getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleKingSideBlocked() throws Exception {
		   getBoard().set(getPiece(), 7, 4);
		   getBoard().set(new Rook(plr), 7, 7);
		   getBoard().set(new Rook(plr), 7, 5);
		   assertFalse(getPiece().isValidMove(new Move(7, 4, 7, 6),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleQueenSideBlocked() throws Exception {
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(new Rook(plr), 0, 0);
		   getBoard().set(new Pawn(plr.next()), 0, 1);
		   assertFalse(getPiece().isValidMove(new Move(0, 4, 0, 2),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleQueenSideBlocked2() throws Exception {
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(new Rook(plr), 0, 0);
		   getBoard().set(new Pawn(plr.next()), 0, 3);
		   assertFalse(getPiece().isValidMove(new Move(0, 4, 0, 2),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleQueenSideWrongTeam() throws Exception {
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(new Rook(plr.next()), 0, 0);
		   assertFalse(getPiece().isValidMove(new Move(0, 4, 0, 2),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleKingSideKingMoved() throws Exception {
		   getBoard().set(getPiece(), 7, 4);
		   getBoard().set(new Rook(plr), 7, 7);
		   getBoard().move(new Move(7, 4, 7, 3));
		   assertFalse(getPiece().isValidMove(new Move(7, 3, 7, 6),
				   getBoard()));
		   getBoard().move(new Move(7, 3, 7, 4));
		   assertFalse(getPiece().isValidMove(new Move(7, 4, 7, 6),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleKingSideRookMoved() throws Exception {
		   IChessPiece rook = new Rook(plr);
		   getBoard().set(getPiece(), 7, 4);
		   getBoard().set(rook, 7, 7);
		   rook.isValidMove(new Move(7, 7, 7, 5), getBoard());
		   getBoard().move(new Move(7, 7, 7, 5));
		   assertFalse(getPiece().isValidMove(new Move(7, 4, 7, 6),
				   getBoard()));
		   rook.isValidMove(new Move(7, 5, 7, 7), getBoard());
		   getBoard().move(new Move(7, 5, 7, 7));
		   assertFalse(getPiece().isValidMove(new Move(7, 4, 7, 6),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleQueenSideKingMoved() throws Exception {
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(new Rook(plr), 0, 0);
		   getBoard().move(new Move(0, 4, 0, 5));
		   assertFalse(getPiece().isValidMove(new Move(0, 5, 0, 2),
				   getBoard()));
		   getBoard().move(new Move(0, 5, 0, 4));
		   assertFalse(getPiece().isValidMove(new Move(0, 4, 0, 2),
				   getBoard()));
	   }
	   
	   @Test
	   public final void canNotCastleQueenSideRookMoved() throws Exception {
		   IChessPiece rook = new Rook(plr);
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(rook, 0, 0);
		   rook.isValidMove(new Move(0, 0, 0, 1), getBoard());
		   getBoard().move(new Move(0, 0, 0, 1));
		   assertFalse(getPiece().isValidMove(new Move(0, 4, 0, 2),
				   getBoard()));
		   rook.isValidMove(new Move(0, 1, 0, 0), getBoard());
		   getBoard().move(new Move(0, 1, 0, 0));
		   assertFalse(getPiece().isValidMove(new Move(0, 4, 0, 2),
				   getBoard()));
	   }
	   
	   @Test
	   public final void cantMoveAnywhereElse() throws Exception {
		   getBoard().set(getPiece(), 4, 4);
		   
		   for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (r > 5 || r < 3 || c > 5 || c < 3) {
						assertFalse(getPiece().isValidMove(new Move(4, 4, r, c)
						, getBoard()));
					}
					
				}
		   }
	   }
	   
	   @Test
	   public final void cantCastleInCheckQueen() throws Exception {		   
		   getBoard().set(getPiece(), 0, 4);
		   getBoard().set(new Rook(plr), 0, 0);
		   assertTrue(getPiece().isValidMove(new Move(0, 4, 0, 2), getBoard()));
	   }
}
