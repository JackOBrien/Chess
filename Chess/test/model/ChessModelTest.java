package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.*;
import org.junit.Before;
import org.junit.Test;

public class ChessModelTest {

	private IChessModel model;
	
	@Before
	public void makeModel(){
		model = new ChessModel();
	}
	
	@Test
	public void moveWorks() throws Exception {
		assertTrue(model.pieceAt(6, 0).type().equals("Pawn"));
		model.move(new Move(6, 0, 4, 0));
		assertTrue(model.pieceAt(6, 0) == null);
		assertTrue(model.pieceAt(4, 0).type().equals("Pawn"));
	}
	
	@Test
	public void whiteFirst() throws Exception {
		assertTrue(model.currentPlayer().equals(Player.WHITE));
	}
	
	@Test
	public void currentPlayerUpdates() throws Exception {
		model.move(new Move(6, 2, 5, 2));
		assertTrue(model.currentPlayer().equals(Player.BLACK));
		model.move(new Move(1, 2, 2, 2));
		assertTrue(model.currentPlayer().equals(Player.WHITE));
	}
	
	@Test
	public void inCheckWhite() throws Exception {
		model.move(new Move(6, 6, 4, 6));
		model.move(new Move(1, 4, 2, 4));
		model.move(new Move(6, 5, 5, 5));
		model.move(new Move(0, 3, 4, 7));
		assertTrue(model.inCheck());
		assertTrue(model.isComplete());
	}
	
	@Test
	public void inCheckBlack() throws Exception {
		model.move(new Move(0, 4, 4, 2));
		model.move(new Move(7, 7, 4, 7));
		assertTrue(model.inCheck());
		assertFalse(model.isComplete());
	}
	
	@Test
	public void notInCheck() throws Exception {
		assertFalse(model.inCheck());
	}
	
	@Test
	public void inCheckQueen() throws Exception {
		model.move(new Move(7, 7, 7, 7));
		model.move(new Move(0, 4, 4, 2));
		model.move(new Move(7, 3, 3, 3));
		assertTrue(model.inCheck());
		assertFalse(model.inCheck() && model.isComplete());
	}
	
	@Test
	public void notInCheckKnight() throws Exception {
		model.move(new Move(6, 4, 5, 4));
		model.move(new Move(0, 6, 2, 5));
		model.move(new Move(7, 4, 6, 4));
		model.move(new Move(2, 5, 4, 4));
		assertFalse(model.inCheck() && model.isComplete());
	}
	
	
	@Test
	public void isNotCompleteAtStart() throws Exception {
		assertFalse(model.inCheck() && model.isComplete());
	}
	
	@Test
	public void isComplete_1() throws Exception {
		model.move(new Move(0, 4, 3, 0));
		model.move(new Move(7, 3, 3, 1));
		model.move(new Move(7, 4, 4, 2));		
		assertTrue(model.inCheck() && model.isComplete());
	}
	
	@Test
	public void isComplete_2() throws Exception {
		model.move(new Move(7, 4, 3, 7));
		model.move(new Move(0, 3, 3, 6));
		model.move(new Move(1, 0, 2, 5));		
		assertTrue(model.inCheck() && model.isComplete());
	}
	
	@Test
	public void isNotCompleteWhite() throws Exception {
		model.move(new Move(7, 4, 3, 7));
		model.move(new Move(0, 3, 3, 6));
		model.move(new Move(1, 0, 2, 5));	
		model.move(new Move(7, 7, 5, 6));
		assertFalse(model.inCheck() && model.isComplete());
	}
	
	@Test
	public void isNotCompleteBlack() throws Exception {
		model.move(new Move(6, 1, 6, 2)); //w
		model.move(new Move(0, 4, 3, 7)); //b
		model.move(new Move(7, 3, 3, 6)); //w
		model.move(new Move(0, 0, 5, 6)); //b
		model.move(new Move(6, 2, 2, 5)); //w
		assertFalse(model.inCheck() && model.isComplete());
	}
	
	@Test
	public void pieceAtWorks() throws Exception {
		assertTrue(model.pieceAt(0, 0) instanceof Rook);
		assertTrue(model.pieceAt(6, 5) instanceof Pawn);
	}
	
	@Test
	public void pieceAtWorksAfterMove() throws Exception {
		assertTrue(model.pieceAt(6, 5) instanceof Pawn);
		model.move(new Move(6, 5, 4, 5));
		assertTrue(model.pieceAt(4, 5) instanceof Pawn);
	}
	
	@Test
	public void isValidMove_1() throws Exception {
		model.isValidMove(new Move(1, 0, 3, 0));
	}
	
	@Test
	public void isValidMove_2() throws Exception {
		model.move(new Move(6, 4, 4, 4));
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 4, 5, 6));
		model.move(new Move(0, 4, 3, 6));
		assertTrue(model.isValidMove(new Move(5, 6, 5, 5)));
		assertFalse(model.isValidMove(new Move(5, 6, 4, 6)));

	}
	
	@Test
	public void isValidMove_3() throws Exception {
		model.move(new Move(6, 4, 4, 4));
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 4, 5, 6));
		model.move(new Move(0, 4, 3, 6));
		model.move(new Move(0, 1, 0, 2));
		assertTrue(model.isValidMove(new Move(3, 6, 2, 5)));
		assertFalse(model.isValidMove(new Move(3, 6, 4, 6)));
	}
	
	@Test
	public void isValidMove_4() throws Exception {
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 3, 2, 4));
		model.move(new Move(0, 4, 1, 4));
		assertFalse(model.isValidMove(new Move(1, 4, 0, 4)));
		System.out.println(model.pieceAt(0, 4));
		assertTrue(model.isValidMove(new Move(1, 4, 2, 4)));
	}
	
	@Test
	public void isValidMove_5() throws Exception {
		model.move(new Move(1, 4, 3, 4));
		model.move(new Move(7, 3, 2, 4));
		model.move(new Move(0, 4, 1, 4));
		assertFalse(model.isValidMove(new Move(1, 3, 2, 3)));
		assertTrue(model.isValidMove(new Move(1, 3, 2, 4)));
	}
}
