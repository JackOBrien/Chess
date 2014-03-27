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

	W_ROOK ("w_rook", "Rook", true), W_KNIGHT ("w_knight", "Knight", true), 
	W_BISH ("w_bish", "Bishop", true), W_QUEEN ("w_queen", "Queen", true), 
	W_KING ("w_king", "King", true), W_PAWN ("w_pawn", "Pawn", true),
	
	B_ROOK ("b_rook", "Rook", false), B_KNIGHT ("b_knight", "Knight", false), 
	B_BISH ("b_bish", "Bishop", false),	B_QUEEN ("b_queen", "Queen", false), 
	B_KING ("b_king", "King", false), B_PAWN ("b_pawn", "Pawn", false);
	
	/** Name of the folder containing the images of the game pieces. */
	private final String path = "images/";
	
	private final String fileType = ".png";
	
	private ImageIcon icon;
	
	private String filename;
	
	private String pieceType;
	
	private boolean isWhite;
	
	private ChessIcon(String name, String type, boolean white) {
		
		filename = name;
		pieceType = type;
		isWhite = white;
				
		icon = ImageLoader.loadIcon(path + filename + fileType);
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public static ImageIcon findIcon(String type, boolean white) {
		
		/* Loop through all values searching for a match */
		for (ChessIcon i : ChessIcon.values()) {
			if (i.isWhite == white) {
				if (i.pieceType.equals(type)) {
					return i.getIcon();
				}
			}
		}
		
		return null;
	}
}
