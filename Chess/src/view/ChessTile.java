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
 * 
 *
 * @author John O'Brien
 * @version Mar 26, 2014
 *******************************************************************/
public class ChessTile extends JButton {

	/** Default serial UID */
	private static final long serialVersionUID = 1L;
	
	/** MouseListener that will bevel buttons when interacted with. */
	private BevelOnHover mouseListener;

	private Color defaultBG;
	
	private int size;
	
	private boolean specialHighlight;
	
	public ChessTile(int pSize) {
		size = pSize;
		
		// Filler color.
		defaultBG = Color.GRAY;
		
		mouseListener = new BevelOnHover(Color.BLACK);
		specialHighlight = false;
		
		setUpButton();
	}
	
	public void setDefaultBackground(Color bg) {
		defaultBG = bg;
		setBackground(bg);
	}
	
	public void resetColor() {
		if (specialHighlight) {
			setBackground(defaultBG);
		}
	}
	
	public void isSpecialHighlight(boolean b) {
		specialHighlight = b;
	}
	
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
