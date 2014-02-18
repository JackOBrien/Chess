package model;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class ChessBoardTest {

	private IChessBoard empty;
	
	private IChessBoard full;
	
	@Before
	public void makeBoard(){
		empty = new ChessBoard(8, false);
		full = new ChessBoard(8, true);
	}
	
	@Test
	public void numRows() throws Exception {
		assertEquals(8, empty.numRows());
	}
	
	@Test
	public void numColumns() throws Exception {
		assertEquals(8, empty.numColumns());
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_NegitiveRow() {
		empty.pieceAt(-1, 0);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_NegitiveColumn() {
		empty.pieceAt(0, -1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_NegitiveBoth() {
		empty.pieceAt(-1, -1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_TooHighRow() {
		empty.pieceAt(8, 0);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_TooHighColumn() {
		empty.pieceAt(0, 8);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_TooHighBoth() {
		empty.pieceAt(8, 8);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_WayTooLow() {
		empty.pieceAt(3, -3000);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void pieceAtThrowsException_WayTooHigh() {
		empty.pieceAt(32135, 2);
	}
	
	@Test
	public void setPlacesWhitePiece_1() throws Exception {
		empty.set(new Pawn(Player.WHITE), 2, 3);
		assertTrue(empty.pieceAt(2,  3) instanceof Pawn);
	}
	
	@Test
	public void setPlacesWhitePiece_2() throws Exception {
		empty.set(new Rook(Player.WHITE), 5, 7);
		assertTrue(empty.pieceAt(5,  7) instanceof Rook);
	}
	
	@Test
	public void setPlacesBlackPiece_1() throws Exception {
		empty.set(new Pawn(Player.WHITE), 2, 2);
		assertTrue(empty.pieceAt(2,  2) instanceof Pawn);
	}
	
	@Test
	public void setPlacesBlackPiece_2() throws Exception {
		empty.set(new Rook(Player.WHITE), 5, 0);
		assertTrue(empty.pieceAt(5,  0) instanceof Rook);
	}
	
	@Test
	public void setMayPlaceNull() throws Exception {
		empty.set(new Rook(Player.WHITE), 5, 0);
		assertTrue(empty.pieceAt(5,  0) instanceof Rook);
		
		empty.set(null, 5, 0);
		assertTrue(empty.pieceAt(5,  5) == null);
	}
	
	@Test
	public void whitesTurnFromStart() throws Exception {
		assertEquals(Player.WHITE, empty.getCurrentPlayer());
	}
	
	@Test
	public void turnUpdates() throws Exception {
		assertEquals(Player.WHITE, empty.getCurrentPlayer());
		empty.set(new Pawn(Player.BLACK), 1, 1);
		empty.move(new Move(1, 1, 1, 2));
		assertEquals(Player.BLACK, empty.getCurrentPlayer());
	}
	
	@Test
	public void zeroMovesFromStart() throws Exception {
		assertEquals(0, empty.getNumMoves());
	}
	
	@Test
	public void numMovesUpdates() throws Exception {
		assertEquals(0, full.getNumMoves());
		full.move(new Move(1, 1, 2, 1));
		assertEquals(1, full.getNumMoves());
		full.move(new Move(1, 1, 3, 1));
		assertEquals(2, full.getNumMoves());
	}
	
	@Test
	public void canFindWhiteKing() throws Exception {
		assertEquals(7, full.findKing(Player.WHITE)[0]);
		assertEquals(4, full.findKing(Player.WHITE)[1]);
		full.move(new Move(7, 4, 5, 5));
		assertEquals(5, full.findKing(Player.WHITE)[0]);
		assertEquals(5, full.findKing(Player.WHITE)[1]);
	}
	
	@Test
	public void canFindBlackKing() throws Exception {
		assertEquals(0, full.findKing(Player.BLACK)[0]);
		assertEquals(4, full.findKing(Player.BLACK)[1]);
		full.move(new Move(0, 4, 5, 5));
		assertEquals(5, full.findKing(Player.BLACK)[0]);
		assertEquals(5, full.findKing(Player.BLACK)[1]);
	}

}
