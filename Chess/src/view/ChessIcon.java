package view;

import javax.swing.ImageIcon;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * 
 *
 * @author John O'Brien
 * @version Mar 26, 2014
 *******************************************************************/
public enum ChessIcon {

	W_ROOK ("w_rook"), W_KNIGHT ("w_knight"), W_BISH ("w_bish"), 
	W_QUEEN ("w_queen"), W_KING ("w_king"), W_PAWN ("w_king"),
	B_ROOK ("b_rook"), B_KNIGHT ("b_knight"), B_BISH ("b_bish"), 
	B_QUEEN ("b_queen"), B_KING ("b_king"), B_PAWN ("b_king");
	
	/** Name of the folder containing the images of the game pieces. */
	private final String path = "images/";
	
	private final String fileType = ".png";
	
	private ImageIcon icon;
	
	private ChessIcon(String name) {
		icon = loadIcon(path + name + fileType);
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	/****************************************************************
	 * Static method to load the ImageIcon from the given location.
	 * 
	 * @param name Name of the file.
	 * @return the requested image.
	 ***************************************************************/
	private static  ImageIcon loadIcon(final String name) {
		java.net.URL imgURL = ChessGUI.class.getResource(name);
		if (imgURL == null) {
			throw new RuntimeException("Icon resource not found.");
		}  

		return new ImageIcon(imgURL);
	}
}
