package presenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	/** Tells if a piece is currently selected */
	private boolean isPieceSelected;
	
	/** Location of the last selected piece: "r,c" */
	private String selectedPiece;
	
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
		
		isPieceSelected = false;
		selectedPiece = "";
		
		convertBoardIntoButtons();
		view.setMoveHandler(moveHandler);
	}
	
	/****************************************************************
	 * Converts the board used in the model into buttons for use
	 * in the view. Used to initially set up the board.
	 * 
	 * @return array of JButtons representing the game board.
	 ***************************************************************/
	public void convertBoardIntoButtons() {
		int rows = model.getBoard().numRows();
		int cols = model.getBoard().numColumns();

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
				
				view.changeImage(r, c, type, white);
			}
		}
	}
	
	/** ActionListener to handle selecting and moving game pieces */
	private ActionListener moveHandler = new ActionListener() {
		
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
			if (!isPieceSelected) {
				
				/* There is no action if you select a piece from the 
				 * wrong player. */
				if (model.currentPlayer() != piece.player()) { return; }
				
				if (selectedPiece != e.getActionCommand()) {
					view.setSelected(row, col);
					selectedPiece = e.getActionCommand();
					isPieceSelected = true;
				} else {
					
				}
				
				
			} else {
				if (selectedPiece == e.getActionCommand()) {
					view.setDeselected(row, col);
					selectedPiece = "";
					isPieceSelected = false;
					return;
				}
			}
			
		}
	};
	
}
