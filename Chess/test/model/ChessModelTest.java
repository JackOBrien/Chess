package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ChessModelTest {

	private IChessModel model;
	
	@Before
	public final void makeModel() {
		model = new ChessModel();
	}
	
	@Test
	public final void moveWorks() throws Exception {
		assertTrue(model.pieceAt(6, 0).type().equals("Pawn"));
		model.move(new Move(6, 0, 4, 0));
		assertNull(model.pieceAt(6, 0));
		assertTrue(model.pieceAt(4, 0).type().equals("Pawn"));
	}
	
	@Test
	public final void whiteFirst() throws Exception {
		assertTrue(model.currentPlayer().equals(Player.WHITE));
	}
	
	@Test
	public final void currentPlayerUpdates() throws Exception {
		model.move(new Move(6, 2, 5, 2));
		assertTrue(model.currentPlayer().equals(Player.BLACK));
		model.move(new Move(1, 2, 2, 2));
		assertTrue(model.currentPlayer().equals(Player.WHITE));
	}
	
	@Test
	public final void inCheckWhite() throws Exception {
		model.move(new Move(6, 6, 4, 6));
		model.move(new Move(1, 4, 2, 4));
		model.move(new Move(6, 5, 5, 5));
		model.move(new Move(0, 3, 4, 7));
		assertTrue(model.inCheck());
		assertTrue(model.isComplete());
	}
	
	@Test
	public final void inCheckBlack() throws Exception {
		model.move(new Move(6, 0, 5, 0));
		model.move(new Move(0, 4, 4, 2));
		model.move(new Move(7, 7, 4, 7));
		assertTrue(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public final void notInCheck() throws Exception {
		assertFalse(model.inCheck());
	}
	
	@Test
	public final void inCheckQueen() throws Exception {
		model.move(new Move(7, 7, 7, 7));
		model.move(new Move(0, 4, 4, 2));
		model.move(new Move(7, 3, 3, 3));
		assertTrue(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public final void notInCheckKnight() throws Exception {
		model.move(new Move(6, 4, 5, 4));
		model.move(new Move(0, 6, 2, 5));
		model.move(new Move(7, 4, 6, 4));
		model.move(new Move(2, 5, 4, 4));
		assertFalse(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	
	@Test
	public final void isNotCompleteAtStart() throws Exception {
		assertFalse(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public final void isComplete1() throws Exception {
		model.move(new Move(0, 4, 3, 0));
		model.move(new Move(7, 3, 3, 1));
		model.move(new Move(7, 4, 4, 2));		
		assertTrue(model.inCheck());
		assertTrue(model.isComplete());
	}
	
	@Test
	public final void isComplete2() throws Exception {
		model.move(new Move(7, 4, 3, 7));
		model.move(new Move(0, 3, 3, 6));
		model.move(new Move(1, 0, 2, 5));		
		assertTrue(model.inCheck());
		assertTrue(model.isComplete());
	}
	
	@Test
	public final void isNotCompleteWhite() throws Exception {
		model.move(new Move(7, 4, 3, 7));
		model.move(new Move(0, 3, 3, 6));
		model.move(new Move(1, 0, 2, 5));	
		model.move(new Move(7, 7, 5, 6));
		assertTrue(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public final void isNotCompleteBlack() throws Exception {
		model.move(new Move(6, 1, 6, 2)); //w
		model.move(new Move(0, 4, 3, 7)); //b
		model.move(new Move(7, 3, 3, 6)); //w
		model.move(new Move(0, 0, 5, 6)); //b
		model.move(new Move(6, 2, 2, 5)); //w
		assertTrue(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public final void pieceAtWorks() throws Exception {
		assertTrue(model.pieceAt(0, 0) instanceof Rook);
		assertTrue(model.pieceAt(6, 5) instanceof Pawn);
	}
	
	@Test
	public final void pieceAtWorksAfterMove() throws Exception {
		assertTrue(model.pieceAt(6, 5) instanceof Pawn);
		model.move(new Move(6, 5, 4, 5));
		assertTrue(model.pieceAt(4, 5) instanceof Pawn);
	}
	
	@Test
	public final void isValidMove1() throws Exception {
		model.isValidMove(new Move(1, 0, 3, 0));
	}
	
	@Test
	public final void isValidMove2() throws Exception {
		model.move(new Move(6, 4, 4, 4));
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 4, 5, 6));
		model.move(new Move(0, 4, 3, 6));
		assertTrue(model.isValidMove(new Move(5, 6, 5, 5)));
		assertFalse(model.isValidMove(new Move(5, 6, 4, 6)));

	}
	
	@Test
	public final void isValidMove3() throws Exception {
		model.move(new Move(6, 4, 4, 4));
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 4, 5, 6));
		model.move(new Move(0, 4, 3, 6));
		model.move(new Move(0, 1, 0, 2));
		assertTrue(model.isValidMove(new Move(3, 6, 2, 5)));
		assertFalse(model.isValidMove(new Move(3, 6, 4, 6)));
	}
	
	@Test
	public final void isValidMove4() throws Exception {
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 3, 2, 4));
		model.move(new Move(0, 4, 1, 4));
		assertFalse(model.isValidMove(new Move(1, 4, 0, 4)));
		assertTrue(model.isValidMove(new Move(1, 4, 2, 4)));
	}
	
	@Test
	public final void isValidMove5() throws Exception {
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 3, 2, 4));
		model.move(new Move(0, 4, 1, 4));
		assertFalse(model.isValidMove(new Move(1, 3, 2, 3)));
		assertTrue(model.isValidMove(new Move(1, 3, 2, 4)));
	}
	
	@Test
	public final void kingsCanPutGameInCheck() throws Exception {
		model.move(new Move(7, 3, 3, 0));
		model.move(new Move(0, 4, 3, 6));
		model.move(new Move(7, 4, 3, 1));
		model.move(new Move(7, 7, 7, 7)); //change turns
		assertFalse(model.inCheck());
		assertTrue(model.isValidMove(new Move(3, 1, 4, 1)));
		model.move(new Move(3, 1, 4, 1));
		assertTrue(model.inCheck());
	}
	
	@Test
	public final void kingsCantAttackEachother() throws Exception {
		model.move(new Move(7, 4, 3, 1));
		model.move(new Move(0, 4, 3, 3));
		assertFalse(model.isValidMove(new Move(3, 1, 3, 2)));
		assertTrue(model.isValidMove(new Move(3, 1, 4, 1)));

	}
	
	@Test
	public final void stopCheckwithCheck() throws Exception {
		model.move(new Move(0, 4, 3, 0));
		model.move(new Move(7, 3, 3, 2));
		model.move(new Move(7, 4, 5, 2));
		model.move(new Move(0, 0, 2, 2));
		model.move(new Move(1, 0, 2, 0));
		model.move(new Move(6, 0, 4, 0));
		model.move(new Move(6, 7, 5, 7));
		assertTrue(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public final void nullPieceCantMove() throws Exception {
		assertFalse(model.isValidMove(new Move(4, 4, 4, 5)));
	}
	
	@Test
	public final void cantCastleInCheckQueen1() throws Exception {
		makeWayForCastle();
		
		assertTrue(model.isValidMove(new Move(0, 4, 0, 2)));
		model.move(new Move(7, 3, 5, 4));
		model.move(new Move(1, 4, 2, 3));
		assertFalse(model.isValidMove(new Move(0, 4, 0, 2)));
	}
	
	@Test
	public final void cantCastleInCheckQueen2() throws Exception {
		makeWayForCastle();
		
		assertTrue(model.isValidMove(new Move(0, 4, 0, 2)));
		model.move(new Move(7, 4, 5, 4));
		model.move(new Move(6, 7, 5, 7));
		assertTrue(model.inCheck());
		assertTrue(model.isValidMove(new Move(0, 4, 0, 2)));
	}
	
	@Test
	public final void cantCastleInCheckQueen3() throws Exception {
		makeWayForCastle();
		
		assertTrue(model.isValidMove(new Move(0, 4, 0, 2)));
		model.move(new Move(7, 4, 5, 4));
		model.move(new Move(6, 7, 5, 7));
		assertTrue(model.inCheck());
		assertTrue(model.isValidMove(new Move(0, 4, 0, 3)));
	}
	
	@Test
	public final void cantCastleInCheckQueen4() throws Exception {
		makeWayForCastle();
		
		assertTrue(model.isValidMove(new Move(0, 4, 0, 2)));
		model.move(new Move(7, 4, 5, 4));
		model.move(new Move(0, 4, 0, 3));
		assertTrue(model.inCheck());
		assertFalse(model.isValidMove(new Move(0, 3, 0, 1)));
	}
	
	private void makeWayForCastle() {
		model.move(new Move(0, 1, 3, 1));
		model.move(new Move(0, 2, 3, 2));
		model.move(new Move(0, 3, 3, 3));
		model.move(new Move(0, 5, 3, 5));
		model.move(new Move(0, 6, 3, 6));
	}
	
	@Test
	public final void checkSize() throws Exception {
		assertEquals(model.numRows(), 8);
		assertEquals(model.numColumns(), 8);
	}
	
	@Test
	public final void testFromChecktoCheck() throws Exception {
		model.move(new Move(7, 4, 4, 0));
		model.move(new Move(0, 4, 3, 5));
		model.move(new Move(7, 0, 4, 1));
		model.move(new Move(1, 1, 3, 1));
		assertTrue(model.isValidMove(new Move(4, 1, 3, 1)));
	}
}
