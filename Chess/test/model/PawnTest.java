package model;

import static org.junit.Assert.*;
import model.Pawn;

import org.junit.Test;


public abstract class PawnTest extends ChessPieceTest {

	private Player plr;
	
	public PawnTest(Player plr) {
		this.plr = plr;
	}

	protected abstract int direction();
	
	@Override
	protected model.IChessPiece make(Player p) {
		return new Pawn(plr);
	}

	@Override
	protected Move getValidMove(int row, int col) {
		int newRow = row +1;
		if (newRow >= board.numRows()) {
			newRow = row;
		}
		return new Move(row, col, newRow, col);
	}
	
	@Test
	public void canMoveInRow() throws Exception {
	   board.set(piece, 4, 4);
	   assertTrue(piece.isValidMove(new Move(4, 4, 4 +direction() , 4), 
			   board));
	}
	
	@Test
	public void canMoveTwice() throws Exception {
		int row = ((Pawn) piece).startingRow;
		
		board.set(piece, row, 4);
		assertTrue(piece.isValidMove(new Move(row, 4, row + (direction() *2)
				, 4), board));
	}
	
	@Test
	public void canNotMoveTwice() throws Exception {
		board.set(piece, 4, 4);
		assertTrue(piece.isValidMove(new Move(4, 4, 4 +direction(), 
				4), board));
		board.set(null, 4, 4);
		board.set(piece, 4 + direction(), 4);
		assertFalse(piece.isValidMove(new Move(4 + direction(), 4
				, 4 + (direction() *3), 4), board));
		assertTrue(piece.isValidMove(new Move(4 + direction(), 4, 
				4 + (direction() *2), 4), board));
	}
	
	@Override 
	public void canCaputre() throws Throwable {
		board.set(piece, 4, 4);
		board.set(new Pawn(plr.next()), 4 + direction(), 3);
		assertTrue(piece.isValidMove(new Move(4, 4, 4 + direction(), 
				3), board));
	}
	
	@Test 
	public void cantMoveAnywhereElse() throws Throwable{
		board.set(piece, 4, 4);
		
		for (int r = 0; r < 8; r++){
			for (int c = 0; c < 8; c++){
				if (r == 4 +direction() && c == 4 || 
					r == (4 +direction()*2) && c == 4) continue;
				else
					assertFalse(piece.isValidMove(new Move(4, 4, r, c), 
							board));
			}
		}
		
	}
	
//	@Test
//	public void cantPromoteFromStart() throws Throwable {
//		Pawn piece = (Pawn) this.piece;
//		
//		if (plr == Player.WHITE)	
//			board.set(piece, 6, 1);
//		else
//			board.set(piece, 1, 1);
//		assertFalse(piece.mayPromote());
//	}
	
//	@Test 
//	public void canPromote() throws Throwable {
//		Pawn piece = (Pawn) this.piece;
//		if (plr == Player.WHITE){
//			board.set(piece, 1, 0);
//			board.move(new Move(1, 0, 0, 0));
//			piece.setLastMove(new Move(1, 0, 0, 0));
//		}else{
//			board.set(piece, 6, 0);
//			board.move(new Move(6, 0, 7, 0));
//			piece.setLastMove(new Move(6, 0, 7, 0));
//		}
//		assertTrue(piece.mayPromote());
//	}
	
	@Test
	public void canEnPassant() throws Throwable {
		IChessPiece pawn = new Pawn(plr.next());
		if (plr == Player.WHITE){
			board.set(piece, 3, 3);
			board.set(pawn, 1, 2);
			pawn.isValidMove(new Move (1, 2, 3, 2), board);
			board.move(new Move(1, 2, 3, 2));
			assertTrue(piece.isValidMove(new Move(3, 3, 2, 2), board));
		}else{
			board.set(piece, 4, 3);
			board.set(pawn, 6, 2);
			pawn.isValidMove(new Move(6, 2, 4, 2), board);
			board.move(new Move(6, 2, 4, 2));
			assertTrue(piece.isValidMove(new Move(4, 3, 5, 2), board));
		}
	}
	
	@Test
	public void cantEnPassant_1() throws Throwable {
		IChessPiece otherPiece = new Pawn(Player.BLACK);
		board.set(otherPiece, 5, 6);
		
		IChessPiece pawn = new Pawn(plr.next());
		if (plr == Player.WHITE){
			board.set(piece, 3, 3);
			board.set(pawn, 1, 2);
			pawn.isValidMove(new Move (1, 2, 3, 2), board);
			board.move(new Move(1, 2, 3, 2));
			board.move(new Move(5, 5, 5, 6));
			assertFalse(piece.isValidMove(new Move(3, 3, 2, 2), board));
		}else{
			board.set(piece, 4, 3);
			board.set(pawn, 6, 2);
			pawn.isValidMove(new Move(6, 2, 4, 2), board);
			board.move(new Move(6, 2, 4, 2));
			board.move(new Move(5, 5, 5, 6));
			assertFalse(piece.isValidMove(new Move(4, 3, 5, 2), board));
			}
	}
		
		@Test
		public void cantEnPassant_2() throws Throwable {
			IChessPiece pawn = new Pawn(plr.next());
			if (plr == Player.WHITE){
				board.set(piece, 3, 3);
				board.set(pawn, 1, 2);
				pawn.isValidMove(new Move (1, 2, 2, 2), board);
				board.move(new Move(1, 2, 2, 2));
				pawn.isValidMove(new Move (2, 2, 3, 2), board);
				board.move(new Move(2, 2, 3, 2));
				board.move(new Move(5, 5, 5, 6));
				assertFalse(piece.isValidMove(new Move(3, 3, 2, 2), board));
			}else{
				board.set(piece, 4, 3);
				board.set(pawn, 6, 2);
				pawn.isValidMove(new Move(6, 2, 5, 2), board);
				board.move(new Move(6, 2, 5, 2));
				pawn.isValidMove(new Move (5, 2, 4, 2), board);
				board.move(new Move(5, 2, 4, 2));
				board.move(new Move(5, 5, 5, 6));
				assertFalse(piece.isValidMove(new Move(4, 3, 5, 2), board));
			}
	}

}