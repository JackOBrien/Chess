package model;

import static org.junit.Assert.*;
import model.*;


import model.Knight;
import model.Pawn;

import org.junit.Test;

public class KnightTest extends ChessPieceTest {

	@Override
	protected IChessPiece make(Player p) {
		return new Knight(p);
	}

	@Override
	protected Move getValidMove(int row, int col) {
		int newRow = row + 2;
		int newCol = col + 1;
		
		return new Move(row, col, newRow, newCol);
	}
	
	@Override 
	public void canCaputre() throws Throwable {
		board.set(piece, 4, 4);
		board.set(new Pawn(piece.player().next()), 2, 3);
		assertTrue(piece.isValidMove(new Move(4, 4, 2, 3), board));
	}
	
	@Test
	public void canMoveOverPieces() throws Exception {
		board.set(piece, 4, 4);
		board.set(new Pawn(Player.WHITE), 3, 3);
		board.set(new Pawn(Player.BLACK), 3, 4);
		board.set(new Pawn(Player.WHITE), 3, 5);
		assertTrue(piece.isValidMove(new Move(4, 4, 2, 3), board));
	}
	
	@Test
	public void cantMoveInRow() throws Exception {
		board.set(piece, 4, 4);
		assertFalse(piece.isValidMove(new Move(4, 4, 4, 3), board));
	}

	@Test
	public void cantMoveInCol() throws Exception {
		board.set(piece, 4, 4);
		assertFalse(piece.isValidMove(new Move(4, 4, 3, 4), board));
	}
}
