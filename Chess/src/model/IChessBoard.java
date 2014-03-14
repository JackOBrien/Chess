package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * This interface provides a uniform view of a Chess board to 
 * the IChessPiece interface.
 *
 * @author Zachary Kurmas
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 24, 2014
 *******************************************************************/
public interface IChessBoard {

   /*****************************************************************
    * Returns the number of rows on the chess board .
    * (a standard game has 8 rows)
    *
    * @return the number of rows on the chess board.
    ****************************************************************/
   int numRows();

   /*****************************************************************
    * Returns the number of columns on the chess board .
    * (a standard game has 8 columns)
    *
    * @return the number of columns on the chess board.
    ****************************************************************/
   int numColumns();

   /*****************************************************************
    * Return the ChessPiece object at location [row, column].
    *
    * @param row the row coordinate
    * @param column the column coordinate
    * @return the {@code ChessPiece} object at location {@code [row, column]}.
    ****************************************************************/
   IChessPiece pieceAt(int row, int column);
   
    /****************************************************************
    * Make the specified move. The move must be validated 
    * (i.e., this method will not verify that the move is legal).
    * Switches turns.
    *
    * @param move the move to be made.
    ****************************************************************/
   void move(Move move);

   /*****************************************************************
    * Place piece at [row, column].
    *
    * @param piece  the piece
    * @param row    the row
    * @param column the column
    ****************************************************************/
   void set(IChessPiece piece, int row, int column);


   /*****************************************************************
    * Remove the piece from [row, column].
    *
    * @param row    the row
    * @param column the column
    ****************************************************************/
   void unset(int row, int column);

   /****************************************************************
    * Returns the number of moves that have been made in the game.
    * 
    * @return number of moves made in this game
    ***************************************************************/
   int getNumMoves();

   /****************************************************************
	* Returns the player who's turn it is currently.
	* 
	* @return the player who's turn it is
	***************************************************************/
   Player getCurrentPlayer();

   /****************************************************************
    * Returns the location of the requested player's king.
    * 
    * @param p player who's king's location is being requested
    * @return the location of Player p's king
    ***************************************************************/
   int[] findKing(Player p);
   
   /****************************************************************
    * Returns a deep copy of this boards 2D array of pieces.
    * 
    * @return the cloned board
    ***************************************************************/
   IChessPiece[][] clone();
}
