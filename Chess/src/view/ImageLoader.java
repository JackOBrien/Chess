package view;

import java.awt.Image;

import javax.swing.ImageIcon;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Utility class to load and return ImageIcons
 *
 * @author John O'Brien
 * @version Mar 26, 2014
 *******************************************************************/
public class ImageLoader {

	/** The amount of extra space between the edge of the button 
	 * and the edge of the image. Default 5. */
	private final static int BORDER = 5;
	
	private ImageLoader() { }
	
	/****************************************************************
	 * Static method to load the ImageIcon from the given location.
	 * 
	 * @param name Name of the file.
	 * @return the requested image.
	 ***************************************************************/
	public static  ImageIcon loadIcon(final String name) {
		java.net.URL imgURL = ChessGUI.class.getResource(name);
		if (imgURL == null) {
			throw new RuntimeException("Icon resource not found.");
		}  

		return new ImageIcon(imgURL);
	}
	
	/****************************************************************
	 * Takes an ImageIcon and changes its size.
	 * 
	 * @param icon the ImageIcon to be changed.
	 * @param pSize the new width and height of the image.
	 * @return the resized ImageIcon.
	 ***************************************************************/
	public static ImageIcon resizeImage(final ImageIcon icon, final int pSize) {
		
		int size = pSize;
		
		size -= BORDER;
		
		if (icon == null) { return icon; }
		
		Image img = icon.getImage();
				
		Image resized = img.getScaledInstance(size, size, 
				Image.SCALE_AREA_AVERAGING);
		return new ImageIcon(resized);
	}
}
