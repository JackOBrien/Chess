package view;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Interface for the user interface of the chess game.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public interface IChessUI {

	/****************************************************************
	 * Changes the image of the given cell to the image of the
	 * piece described.
	 * 
	 * @param row row location of the piece.
	 * @param col column location of the piece.
	 * @param type the name of the piece, i.e. "King".
	 * @param white tells if the piece is white or not.
	 ***************************************************************/
	void changeImage(int row, int col, String type, boolean white);

	/****************************************************************
	 * Adds an ActionListner to all buttons to handle the moving
	 * of game pieces.
	 * 
	 * @param mh the ActionListner to handle moves.
	 ***************************************************************/
	void setMoveHandler(ActionListener mh);
	
	/****************************************************************
	 * Adds an ActionListner to the different promotion options
	 * used when a pawn promotes.
	 * 
	 * @param ph the ActionListner to handle promotion.
	 ***************************************************************/
	void setPromotionHandler(ActionListener ph);

	/****************************************************************
	 * Changes the background color of the piece at the given location
	 * to be the color for selected pieces.
	 * 
	 * @param row row location of the piece.
	 * @param col column location of the piece.
	 ***************************************************************/
	void setSelected(int row, int col);

	/****************************************************************
	 * Changes the background color of the piece at the given location
	 * to be the color for highlighted pieces.
	 * 
	 * @param row row location of the piece.
	 * @param col column location of the piece.
	 ***************************************************************/
	void setHighlighted(int row, int col);

	/****************************************************************
	 * Set the color of all buttons to their regular background color.
	 ***************************************************************/
	void deselectAll();

	/****************************************************************
	 * Promotes the pawn at the given location.
	 * 
	 * @param row row location.
	 * @param col column location.
	 * @param white tells if the piece is white.
	 ***************************************************************/
	void pawnPromotion(int row, int col, boolean white);

	/****************************************************************
	 * Display warning that the given player is in check.
	 * 
	 * @param white tells if the player in check is white or not.
	 ***************************************************************/
	void gameInCheck(boolean white);

	/****************************************************************
	 * Displays when the game is over. Asks the user if they
	 * want to play again. If not, the game exits.
	 * 
	 * @param white tells if white won or not.
	 ***************************************************************/
	void gameOver(boolean white);

	/****************************************************************
	 * Starts the clock of the describes player and stops the clock
	 * of the other player.
	 * 
	 * @param white describes if the player is white or not.
	 ***************************************************************/
	void startTimer(boolean white);

	/****************************************************************
	 * Stops both player's game timers.
	 ***************************************************************/
	void stopTimers();

	/****************************************************************
	 * Adds focus listener to the frame to stop all timers when out
	 * of focus.
	 * 
	 * @param fh focus listener to stop timers when lost.
	 ***************************************************************/
	void setFocusHandler(FocusListener fh);

}
