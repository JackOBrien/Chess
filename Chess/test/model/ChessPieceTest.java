package model;

import static org.junit.Assert.*;
import model.ChessBoard;
import model.IChessPiece;
import model.Move;
import model.Player;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@code ChessPiece} class
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
   public void makeBoard() {
      // Don't put any pieces on the board.
      board = new ChessBoard(BOARD_SIZE, false);
      piece = make();
   }

   protected IChessPiece make() {
      return make(Player.WHITE);
   }

   protected abstract IChessPiece make(Player p);

   protected abstract Move getValidMove(int row, int col);

   @Test
   public void complainsIfTargetOccupiedBySamePlayer() throws Throwable {
      Move move = getValidMove(2, 4);
      board.set(make(), move.toRow, move.toColumn);
      board.set(piece, move.fromRow, move.fromColumn);
      assertFalse("ChessPiece Test 3", piece.isValidMove(move, board));
   }

   @Test
   public void canCaputre() throws Throwable {
      Move move = getValidMove(2, 4);
      board.set(make(piece.player().next()), move.toRow, move.toColumn);
      board.set(piece, move.fromRow, move.fromColumn);
      assertTrue("ChessPiece Test 4", piece.isValidMove(move, board));
   }


}
