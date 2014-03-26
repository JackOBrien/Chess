package view.colors;

import java.awt.Color;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * 
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
	
	public static final int RED = 1;
	
	public static final int BLUE = 2;
	
	public static final int RAINBOW = 3;
	
	public static final int GRAY = 4;
	
	public ColorController() {
		switchPalette(RED);
	}
	
	public ColorController(int color) {
		switchPalette(color);
	}
	
	public void switchPalette(int color) {
		
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
	
	public Color getLight() {
		return light;
	}
	
	public Color getDark() {
		return dark;
	}
	
	public Color getSelected() {
		return selected;
	}
	
	public Color getHighlighted() {
		return highlighted;
	}
	
	public Color getPromotion() {
		return promotion;
	}
	
	public Color getAccent() {
		return accent;
	}
}
