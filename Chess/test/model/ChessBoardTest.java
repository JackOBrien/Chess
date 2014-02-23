package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public class ChessBoardTest {

	private IChessBoard empty;
	
	private IChessBoard full;
	
	@Before
	public final void makeBoard() {
		empty = new ChessBoard(8, false);
		full = new ChessBoard(8, true);
	}
	
	@Test
	public final void numRows() throws Exception {
		assertEquals(8, empty.numRows());
	}
	
	@Test
	public final void numColumns() throws Exception {
		assertEquals(8, empty.numColumns());
	}
	
	@Test
	public final void setPlacesWhitePiece1() throws Exception {
		empty.set(new Pawn(Player.WHITE), 2, 3);
		assertTrue(empty.pieceAt(2,  3) instanceof Pawn);
	}
	
	@Test
	public final void setPlacesWhitePiece2() throws Exception {
		empty.set(new Rook(Player.WHITE), 5, 7);
		assertTrue(empty.pieceAt(5,  7) instanceof Rook);
	}
	
	@Test
	public final void setPlacesBlackPiece1() throws Exception {
		empty.set(new Pawn(Player.WHITE), 2, 2);
		assertTrue(empty.pieceAt(2,  2) instanceof Pawn);
	}
	
	@Test
	public final void setPlacesBlackPiece2() throws Exception {
		empty.set(new Rook(Player.WHITE), 5, 0);
		assertTrue(empty.pieceAt(5,  0) instanceof Rook);
	}
	
	@Test
	public final void setMayPlaceNull() throws Exception {
		empty.set(new Rook(Player.WHITE), 5, 0);
		assertTrue(empty.pieceAt(5,  0) instanceof Rook);
		
		empty.set(null, 5, 0);
		assertNull(empty.pieceAt(5,  5));
	}
	
	@Test
	public final void whitesTurnFromStart() throws Exception {
		assertEquals(Player.WHITE, empty.getCurrentPlayer());
	}
	
	@Test
	public final void turnUpdates() throws Exception {
		assertEquals(Player.WHITE, empty.getCurrentPlayer());
		empty.set(new Pawn(Player.BLACK), 1, 1);
		empty.move(new Move(1, 1, 1, 2));
		assertEquals(Player.BLACK, empty.getCurrentPlayer());
	}
	
	@Test
	public final void zeroMovesFromStart() throws Exception {
		assertEquals(0, empty.getNumMoves());
	}
	
	@Test
	public final void numMovesUpdates() throws Exception {
		assertEquals(0, full.getNumMoves());
		full.move(new Move(1, 1, 2, 1));
		assertEquals(1, full.getNumMoves());
		full.move(new Move(1, 1, 3, 1));
		assertEquals(2, full.getNumMoves());
	}
	
	@Test
	public final void canFindWhiteKing() throws Exception {
		assertEquals(7, full.findKing(Player.WHITE)[0]);
		assertEquals(4, full.findKing(Player.WHITE)[1]);
		full.move(new Move(7, 4, 5, 5));
		assertEquals(5, full.findKing(Player.WHITE)[0]);
		assertEquals(5, full.findKing(Player.WHITE)[1]);
	}
	
	@Test
	public final void canFindBlackKing() throws Exception {
		assertEquals(0, full.findKing(Player.BLACK)[0]);
		assertEquals(4, full.findKing(Player.BLACK)[1]);
		full.move(new Move(0, 4, 5, 5));
		assertEquals(5, full.findKing(Player.BLACK)[0]);
		assertEquals(5, full.findKing(Player.BLACK)[1]);
	}
	
	@Test
	public final void canUnSet() throws Exception {
		empty.set(new Pawn(Player.WHITE), 4, 4);
		empty.unset(4, 4);
		assertNull(empty.pieceAt(4, 4));
	}
	
	@Test
	public final void testCoptConstructor() throws Exception {
		IChessBoard newBoard = new ChessBoard((ChessBoard) full);
		for (int r = 0; r < newBoard.numRows(); r++) {
			for (int c = 0; c < newBoard.numColumns(); c++) {
				if (full.pieceAt(r, c) == null) {
					assertNull(newBoard.pieceAt(r, c));
					continue;
				}
				
				assertTrue(newBoard.pieceAt(r, c).is(full.pieceAt(r, c)
						.type()));
			}
		}
		
		newBoard.set(new Rook(Player.BLACK), 4, 4);
		assertNull(full.pieceAt(4, 4));
	}

}
