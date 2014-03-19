package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Adds new outlines to buttons when they're being interacted with.
 *
 * @author John O'Brien
 * @version Mar 15, 2014
 *******************************************************************/
public class BevelOnHover implements MouseListener {

	/** The color of the default border. */
	private Color borderColor;
	
	/****************************************************************
	 * Mouse Listener that manipulates the border of a button when
	 * hovered over or pressed.
	 * 
	 * @param border the initial color of the border.
	 ***************************************************************/
	public BevelOnHover(final Color border) {
		borderColor = border;
	}
	
	@Override
	public final void mouseReleased(final MouseEvent e) {
		JButton button = (JButton) e.getComponent();
		
		Border border = BorderFactory.createRaisedSoftBevelBorder();
		button.setBorder(border);
	}
	
	@Override
	public final void mousePressed(final MouseEvent e) {
		JButton button = (JButton) e.getComponent();	
		
		Border border = BorderFactory.createLoweredSoftBevelBorder();
		button.setBorder(border);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		JButton button = (JButton) e.getComponent();
		
		Border line = BorderFactory.createLineBorder(borderColor, 1);
		button.setBorder(line);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		JButton button = (JButton) e.getComponent();
		
		Border border = BorderFactory.createRaisedSoftBevelBorder();
		button.setBorder(border);
	}

	@Override
	public void mouseClicked(MouseEvent e) { }
}
