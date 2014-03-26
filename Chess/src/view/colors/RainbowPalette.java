package view.colors;

import java.awt.Color;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Rainbow color palate for the chess GUI.
 *
 * @author John O'Brien
 * @version Mar 19, 2014
 *******************************************************************/
public final class RainbowPalette {
	
	/** The color for the light spaces on the board. */
	public static final Color LIGHT = new Color(255, 165, 23);
	
	/** The color for the dark spaces on the board. */
	public static final Color DARK = new Color(0, 30, 255);
	
	/** The color used for pieces that are selected. */
	public static final Color SELECTED = new Color(252, 255, 0);
	
	/** The color for highlighted squares such as valid moves. */
	public static final Color HIGHLIGHTED = new Color(28, 230, 33);
	
	/** The background color for the promotion options. */
	public static final Color PROMOTION = new Color(255, 23, 236);
	
	/** An accent color used for the menu bar and promotion dialog. */
	public static final Color ACCENT = new Color(213, 0, 0);
	
	/****************************************************************
	 * Constructor for BoardColors.
	 ***************************************************************/
	private RainbowPalette() {	}
}
