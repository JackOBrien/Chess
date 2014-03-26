package view.colors;

import java.awt.Color;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Gray color palate for the chess GUI.
 *
 * @author John O'Brien
 * @version Mar 19, 2014
 *******************************************************************/
public final class GrayPalette {
	
	/** The color for the light spaces on the board. */
	public static final Color LIGHT = new Color(179, 179, 179);
	
	/** The color for the dark spaces on the board. */
	public static final Color DARK = new Color(46, 46, 46);
	
	/** The color used for pieces that are selected. */
	public static final Color SELECTED = new Color(255, 255, 255);
	
	/** The color for highlighted squares such as valid moves. */
	public static final Color HIGHLIGHTED = new Color(141, 141, 141);
	
	/** The background color for the promotion options. */
	public static final Color PROMOTION = new Color(160, 160, 160);
	
	/** An accent color used for the menu bar and promotion dialog. */
	public static final Color ACCENT = new Color(12, 12, 12);
	
	/****************************************************************
	 * Constructor for BoardColors.
	 ***************************************************************/
	private GrayPalette() {	}
}
