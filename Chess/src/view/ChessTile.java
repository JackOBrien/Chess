package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * The buttons that make up the game board.
 *
 * @author John O'Brien
 * @version Mar 26, 2014
 *******************************************************************/
public class ChessTile extends JButton {

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/** Normal state of a button. */
	public static final int NORMAL = 0;
	
	/** Button is selected. */
	public static final int SELECTED = 1;
	
	/** Button is highlighted. */
	public static final int HIGHLIGHTED = 2;
	
	/** Current state of the button. */
	private int state;
	
	/** MouseListener that will bevel buttons when interacted with. */
	private BevelOnHover mouseListener;

	/** Default background color of the button. */
	private Color defaultBG;
	
	/** Size of the button. */
	private int size;
	
	/** Tells if the button is "special highlighted". This feature is not
	 * implemented in release 3. */
	private boolean specialHighlight;
	
	/** Tells if the piece is a light or dark tile. */
	private boolean isLight;
	
	/** Tells what the "special highlight" color is of the piece. This 
	 * feature is not implemented in release 3. */
	private Color highlight;
	
	/****************************************************************
	 * Constructor for a tile on the game board.
	 * 
	 * @param pSize the size of the tile.
	 ***************************************************************/
	public ChessTile(int pSize) {
		size = pSize;
		
		state = NORMAL;
		
		// Filler colors.
		defaultBG = Color.GRAY;
		highlight = Color.LIGHT_GRAY;
		
		mouseListener = new BevelOnHover(Color.BLACK);
		specialHighlight = false;
		
		setUpButton();
	}
	
	/****************************************************************
	 * Sets the default background of the tile.
	 * 
	 * @param bg the default background of the tile.
	 ***************************************************************/
	public final void setDefaultBackground(Color bg) {
		defaultBG = bg;
		setBackground(bg);
	}
	
	/****************************************************************
	 * Sets the special background of the tile.
	 * 
	 * @param c the special background of the tile.
	 ***************************************************************/
	public void setSpecialBackground(Color c) {
		highlight = c;
	}
	
	
	/****************************************************************
	 * Sets if this tile is light or not.
	 * 
	 * @param light tells if the tile is light or not.
	 ***************************************************************/
	public void setIsLight(boolean light) {
		isLight = light;
	}
	
	
	/****************************************************************
	 * @return if this tile is light or not.
	 ***************************************************************/
	public boolean isLight() {
		return isLight;
	}
	
	/****************************************************************
	 * Tells if the tile is the given state or not.
	 * 
	 * @param pState the given state.
	 * @return true if current state matches given state.
	 ***************************************************************/
	public boolean isState(int pState) {
		return state == pState;
	}
	
	/****************************************************************
	 * Sets the current state of the tile.
	 * 
	 * @param pState the current state of the tile.
	 ***************************************************************/
	public void setState(int pState) {
		state = pState;
	}
	
	/****************************************************************
	 * Resets the tile to it's default background color.
	 ***************************************************************/
	public void resetColor() {
		if (!specialHighlight) {
			setBackground(defaultBG);
		} else {
			setBackground(highlight);
		}
		
		state = NORMAL;
	}
	
//	public void isSpecialHighlight(boolean b) {
//		specialHighlight = b;
//	}
	
	/****************************************************************
	 * Sets up a default button.
	 ***************************************************************/
	private void setUpButton() {
		setPreferredSize(new Dimension(size, size));
		setSize(new Dimension(size, size));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(mouseListener);
		setOpaque(true);
		setFocusable(false);
		
		Border line = BorderFactory.createLineBorder(Color.BLACK, 1);
		setBorder(line);
	}
}
