package view;

import javax.swing.ImageIcon;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 *
 * @author John O'Brien
 * @version Mar 26, 2014
 *******************************************************************/
public enum ChessIcon {
	
	/** Icon to represent the white Rooks. */
	W_ROOK ("w_rook", "Rook", true), 
	
	/** Icon to represent the white Knights. */
	W_KNIGHT ("w_knight", "Knight", true), 
	
	/** Icon to represent the white Bishops. */
	W_BISH ("w_bish", "Bishop", true), 
	
	/** Icon to represent the white Queen. */
	W_QUEEN ("w_queen", "Queen", true), 

	/** Icon to represent the white King. */
	W_KING ("w_king", "King", true), 
	
	/** Icon to represent the white Pawns. */
	W_PAWN ("w_pawn", "Pawn", true),
	
	/** Icon to represent the black Rooks. */
	B_ROOK ("b_rook", "Rook", false), 
	
	/** Icon to represent the black Knights. */
	B_KNIGHT ("b_knight", "Knight", false),
	
	/** Icon to represent the black Bishop. */
	B_BISH ("b_bish", "Bishop", false),
	
	/** Icon to represent the black Queen. */
	B_QUEEN ("b_queen", "Queen", false), 
	
	/** Icon to represent the black King. */
	B_KING ("b_king", "King", false), 
	
	/** Icon to represent the black Pawns. */
	B_PAWN ("b_pawn", "Pawn", false);
	
	/** Name of the folder containing the images of the game pieces. */
	private final String path = "images/";
	
	/** The file type of the icons. */
	private final String fileType = ".png";
	
	/** The icon. */
	private ImageIcon icon;
	
	/** The name of the file. */
	private String filename;
	
	/** The type of the piece. */
	private String pieceType;
	
	/** Tells if the piece is white. */
	private boolean isWhite;
	
	/****************************************************************
	 * Constructor for the enum.
	 * 
	 * @param name name of the piece's file.
	 * @param type name of the piece.
	 * @param white tells if the piece is white.
	 ***************************************************************/
	private ChessIcon(String name, String type, boolean white) {
		
		filename = name;
		pieceType = type;
		isWhite = white;
				
		icon = ImageLoader.loadIcon(path + filename + fileType);
	}
	
	/****************************************************************
	 * @return the piece's icon.
	 ***************************************************************/
	public ImageIcon getIcon() {
		return icon;
	}
	
	/****************************************************************
	 * Returns the image of the described game piece.
	 * 
	 * @param type the type of the piece.
	 * @param white tells if the piece is white.
	 * @return the image of the described game piece.
	 ***************************************************************/
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
