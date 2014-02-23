package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Objects implementing this interface represent the state of a chess game.
 *
 * @author Zachary Kurmas
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 24, 2014
 *******************************************************************/
public interface IChessModel {

   /*****************************************************************
    * Returns whether the game is complete. In other words, checks if
    * either player is in Check Mate.
    *
    * @return true if complete, false otherwise.
    ****************************************************************/
   boolean isComplete();

   /*****************************************************************
    * Returns whether the piece at location [move.fromRow, move.fromColumn]
    * is allowed to move to location [move.fromRow, move.fromColumn].
    *
    * @param move a Move object describing the move to be made.
    * @return true if the proposed move is valid, false otherwise.
    ****************************************************************/
   boolean isValidMove(Move move);

   /*****************************************************************
    * Moves the piece from location [move.fromRow, move.fromColumn] to
    * location [move.fromRow,move.fromColumn].
    *
    * @param move a Move object describing the move to be made.
    ****************************************************************/
   void move(Move move);

   /*****************************************************************
    * Report whether the current player is in check.
    *
    * @return true if the current player is in check, false otherwise.
    ****************************************************************/
   boolean inCheck();

   /*****************************************************************
    * Return the current player.
    *
    * @return the current player
    ****************************************************************/
   Player currentPlayer();
      
   /*****************************************************************
    * Return the ChessPiece object at location [row, column].
    *
    * @param row the row coordinate
    * @param column the column coordinate
    * @return the {@code ChessPiece} object at location {@code [row, column]}.
    ****************************************************************/
   IChessPiece pieceAt(int row, int column);

   /****************************************************************
    * Returns the board used by the model.
    * Used for testing
    * 
    * @return game board used by the model.
    ***************************************************************/
   IChessBoard getBoard();

}
