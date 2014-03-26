package view.colors;

import java.awt.Color;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Red color palate for the chess GUI.
 *
 * @author John O'Brien
 * @version Mar 19, 2014
 *******************************************************************/
public final class RedPalette {
	
	/** The color for the light spaces on the board. */
	public static final Color LIGHT = new Color(196, 177, 143);
	
	/** The color for the dark spaces on the board. */
	public static final Color DARK = new Color(49, 46, 40);
	
	/** The color used for pieces that are selected. */
	public static final Color SELECTED = new Color(146, 0, 17);
	
	/** The color for highlighted squares such as valid moves. */
	public static final Color HIGHLIGHTED = new Color(214, 55, 57);
	
	/** The background color for the promotion options. */
	public static final Color PROMOTION = new Color(160, 160, 155);
	
	/** An accent color used for the menu bar and promotion dialog. */
	public static final Color ACCENT = new Color(53, 28, 28);
	
	/****************************************************************
	 * Constructor for BoardColors.
	 ***************************************************************/
	private RedPalette() {	}
}
