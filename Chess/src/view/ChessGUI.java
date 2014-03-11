package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Graphical user interface of a chess game.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public class ChessGUI implements IChessGUI {

	private ImageIcon w_bish = loadIcon("images\\w_bish.png");
	
	private final int DEFAULT_IMAGE_SIZE = 60;
	
	/********************************************************
	 * Static method to load the ImageIcon from the given location
	 * 
	 * @param name  Name of the file
	 * @return  The requested image
	 *******************************************************/
	private static  ImageIcon loadIcon(String name) {
		java.net.URL imgURL = ChessGUI.class.getResource(name);
		if (imgURL == null) {
			throw new RuntimeException("Icon resource not found.");
		}  

		return new ImageIcon(imgURL);
	}
	
	/****************************************************************
	 * TODO
	 * 
	 * @param button
	 * @return
	 ***************************************************************/
	private ImageIcon resizeImage(JButton button) {
		ImageIcon icon = ((ImageIcon) button.getIcon());
		Image img = icon.getImage();
		
		int size = button.getHeight();
		
		Image resized = img.getScaledInstance(size, size, 
				Image.SCALE_AREA_AVERAGING);
		return new ImageIcon(resized);
	}
	
	public ChessGUI() {
		JFrame frame = new JFrame();
		JButton b = new JButton();
		b.setIcon(w_bish);
		b.setSize(new Dimension(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE));
		b.setBackground(Color.gray);
		b.setIcon(resizeImage(b));
		
		frame.add(b);
		
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
