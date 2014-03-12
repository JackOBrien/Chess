package presenter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * Presenter for Chess. Handles interactions between model and view.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public class Presenter {

	private final int IMG_SIZE = 60;

	private Color light;
	private Color dark;
	private Color selected;
//	private Color highlighted;
	
	/** Tells if a piece is currently selected */
	private boolean pieceSelected;
	
	private IChessModel model;
	private IChessGUI view;
	
	/****************************************************************
	 * Constructor for the Presenter. 
	 * 
	 * @param pModel the model with the chess logic.
	 * @param pView the view with the chess GUI.
	 ***************************************************************/
	public Presenter(IChessModel pModel, IChessGUI pView) {
		model = pModel;
		view = pView;
		
		light = new Color(196, 177, 143);
		dark = new Color(49, 46, 40);
		selected = new Color(255, 179, 47);
		
		pieceSelected = false;
		
		view.initializeBoard(convertBoardIntoButtons());
	}
	
	/****************************************************************
	 * Converts the board used in the model into buttons for use
	 * in the view. Used to initially set up the board.
	 * 
	 * @return array of JButtons representing the game board.
	 ***************************************************************/
	public JButton[][] convertBoardIntoButtons() {
		int rows = model.getBoard().numRows();
		int cols = model.getBoard().numColumns();
		
		JButton[][] buttonArray = new JButton[rows][cols];
		
		// First square is light.
		boolean lightSquare = true;

		/* Creates every button with the appropriate image, color
		 * and location. Button default look is handled here.  */
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {

				// The ChessPiece at the current location and its descriptors.
				IChessPiece piece = model.pieceAt(r, c);
				String type = "";
				Boolean white = true;
				
				/* Null check */
				if (piece != null) {
					type = piece.type();
					white = piece.player() == Player.WHITE;
				}
				
				// The amount of extra space between the edge of 
				// the button an the edge of the image. Default 5.
				final int borderSpace = 5;
				
				// Finds the proper image and resizes it to defaults.
				ImageIcon icon = view.imageFinder(type, white);
				icon = resizeImage(icon, IMG_SIZE - borderSpace);
				
				// Creates the JButton
				JButton button = new JButton(icon);
				button.setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
				button.addActionListener(handlePieces);
				
				// Chooses the proper background color (light or dark)
				// depending on the boolean value.
				Color bg = dark;
				
				if (lightSquare) {
					bg = light;
				}
				
				button.setBackground(bg);
				
				// Adds an actionCommand for the button with the
				// coordinates of its location on the board: r,c.
				String name = r + "," + c;
				button.setActionCommand(name);
				
				buttonArray[r][c] = button;
				
				// Switches from dark to light or light to dark to 
				// prepare for the next column.
				lightSquare = !lightSquare;
			}
			
			// Switches from dark to light or light to dark to 
			// prepare for the next row.
			lightSquare = !lightSquare;
		}
		
		return buttonArray;
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
	
	/** ActionListener to handle selecting and moving game pieces */
	private ActionListener handlePieces = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Splits and parses the actionCommand into two integers.
			String[] location = e.getActionCommand().split(",");
			int row = Integer.parseInt(location[0]);
			int col = Integer.parseInt(location[1]);
			
			IChessPiece piece = model.pieceAt(row, col);
			
			/* There is no action when selecting a blank cell. */
			if (piece == null) { return; } 
			
			/* Checks if the player is choosing a piece or its destination. */
			if (!pieceSelected) {
				
				/* There is no action if you select a piece from the 
				 * wrong player. */
				if (model.currentPlayer() != piece.player()) { return; }
				
				
			}
			
		}
	};
	
}
