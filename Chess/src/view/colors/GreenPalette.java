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
public final class GreenPalette {
	
	/** The color for the light spaces on the board. */
	public static final Color LIGHT = new Color(204, 200, 178);
	
	/** The color for the dark spaces on the board. */
	public static final Color DARK = new Color(32, 56, 33);
	
	/** The color used for pieces that are selected. */
	public static final Color SELECTED = new Color(1, 74, 25);
	
	/** The color for highlighted squares such as valid moves. */
	public static final Color HIGHLIGHTED = new Color(2, 161, 66);
	
	/** The background color for the promotion options. */
	public static final Color PROMOTION = new Color(160, 160, 155);
	
	/** An accent color used for the menu bar and promotion dialog. */
	public static final Color ACCENT = new Color(19, 45, 22);
	
	/****************************************************************
	 * Constructor for BoardColors.
	 ***************************************************************/
	private GreenPalette() {	}
}
