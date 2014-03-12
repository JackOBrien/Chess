package presenter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import view.ChessGUI;
import view.IChessGUI;
import model.IChessModel;
import model.IChessPiece;
import model.Player;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * 
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public class Presenter {

	/** Image for the white game pieces */
	private ImageIcon w_bish = loadIcon("images\\w_bish.png"),
	  w_king = loadIcon("images\\w_king.png"),
	  w_knight = loadIcon("images\\w_knight.png"),
	  w_pawn = loadIcon("images\\w_pawn.png"),
	  w_queen = loadIcon("images\\w_queen.png"),
	  w_rook = loadIcon("images\\w_rook.png");

	/** Image for the black game pieces */
	private ImageIcon b_bish = loadIcon("images\\b_bish.png"),
	 b_king = loadIcon("images\\b_king.png"),
	 b_knight = loadIcon("images\\b_knight.png"),
	 b_pawn = loadIcon("images\\b_pawn.png"),
	 b_queen = loadIcon("images\\b_queen.png"),
	 b_rook = loadIcon("images\\b_rook.png");

	private final int IMG_SIZE = 60;

	private Color light;
	private Color dark;
	private Color selected;
//	private Color highlighted;
	
	private IChessModel model;
	private IChessGUI view;
	
	public Presenter(IChessModel pModel, IChessGUI pView) {
		model = pModel;
		view = pView;
		
		light = new Color(196, 177, 143);
		dark = new Color(49, 46, 40);
		selected = new Color(255, 179, 47);
		
		view.initializeBoard(convertBoardIntoButtons());
	}
	
	/********************************************************
	 * Static method to load the ImageIcon from the given location
	 * 
	 * @param name  Name of the file
	 * @return  The requested image
	 *******************************************************/
	public static  ImageIcon loadIcon(String name) {
		java.net.URL imgURL = ChessGUI.class.getResource(name);
		if (imgURL == null) {
			throw new RuntimeException("Icon resource not found.");
		}  

		return new ImageIcon(imgURL);
	}
	
	public JButton[][] convertBoardIntoButtons() {
		int rows = model.getBoard().numRows();
		int cols = model.getBoard().numColumns();
		
		JButton[][] buttonArray = new JButton[rows][cols];
		
		boolean lightSquare = false;

		
		for (int r = 0; r < rows; r++) {
			
			lightSquare = !lightSquare;
			
			for (int c = 0; c < cols; c++) {
				
				
				
				
				IChessPiece piece = model.pieceAt(r, c);
				
				final int borderSpace = 5;
				ImageIcon icon = imageFinder(piece);
				icon = resizeImage(icon, IMG_SIZE - borderSpace);
				
				JButton button = new JButton(icon);
				button.setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
				Color bg = dark;
				
				if (lightSquare) {
					bg = light;
				}
				
				button.setBackground(bg);
				
				buttonArray[r][c] = button;
				
				lightSquare = !lightSquare;
			}
		}
		
		return buttonArray;
	}
	
	/****************************************************************
	 * Helper method to return the image that matches the
	 * given chess piece.
	 * 
	 * ie: Rook(Player.WHITE) --> w_rook
	 * 
	 * @param p the given chess piece.s
	 * @return  image corresponding to the piece.
	 ***************************************************************/
	private ImageIcon imageFinder(IChessPiece p) {		
		ImageIcon image = null;
		
		if (p == null) { return image; }
		
		if (p.player() == Player.WHITE) {
			
			/* Assigns proper image */
			switch(p.type()) {
			case "Bishop":
				image = w_bish;
				break;
			case "King":
				image = w_king;
				break;
			case "Knight":
				image = w_knight;
				break;
			case "Pawn":
				image = w_pawn;
				break;
			case "Queen":
				image = w_queen;
				break;
			case "Rook":
				image = w_rook;
				break;
			}
		} else {
			
			/* Assigns proper image */
			switch(p.type()) {
			case "Bishop":
				image = b_bish;
				break;
			case "King":
				image = b_king;
				break;
			case "Knight":
				image = b_knight;
				break;
			case "Pawn":
				image = b_pawn;
				break;
			case "Queen":
				image = b_queen;
				break;
			case "Rook":
				image = b_rook;
				break;
			}
		}
		
		return image;
	}
	
	/****************************************************************
	 * Takes an ImageIcon and changes its size.
	 * 
	 * @param icon the ImageIcon to be changed.
	 * @param size the new width and height of the image.
	 * @return the resized ImageIcon.
	 ***************************************************************/
	private ImageIcon resizeImage(ImageIcon icon, int size) {
		
		if (icon == null) { return icon; }
		
		Image img = icon.getImage();
				
		Image resized = img.getScaledInstance(size, size, 
				Image.SCALE_AREA_AVERAGING);
		return new ImageIcon(resized);
	}
	
}
