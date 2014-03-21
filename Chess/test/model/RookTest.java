package model;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RookTest extends ChessPieceTest {

   @Override
   protected final IChessPiece make(final Player p) {
      return new Rook(p);
   }

   @Override
   protected final Move getValidMove(final int row, final int col) {
      int newRow = row + 1;

      return new Move(row, col, newRow, col);
   }

   @Test
   public final void canMoveInRow() throws Exception {
      getBoard().set(getPiece(), 1, 1);
      assertTrue("Rook Test 1", getPiece().isValidMove(new Move(1, 1, 1, 6),
    		  getBoard()));
   }

   @Test
   public final void canMoveInColumn() throws Throwable {
	   getBoard().set(getPiece(), 1, 1);
       assertTrue("Rook Test 2", getPiece().isValidMove(new Move(1, 1, 6, 1)
       , getBoard()));
   }

   @Test
   public final void cannotMoveDiagonal() throws Throwable {
	  getBoard().set(getPiece(), 1, 1);
      assertFalse("Rook Test 3", getPiece().isValidMove(new Move(1, 1, 3, 3)
      , getBoard()));
   }
   
   @Test
   public final void cannotMoveIfPieceInTheWay() throws Throwable { 
	   getBoard().set(getPiece(), 7, 0);
	   getBoard().set(new Pawn(Player.WHITE), 5, 0);
	   assertFalse(getPiece().isValidMove(new Move(7, 0, 4, 0), getBoard()));
   }
   
   @Test
   public final void canMoveBackwards() throws Throwable { 
	   getBoard().set(getPiece(), 7, 0);
	   assertTrue(getPiece().isValidMove(new Move(7, 0, 4, 0), getBoard()));
   }
   
   @Test
   public final void canMoveCloseInRow() throws Exception {
      getBoard().set(getPiece(), 1, 1);
      assertTrue("Rook Test 1", getPiece().isValidMove(new Move(1, 1, 1, 2),
    		  getBoard()));
   }

   @Test
   public final void canMoveCloseInColumn() throws Throwable {
	   getBoard().set(getPiece(), 1, 1);
       assertTrue("Rook Test 2", getPiece().isValidMove(new Move(1, 1, 0, 1),
    		   getBoard()));
   }
   
   @Test
   public final void cantMoveAnywhereElse() throws Throwable {
	   getBoard().set(getPiece(), 4, 4);

	   for (int r = 0; r < 8; r++) {
		   for (int c = 0; c < 8; c++) {
			   if (r != 4 && c != 4 
					   || r == 4 && c == 4) {
				   assertFalse(getPiece().isValidMove(new Move(4, 4, r, c), 
						   getBoard()));
			   } else {
				   assertTrue(getPiece().isValidMove(new Move(4, 4, r, c), 
						   getBoard()));
			   }
		   }
	   }
   }
   
//   @Test
//   public final void checkIfMoved() throws Throwable {
//	   IChessBoard board = getBoard();
//	   Rook r = new Rook(Player.WHITE);
//	   board.set(r, 7, 7);
//	   assertFalse(r.checkifMoved(7, 7));
//	   r.isValidMove(new Move(7, 7, 7, 6), board);
//	   assertFalse(r.checkifMoved(7, 7));
//	   r.isValidMove(new Move(7, 7, 7, 6), board);
//	   board.move(new Move(7, 7, 7, 6));
//	   assertTrue(r.checkifMoved(7, 6));
//   }
//   
//   @Test
//   public final void checkIfMoved2() throws Throwable {
//	   IChessBoard board = getBoard();
//	   Rook r = new Rook(Player.WHITE);
//	   board.set(r, 7, 0);
//	   assertFalse(r.checkifMoved(7, 0));
//	   r.isValidMove(new Move(7, 0, 7, 3), board);
//	   board.move(new Move(7, 0, 7, 3));
//	   assertTrue(r.checkifMoved(7, 3));
//	   assertTrue(r.checkifMoved(7, 3));
//   }

}