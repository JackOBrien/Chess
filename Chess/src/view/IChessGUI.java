package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

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
	 * @param pBoard TODO
	 ***************************************************************/
	void initializeBoard(JButton[][] pBoard);

	/****************************************************************
	 * Changes the background color of the piece at the given location
	 * 
	 * @param row row location of the piece.
	 * @param col column location of the piece.
	 * @param c the Color to change the background to.
	 ***************************************************************/
	void changeColor(int row, int col, Color c);

	/****************************************************************
	 * Helper method to return the image that matches the
	 * described chess piece.
	 * 
	 * @param type the name of the piece, i.e. "King".
	 * @param white tells if the piece is white or not.
	 * @return  image corresponding to the piece described.
	 ***************************************************************/
	ImageIcon imageFinder(String type, boolean white);

}
