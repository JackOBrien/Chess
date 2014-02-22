package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@code ChessPiece} class.
 *
 * @author Zachary Kurmas
 */
// Created  12/8/12 at 9:28 PM
// (C) Zachary Kurmas 2012

public abstract class ChessPieceTest {

   private static final int BOARD_SIZE = 8;

   protected ChessBoard board;
   protected IChessPiece piece;

   @Before
   public final void makeBoard() {
      // Don't put any pieces on the board.
      board = new ChessBoard(BOARD_SIZE, false);
      piece = make();
   }

   protected final IChessPiece make() {
      return make(Player.WHITE);
   }

   protected abstract IChessPiece make(Player p);

   protected abstract Move getValidMove(int row, int col);

   @Test
   public final void complainsIfTargetOccupiedBySamePlayer() throws Throwable {
      Move move = getValidMove(2, 4);
      board.set(make(), move.getToRow(), move.getToColumn());
      board.set(piece, move.getFromRow(), move.getFromColumn());
      assertFalse("ChessPiece Test 3", piece.isValidMove(move, board));
   }

   @Test
   //says need final modifier but then in Knight test says to take off final 
   //modifier.
   public void canCaputre() throws Throwable {
      Move m = getValidMove(2, 4);
      board.set(make(piece.player().next()), m.getToRow(), m.getToColumn());
      board.set(piece, m.getFromRow(), m.getFromColumn());
      assertTrue("ChessPiece Test 4", piece.isValidMove(m, board));
   }
   
   @Test
   public final void hasATypeString() throws Throwable {
	   assertNotNull(piece.type());
   }

   @Test (expected = IllegalArgumentException.class)
public final void complainsIfImproperFromLocation() throws Throwable {
	   Move m = new Move(4, 4, 4, 5);
	   piece.isValidMove(m, board);
   }

}
