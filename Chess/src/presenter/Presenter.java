package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.IChessGUI;
import model.Bishop;
import model.IChessModel;
import model.IChessPiece;
import model.Knight;
import model.Move;
import model.Pawn;
import model.Player;
import model.Queen;
import model.Rook;

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
	
	/** The player who is promoting a piece */
	private Player playerPromoting;
	
	/** The most recent move to promote a piece */
	private Move promoMove;
	
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
		view.setPromotionHandler(promotionHandler);
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
				
				boolean white = false;
				
				/* Null check */
				if (piece != null) {
					type = piece.type();
					white = piece.player().isWhite();
				}
				
				view.changeImage(r, c, type, white);
			}
		}
	}
	
	private void highlightValidMoves(int row, int col) {
		int size = model.getBoard().numRows();
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				Move m = new Move(row, col, r, c);
				
				if (r == 5 && c == 4) {
					model.isValidMove(m);
				}
				
				if (model.isValidMove(m)) {
					view.setHighlighted(r, c);
				}
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
			

			/* Checks if the player is choosing a piece or its destination. */
			if (!isPieceSelected) {
				
				/* There is no action when selecting a blank cell. */
				if (piece == null) { return; } 
				
				/* There is no action if you select a piece from the 
				 * wrong player. */
				if (model.currentPlayer() != piece.player()) { return; }
				
				/* Recolors the selected piece. */
				view.setSelected(row, col);
				selectedPiece = e.getActionCommand();
				isPieceSelected = true;
				highlightValidMoves(row, col);
				
				return;
				
			} else {
				
				/* Checks if you are deselecting the piece. */
				if (selectedPiece == e.getActionCommand()) {
					view.deselectAll();
					selectedPiece = "";
					isPieceSelected = false;
					return;
				}
								
				// Parses the selected piece
				String[] loc = selectedPiece.split(",");
				int fromRow = Integer.parseInt(loc[0]);
				int fromColumn = Integer.parseInt(loc[1]);
				
				piece = model.pieceAt(fromRow, fromColumn);
				
				Move move = new Move(fromRow, fromColumn, row, col);
				
				/* There is no action if the move is not valid */
				if (!model.isValidMove(move)) { return; }
												
				// Handles special moves
				handleEnPassant(move);
				handleCastle(move);
				
				model.move(move);
				
				view.changeImage(fromRow, fromColumn, "", true);
				view.changeImage(row, col, piece.type(), 
						piece.player().isWhite());
				
				// Promotion must be handled after the move
				handlePromotion(move);
								
				view.deselectAll();
				isPieceSelected = false;
			}
			
		}
	};

	private ActionListener promotionHandler = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String source = e.getActionCommand();
			
			IChessPiece newPiece;
			
			switch (source) {
			case "Rook":
				newPiece = new Rook(playerPromoting);
				break;
			case "Knight":
				newPiece = new Knight(playerPromoting);
				break;
			case "Bishop":
				newPiece = new Bishop(playerPromoting);
				break;
			case "Queen":
				newPiece = new Queen(playerPromoting);
				break;
			default:
				newPiece = null;
				break;
			}
			
			int tR = promoMove.toRow();
			int tC = promoMove.toColumn();
			
			model.getBoard().set(newPiece, tR, tC);
			view.changeImage(tR, tC, source, playerPromoting.isWhite());
			
		}
	};
	
	/****************************************************************
	 * If a piece performs enPassant, it will remove the piece
	 * that was attacked from the board.
	 * 
	 * @param move the move being attempted.
	 ***************************************************************/
	protected void handleEnPassant(Move move) {
		IChessPiece piece = model.pieceAt(move.fromRow(), move.fromColumn());
		
		if (piece == null || !piece.is("Pawn")) { return; }
		
		Pawn pawn = (Pawn) piece;
		
		if (pawn.isAttacking(move, model.getBoard()) && 
				model.pieceAt(move.toRow(), move.toColumn()) == null) {
			view.changeImage(move.fromRow(), move.toColumn(), "", true);
			
		}
	}
	
	/****************************************************************
	 * If a pawn reaches the end of the board, they much choose 
	 * a new piece to promote the pawn to.
	 * 
	 * @param m the move being attempted.
	 * @return 
	 ***************************************************************/
	protected void handlePromotion(Move m) {
		IChessPiece piece = model.pieceAt(m.toRow(), m.toColumn());

		if (piece == null || !piece.is("Pawn")) { return; }
		
		/* Ensures the pawn has reached the end of the board */
		if (((Pawn) piece).mayPromote()) {			
			playerPromoting = piece.player();
			promoMove = m;
			
			view.pawnPromotion(m.toRow(), m.toColumn(), 
					playerPromoting.isWhite());
		}
	}

	/****************************************************************
	 * If a piece performs a Castle, it will remove the piece
	 * that was attacked from the board.
	 * 
	 * @param move the move being attempted.
	 ***************************************************************/
	protected void handleCastle(Move move) {
		IChessPiece piece = model.pieceAt(move.fromRow(), move.fromColumn());
		
		if (piece == null || !piece.is("King")) { return; }
		
		int distance = move.toColumn() - move.fromColumn();
		int direction = distance > 0 ? 1 : -1;
		
		if (distance * direction != 2) { return; }

		int rookColumn = 0;

		if (direction == 1) {
			rookColumn = model.getBoard().numColumns() - 1;
		} 
				
		view.changeImage(move.fromRow(), rookColumn, "", false);
		view.changeImage(move.fromRow(), move.fromColumn() + direction, 
				"Rook", piece.player().isWhite());
	}
	
	protected void checkForCheck() {
		if (model.inCheck()) {
		
			
			
			if (model.isComplete()) {
				
			}
		}
	}
	
}
