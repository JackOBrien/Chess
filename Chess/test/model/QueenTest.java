package model;

import static org.junit.Assert.*;
import model.*;

import model.Pawn;
import model.Queen;

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
		board.set(piece, 0, 0);
		assertTrue(piece.isValidMove(new Move(0, 0, 6, 0), board));
	}
	
	@Test
	public final void canMoveInColumn() throws Exception {
		board.set(piece, 0, 0);
		assertTrue(piece.isValidMove(new Move(0, 0, 0, 7), board));
	}
	
	@Test
	public final void canMoveDiag() throws Exception {
		board.set(piece, 0, 0);
		assertTrue(piece.isValidMove(new Move(0, 0, 7, 7), board));
	}
	
	@Test
	public final void cantMoveElse() throws Exception {
		board.set(piece, 4, 4);
		
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (r == 4 && c != 4 
						|| r != 4 && c == 4) {
					assertTrue(piece.isValidMove(new Move(4, 4, r, c), 
							board));
				}
			}
		}
		
		assertFalse(piece.isValidMove(new Move(4, 4, 5, 6), board));
		assertFalse(piece.isValidMove(new Move(4, 4, 2, 3), board));
	}	
	
	@Test
	public final void cannotMoveIfWhitePieceInTheWay() throws Throwable { 
		board.set(piece, 7, 0);
	 	board.set(new Pawn(Player.WHITE), 5, 0);
		assertFalse(piece.isValidMove(new Move(7, 0, 4, 0), board));
	}
	
	@Test
	public final void cannotMoveIfBlackPieceInTheWay() throws Throwable { 
		board.set(piece, 7, 0);
	 	board.set(new Pawn(Player.BLACK), 5, 0);
		assertFalse(piece.isValidMove(new Move(7, 0, 4, 0), board));
	}
	
	@Test
	public final void cannotMoveIfPieceInTheWayDiag1() throws Throwable { 
		board.set(piece, 7, 0);
	 	board.set(new Pawn(Player.BLACK), 5, 2);
		assertFalse(piece.isValidMove(new Move(7, 0, 4, 3), board));
	}
	
	@Test
	public final void cannotMoveIfPieceInTheWayDiag2() throws Throwable { 
		board.set(piece, 3, 5);
	 	board.set(new Pawn(Player.WHITE), 5, 3);
		assertFalse(piece.isValidMove(new Move(3, 5, 7, 1), board));
	}

}
