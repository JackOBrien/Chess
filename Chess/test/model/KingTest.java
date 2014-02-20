package model;

import static org.junit.Assert.*;
import model.*;


import org.junit.Test;

public class KingTest extends ChessPieceTest{

	private Player plr;
	
	@Override
	   protected IChessPiece make(Player p) {
	    plr = p;  
		return new King(p);
	   }

	   @Override
	   protected Move getValidMove(int row, int col) {
	      int newRow = row + 1;

	      return new Move(row, col, newRow, col);
	   }

	   @Test
	   public void canMoveInRow() throws Exception {
	      board.set(piece, 1, 1);
	      assertTrue(piece.isValidMove(new Move(1, 1, 1, 2), board));
	   }
	   
	   @Test
	   public void canMoveInColumn() throws Exception {
	      board.set(piece, 1, 1);
	      assertTrue(piece.isValidMove(new Move(1, 1, 2, 1), board));
	   }
	   
	   @Test
	   public void canMoveDiag() throws Exception {
		   board.set(piece, 1, 1);
		   assertTrue(piece.isValidMove(new Move(1, 1, 2, 2), board));
	   }
	   
	   @Test
	   public void canCastleKingSide() throws Exception {
		   board.set(piece, 7, 4);
		   board.set(new Rook(plr), 7, 7);
		   assertTrue(piece.isValidMove(new Move(7, 4, 7, 6), board));
	   }
	   
	   @Test
	   public void canCastleQueenSide() throws Exception {
		   board.set(piece, 0, 4);
		   board.set(new Rook(plr), 0, 0);
		   assertTrue(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
	   
	   @Test
	   public void canNotCastleKingSideBlocked() throws Exception {
		   board.set(piece, 7, 4);
		   board.set(new Rook(plr), 7, 7);
		   board.set(new Rook(plr), 7, 5);
		   assertFalse(piece.isValidMove(new Move(7, 4, 7, 6), board));
	   }
	   
	   @Test
	   public void canNotCastleQueenSideBlocked() throws Exception {
		   board.set(piece, 0, 4);
		   board.set(new Rook(plr), 0, 0);
		   board.set(new Pawn(plr.next()), 0, 1);
		   assertFalse(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
	   
	   @Test
	   public void canNotCastleQueenSideBlocked_2() throws Exception {
		   board.set(piece, 0, 4);
		   board.set(new Rook(plr), 0, 0);
		   board.set(new Pawn(plr.next()), 0, 3);
		   assertFalse(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
	   
	   @Test
	   public void canNotCastleQueenSideWrongTeam() throws Exception {
		   board.set(piece, 0, 4);
		   board.set(new Rook(plr.next()), 0, 0);
		   assertFalse(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
	   
	   @Test
	   public void canNotCastleKingSideKingMoved() throws Exception {
		   board.set(piece, 7, 4);
		   board.set(new Rook(plr), 7, 7);
		   board.move(new Move(7, 4, 7, 3));
		   assertFalse(piece.isValidMove(new Move(7, 3, 7, 6), board));
		   board.move(new Move(7, 3, 7, 4));
		   assertFalse(piece.isValidMove(new Move(7, 4, 7, 6), board));
	   }
	   
	   @Test
	   public void canNotCastleKingSideRookMoved() throws Exception {
		   IChessPiece rook = new Rook(plr);
		   board.set(piece, 7, 4);
		   board.set(rook, 7, 7);
		   rook.isValidMove(new Move(7, 7, 7, 5), board);
		   board.move(new Move(7, 7, 7, 5));
		   assertFalse(piece.isValidMove(new Move(7, 4, 7, 6), board));
		   rook.isValidMove(new Move(7, 5, 7, 7), board);
		   board.move(new Move(7, 5, 7, 7));
		   assertFalse(piece.isValidMove(new Move(7, 4, 7, 6), board));
	   }
	   
	   @Test
	   public void canNotCastleQueenSideKingMoved() throws Exception {
		   board.set(piece, 0, 4);
		   board.set(new Rook(plr), 0, 0);
		   board.move(new Move(0, 4, 0, 5));
		   assertFalse(piece.isValidMove(new Move(0, 5, 0, 2), board));
		   board.move(new Move(0, 5, 0, 4));
		   assertFalse(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
	   
	   @Test
	   public void canNotCastleQueenSideRookMoved() throws Exception {
		   IChessPiece rook = new Rook(plr);
		   board.set(piece, 0, 4);
		   board.set(rook, 0, 0);
		   rook.isValidMove(new Move(0, 0, 0, 1), board);
		   board.move(new Move(0, 0, 0, 1));
		   assertFalse(piece.isValidMove(new Move(0, 4, 0, 2), board));
		   rook.isValidMove(new Move(0, 1, 0, 0), board);
		   board.move(new Move(0, 1, 0, 0));
		   assertFalse(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
	   
	   @Test
	   public void cantMoveAnywhereElse() throws Exception {
		   board.set(piece, 4, 4);
		   
		   for (int r = 0; r < 8; r++){
				for (int c = 0; c < 8; c++){
					if (r > 5 || r < 3 || c > 5 || c < 3){
						assertFalse(piece.isValidMove(new Move(4, 4, r, c)
						, board));
					}
					
				}
		   }
	   }
	   
	   @Test
	   public void cantCastleInCheck_Queen() throws Exception{		   
		   board.set(piece, 0, 4);
		   board.set(new Rook(plr), 0, 0);
		   assertTrue(piece.isValidMove(new Move(0, 4, 0, 2), board));
	   }
}
