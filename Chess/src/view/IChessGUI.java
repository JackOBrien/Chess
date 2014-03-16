package view;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * 
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public interface IChessGUI {

	/****************************************************************
	 * Helper method to return the image that matches the
	 * described chess piece.
	 * 
	 * @param type the name of the piece, i.e. "King".
	 * @param white tells if the piece is white or not.
	 * @return  image corresponding to the piece described.
	 ***************************************************************/
	ImageIcon imageFinder(String type, boolean white);

	/****************************************************************
	 * Changes the image of the given cell to the image of the
	 * piece described
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
	 * @param row
	 * @param col TODO
	 * @param white
	 * @return
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

}
