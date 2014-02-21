package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Chess board that holds all the game pieces.
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 20, 2014
 *******************************************************************/
public class ChessBoard implements IChessBoard {

	/** 2d array of ChessPieces to represent the board. */
	private IChessPiece[][] board;
	
	/** Tells who's turn it currently is (WHITE or BLACK). */
	private Player currentPlayer;
	
	/** Tells how many moves have been made. */
	private int numMoves;
	
	/** Location of the white King. */
	private int[] whiteKing;
	
	/** Location of the black King. */
	private int[] blackKing;
	
	/****************************************************************
	 * Constructor for ChessBoard.
	 * Default game has 32 pieces and WHITE goes first.
	 * 
	 * @param boardSize the size of an edge on the square board (8 default).
	 * @param placePieces if true, will place game pieces. 
	 * 			If false, will create an empty game board.
	 ***************************************************************/
	public ChessBoard(final int boardSize, final boolean placePieces){
		board = new ChessPiece[boardSize][boardSize];
		
		/* Creates an empty board if placePieces is false */
		if (!placePieces) {
			for (int r = 0; r < boardSize; r++) {
				for (int c = 0; c < boardSize; c++) {
					board[r][c] = null;
				}
			}
		} else {
			setupBoard();
		}
		
		currentPlayer = Player.WHITE;
		numMoves = 0;
		
		whiteKing = new int[2];
		blackKing = new int[2];
		
		/* Default locations of the two kings */
		whiteKing[0] = 7;
		whiteKing[1] = 4;
		
		blackKing[0] = 0;
		blackKing[1] = 4;
	}
	
	/****************************************************************
	 * Copy constructor.
	 * 
	 * @param other the ChessBoard to copy.
	 ***************************************************************/
	public ChessBoard(final ChessBoard other) {
		board = other.clone();
		
		currentPlayer = other.currentPlayer;
		numMoves = other.numMoves;
		whiteKing = other.whiteKing;
		blackKing = other.blackKing;
	}
	
	/****************************************************************
	 * Places the game pieces into their default locations on the board.
	 ***************************************************************/
	private void setupBoard(){
		
		/* Rows for the black pieces*/
		int rowPawns = 1; 
		int row = 0;
		
		/* Places both black and white pieces */
		for (Player p : Player.values()) {				
			board[row][0] = new Rook(p);
			board[row][1] = new Knight(p);
			board[row][2] = new Bishop(p);
			board[row][3] = new Queen(p);
			board[row][4] = new King(p);
			board[row][5] = new Bishop(p);
			board[row][6] = new Knight(p);
			board[row][7] = new Rook(p);
			
			/* Places pawns */
			for (int col = 0; col < 8; col++) {
				board[rowPawns][col] = new Pawn(p);
			}
			
			row = 7; 
			rowPawns = 6;
		}
		
		/* Sets the rest to null */
		for (int r = 2; r < 6; r++) {
			{
				for (int c = 0; c < 8; c++) {
					board[r][c] = null;
				}
			}
		}
	}
	
	@Override
	public final int numRows() {
		return board.length;
	}

	@Override
	public final int numColumns() {
		return board[0].length;
	}

	@Override
	public final IChessPiece pieceAt(final int row, final int column) {
		return board[row][column];
	}

	@Override
	public final void move(final Move move) {
		
		/* Moves piece at the from location to the to location
		 * Sets from location to null. */
		IChessPiece movingPiece = pieceAt(move.fromRow, move.fromColumn);
		// Checks and handles an en Passant move
		handleEnPassant(movingPiece, move);
		unset(move.fromRow, move.fromColumn);
		set(movingPiece, move.toRow, move.toColumn);
		
		// Switches turns
		currentPlayer = currentPlayer.next();
		
		/* If the piece being moved is a king, the location is recorded */
		if (movingPiece != null && movingPiece.is("King")) {
			updateKingLocation(move.toRow, move.toColumn);
		}
		
		/* Increments the number of moves that have been made */
		numMoves++;
	}
	
	/****************************************************************
	 * @param movingPiece TODO
	 * @param move the move being attempted 
	 ***************************************************************/
	private void handleEnPassant(final IChessPiece movingPiece,
			final Move move) {
		
		if (movingPiece == null) { return; }
		
		if (!movingPiece.is("Pawn")) { return; }
		
		Pawn p = (Pawn) movingPiece;
		
		/* Removes attacked pawn from the board */
		if (pieceAt(move.toRow, move.toColumn) == null 
				&& 
				p.isAttacking(move, this)) { 
			unset(move.toRow, move.fromColumn);
		}
	}

	/****************************************************************
	 * Updates the arrays keeping track of each king's location.
	 * 
	 * @param row row coordinate of the piece
	 * @param col column coordinate of the piece
	 ***************************************************************/
	private void updateKingLocation(final int row, final int col) {
		
		/* Checks which player owns the piece */
		if (pieceAt(row, col).player() == Player.WHITE) {
			whiteKing[0] = row;
			whiteKing[1] = col;
		} else {
			blackKing[0] = row;
			blackKing[1] = col;
		}
	}

	@Override
	public final void set(final IChessPiece piece, final int row,
			final int column) {
		board[row][column] = piece;
	}

	@Override
	public final void unset(final int row, final int column) {
		board[row][column] = null;
	}
	
	@Override
	public final int[] findKing(final Player p) {
		if (p == Player.WHITE) {
			return whiteKing;
		} else {
			return blackKing;
		}
	}
	
	private void setKing(Player p, int[] location) {
		if (p == Player.WHITE) {
			whiteKing = location;
		} else {
			blackKing = location;
		}
	}
	
	@Override
	public final int getNumMoves() {
		return numMoves;
	}
	
	@Override
	public final Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	@Override
	public final IChessPiece[][] clone() {
		IChessPiece[][] b = new ChessPiece[numRows()][numColumns()];
		
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numColumns(); c++) {
				
				assignProperPiece(b, r, c);
			}
		}
		return b;
	}
	
	/****************************************************************
	 * Places whatever piece was found on this board at the given 
	 * location on a given board.
	 * 
	 * This helper is used to replace the switch statement that was
	 * originally used in the clone method. The reason being, EclEmma
	 * doesn't properly cover switch statements. 
	 * 
	 * @param b the given board to have pieces placed on.
	 * @param r the row location of the piece.
	 * @param c the column location of the piece.
	 ***************************************************************/
	private void assignProperPiece(final IChessPiece[][] b, final int r,
			final int c) {
		IChessPiece p = pieceAt(r, c);
		
		if (p == null) { return; }
		
		Player plr = p.player();
		String type = p.type();
		
		if (type.equals("Pawn")) {
			b[r][c] = new Pawn(plr);
		}

		if (type.equals("Rook")) {
			b[r][c] = new Rook(plr);
		}

		if (type.equals("Bishop")) {
			b[r][c] = new Bishop(plr);
		}

		if (type.equals("Knight")) {
			b[r][c] = new Knight(plr);
		}

		if (type.equals("Queen")) {
			b[r][c] = new Queen(plr);
		}

		/* The king also records the location of the kings */
		if (type.equals("King")) {
			b[r][c] = new King(plr);
			int [] location = {r, c};
			setKing(plr, location);
		}
	}
}
