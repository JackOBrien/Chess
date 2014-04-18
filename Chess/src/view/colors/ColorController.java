package view.colors;

import java.awt.Color;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Allows for easy switching between color palettes. 
 *
 * @author John O'Brien
 * @version Mar 26, 2014
 *******************************************************************/
public class ColorController {

	/** The color for the light spaces on the board. */
	private Color light;
	
	/** The color for the dark spaces on the board. */
	private Color dark;
	
	/** The color used for pieces that are selected. */
	private Color selected;
	
	/** The color for highlighted squares such as valid moves. */
	private Color highlighted;
	
	/** The background color for the promotion options. */
	private Color promotion;
	
	/** An accent color used for the menu bar and promotion dialog. */
	private Color accent;
	
	/** Red color palette. */
	public static final int RED = 1;
	
	/** Blue color palette. */
	public static final int BLUE = 2;
	
	/** Rainbow color palette. */
	public static final int RAINBOW = 3;
	
	/** Gray color palette. */
	public static final int GRAY = 4;
	
	/** Green color palette. */
	public static final int GREEN = 5;
	
	/****************************************************************
	 * Default constructor for ColorController. Sets the default color to red.
	 ***************************************************************/
	public ColorController() {
		switchPalette(RED);
	}
	
	/****************************************************************
	 * Constructor that allows for setting of the initial color palette.
	 * 
	 * @param color the initial color palette.
	 ***************************************************************/
	public ColorController(final int color) {
		switchPalette(color);
	}
	
	/****************************************************************
	 * Switches the current color palette the the one given as 
	 * a parameter.
	 * 
	 * @param color the color palette to switch to.
	 ***************************************************************/
	public final void switchPalette(final int color) {
		
		switch (color) {
		case BLUE:
			light = BluePalette.LIGHT;
			dark = BluePalette.DARK;
			selected = BluePalette.SELECTED;
			highlighted = BluePalette.HIGHLIGHTED;
			promotion = BluePalette.PROMOTION;
			accent = BluePalette.ACCENT;
			break;
		case RAINBOW:
			light = RainbowPalette.LIGHT;
			dark = RainbowPalette.DARK;
			selected = RainbowPalette.SELECTED;
			highlighted = RainbowPalette.HIGHLIGHTED;
			promotion = RainbowPalette.PROMOTION;
			accent = RainbowPalette.ACCENT;
			break;
		case GRAY:
			light = GrayPalette.LIGHT;
			dark = GrayPalette.DARK;
			selected = GrayPalette.SELECTED;
			highlighted = GrayPalette.HIGHLIGHTED;
			promotion = GrayPalette.PROMOTION;
			accent = GrayPalette.ACCENT;
			break;
		case GREEN:
			light = GreenPalette.LIGHT;
			dark = GreenPalette.DARK;
			selected = GreenPalette.SELECTED;
			highlighted = GreenPalette.HIGHLIGHTED;
			promotion = GreenPalette.PROMOTION;
			accent = GreenPalette.ACCENT;
			break;
		default:
			light = RedPalette.LIGHT;
			dark = RedPalette.DARK;
			selected = RedPalette.SELECTED;
			highlighted = RedPalette.HIGHLIGHTED;
			promotion = RedPalette.PROMOTION;
			accent = RedPalette.ACCENT;
			break;
		}
	}
	
	/****************************************************************
	 * Returns the color for the light squares on the board.
	 * 
	 * @return the color for the light squares on the board.
	 ***************************************************************/
	public final Color getLight() {
		return light;
	}
	
	/****************************************************************
	 * Returns the color for the dark squares on the board.
	 * 
	 * @return the color for the dark squares on the board.
	 ***************************************************************/
	public final Color getDark() {
		return dark;
	}
	
	/****************************************************************
	 * Returns the color for the selected squares on the board.
	 * 
	 * @return the color for the selected squares on the board.
	 ***************************************************************/
	public final Color getSelected() {
		return selected;
	}
	
	/****************************************************************
	 * Returns the color for the highlighted squares on the board.
	 * 
	 * @return the color for the highlighted squares on the board.
	 ***************************************************************/
	public final Color getHighlighted() {
		return highlighted;
	}
	
	/****************************************************************
	 * Returns the color for the background of promotion options.
	 * 
	 * @return the color for the background of promotion options.
	 ***************************************************************/
	public final Color getPromotion() {
		return promotion;
	}
	
	/****************************************************************
	 * Returns the color for the board's accent.
	 * 
	 * @return the color for the board's accent.
	 ***************************************************************/
	public final Color getAccent() {
		return accent;
	}
}
