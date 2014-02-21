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
		int newRow = row +direction();
		
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
		board.unset(4, 4);
		board.set(piece, 4 + direction(), 4);
		assertFalse(piece.isValidMove(new Move(4 + direction(), 4
				, 4 + (direction() *3), 4), board));
		assertTrue(piece.isValidMove(new Move(4 + direction(), 4, 
				4 + (direction() *2), 4), board));
	}
	
	@Test
	public void canNotMoveTwice_2() throws Exception {
		Pawn p = (Pawn) piece;
		int row = ((Pawn) piece).startingRow;
 
		board.set(piece, row, 2);
		Move m = new Move(row, 2, row + direction(), 2);
		assertTrue(p.isValidMove(m, board));
		board.move(m);
		m = new Move(row + direction(), 2, row + direction() * 3, 2);
		assertFalse(p.isValidMove(m, board));
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
	
	@Test
	public void cantPromoteFromStart() throws Throwable {
		Pawn p = (Pawn) piece;
		
		board.set(p, p.startingRow, 4);
		assertFalse(p.mayPromote());
	}
	
	@Test
	public void cantPromoteMiddle() throws Throwable {
		Pawn p = (Pawn) piece;
		
		board.set(p, 4, 4);
		assertTrue(p.isValidMove(new Move(4, 4, 4 + direction(), 4), board));
		assertFalse(p.mayPromote());
	}
	
	@Test 
	public void canPromote() throws Throwable {
		Pawn p = (Pawn) piece;
		if (plr == Player.WHITE){
			board.set(piece, 1, 0);
			assertTrue(p.isValidMove(new Move(1, 0, 0, 0), board));
			board.move(new Move(1, 0, 0, 0));
		}else{
			board.set(p, 6, 0);
			assertTrue(p.isValidMove(new Move(6, 0, 7, 0), board));
			board.move(new Move(6, 0, 7, 0));
		}
		assertTrue(p.mayPromote());
	}
	
	@Test 
	public void canPromoteAttack() throws Throwable {
		Pawn p = (Pawn) piece;
		if (plr == Player.WHITE){
			board.set(piece, 1, 0);
			board.set(new Knight(Player.BLACK), 0, 1);
			assertTrue(p.isValidMove(new Move(1, 0, 0, 1), board));
			board.move(new Move(1, 0, 0, 1));
		}else{
			board.set(p, 6, 0);
			board.set(new Bishop(Player.WHITE), 7, 1);
			assertTrue(p.isValidMove(new Move(6, 0, 7, 1), board));
			board.move(new Move(6, 0, 7, 1));
		}
		assertTrue(p.mayPromote());
	}
	
	@Test
	public void canEnPassant() throws Throwable {
		IChessPiece pawn = new Pawn(plr.next());
		if (plr == Player.WHITE){
			board.set(piece, 3, 3);
			board.set(pawn, 1, 2);
			assertTrue(pawn.isValidMove(new Move (1, 2, 3, 2), board));
			board.move(new Move(1, 2, 3, 2));
			assertTrue(piece.isValidMove(new Move(3, 3, 2, 2), board));
			assertNotNull(board.pieceAt(3, 3));
			board.move(new Move(3, 3, 2, 2));
			assertNull(board.pieceAt(3, 3));
		}else{
			board.set(piece, 4, 3);
			board.set(pawn, 6, 2);
			pawn.isValidMove(new Move(6, 2, 4, 2), board);
			board.move(new Move(6, 2, 4, 2));
			assertTrue(piece.isValidMove(new Move(4, 3, 5, 2), board));
			assertNotNull(board.pieceAt(4, 3));
			board.move(new Move(4, 3, 5, 2));
			assertNull(board.pieceAt(4, 3));
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
		
		@Test
		public void cantJump() throws Exception {
			int row = ((Pawn) piece).startingRow;
			board.set(piece, row, 4);
			board.set(new Rook(plr.next()), row + direction(), 4);
			assertFalse(piece.isValidMove(new Move(row, 4, row + 
					direction() * 2, 4), board));
		}

}