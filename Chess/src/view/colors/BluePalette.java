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
public final class BluePalette {
	
	/** The color for the light spaces on the board. */
	public static final Color LIGHT = new Color(210, 197, 176);
	
	/** The color for the dark spaces on the board. */
	public static final Color DARK = new Color(36, 47, 64);
	
	/** The color used for pieces that are selected. */
	public static final Color SELECTED = new Color(6, 47, 152);
	
	/** The color for highlighted squares such as valid moves. */
	public static final Color HIGHLIGHTED = new Color(1, 146, 155);
	
	/** The background color for the promotion options. */
	public static final Color PROMOTION = new Color(160, 160, 155);
	
	/** An accent color used for the menu bar and promotion dialog. */
	public static final Color ACCENT = new Color(32, 32, 60);
	
	/****************************************************************
	 * Constructor for BoardColors.
	 ***************************************************************/
	private BluePalette() {	}
}
