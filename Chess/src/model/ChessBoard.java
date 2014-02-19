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
 * @version Feb 2, 2014
 *******************************************************************/
public class ChessBoard implements IChessBoard {

	/** 2d array of ChessPieces to represent the board */
	private IChessPiece[][] board;
	
	/** Tells who's turn it currently is (WHITE or BLACK) */
	private Player currentPlayer;
	
	/** Tells how many moves have been made */
	private int numMoves;
	
	/** Location of the white King */
	private int[] whiteKing;
	
	/** Location of the black King */
	private int[] blackKing;
	
	/****************************************************************
	 * Constructor for ChessBoard.
	 * Default game has 32 pieces and WHITE goes first.
	 * 
	 * @param boardSize the size of an edge on the square board (8 default).
	 * @param placePieces if true, will place game pieces. 
	 * 			If false, will create an empty game board.
	 ***************************************************************/
	public ChessBoard(int boardSize, boolean placePieces){
		board = new ChessPiece[boardSize][boardSize];
		
		/* Creates an empty board if placePieces is false */
		if(!placePieces)
			for(int r = 0; r < boardSize; r++)
				for(int c = 0; c < boardSize; c++)
					board[r][c] = null;
		else
			setupBoard();
		
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
	public ChessBoard(ChessBoard other) {
		board = other.clone();
		
		currentPlayer = other.currentPlayer;
		numMoves = other.numMoves;
		whiteKing = other.whiteKing;
		blackKing = other.blackKing;
	}
	
	/****************************************************************
	 * Places the game pieces into their default locations on the board
	 ***************************************************************/
	private void setupBoard(){
		
		/* Rows for the black pieces*/
		int rowPawns = 1; 
		int row = 0;
		
		/* Places both black and white pieces */
		for (Player p : Player.values()){				
			board[row][0] = new Rook(p);
			board[row][1] = new Knight(p);
			board[row][2] = new Bishop(p);
			board[row][3] = new Queen(p);
			board[row][4] = new King(p);
			board[row][5] = new Bishop(p);
			board[row][6] = new Knight(p);
			board[row][7] = new Rook(p);
			
			/* Places pawns */
			for (int col = 0; col < 8; col++)
				board[rowPawns][col] = new Pawn(p);
			
			row = 7; 
			rowPawns = 6;
		}
		
		/* Sets the rest to null */
		for (int r = 2; r < 6; r++)
			for (int c = 0; c < 8; c++)
				board[r][c] = null;
	}
	
	@Override
	public int numRows() {
		return board.length;
	}

	@Override
	public int numColumns() {
		return board[0].length;
	}

	@Override
	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	@Override
	public void move(Move move) {
		
		/* Moves piece at the from location to the to location
		 * Sets from location to null */
		IChessPiece movingPiece = pieceAt(move.fromRow, move.fromColumn);
		set(null, move.fromRow, move.fromColumn);
		set(movingPiece, move.toRow, move.toColumn);
		
		/* Switches turns */
		currentPlayer = currentPlayer.next();
		
		/* If the piece being moved is a king, the location is recorded */
		if (movingPiece != null && movingPiece instanceof King)
			updateKingLocation(move.toRow, move.toColumn);
		
		/* Increments the number of moves that have been made */
		numMoves++;
	}
	
	/****************************************************************
	 * Updates the arrays keeping track of each king's location.
	 * 
	 * @param row row coordinate of the piece
	 * @param col column coordinate of the piece
	 ***************************************************************/
	private void updateKingLocation(int row, int col) {
		
		/* Checks which player owns the piece */
		if(pieceAt(row, col).player() == Player.WHITE) {
			whiteKing[0] = row;
			whiteKing[1] = col;
		}else {
			blackKing[0] = row;
			blackKing[1] = col;
		}
	}

	@Override
	public void set(IChessPiece piece, int row, int column) {
		board[row][column] = piece;
	}

	@Override
	public void unset(int row, int column) {
		board[row][column] = null;
	}
	
	@Override
	public int[] findKing(Player p) {
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
	public int getNumMoves() {
		return numMoves;
	}
	
	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public IChessPiece[][] getGameBoard(){
		return board;
	}
	
	@Override
	public IChessPiece[][] clone() {
		IChessPiece[][] b = new ChessPiece[numRows()][numColumns()];
		
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numColumns(); c++) {
				IChessPiece p = pieceAt(r, c);
				
				if (p == null) { continue; }
				
				Player plr = p.player();

				switch (p.type()) {
				case "Pawn": b[r][c] = new Pawn(plr);
					break;
				case "Rook": b[r][c] = new Rook(plr);
					break;
				case "Bishop": b[r][c] = new Bishop(plr);
					break;
				case "Knight": b[r][c] = new Knight(plr);
					break;
				case "Queen": b[r][c] = new Queen(plr);
					break;
				case "King": b[r][c] = new King(plr);
					int [] location = {r, c};
					setKing(plr, location);
					break;
				default:
					break;
				}
			}
		}
		return b;
	}
	
	@Override
	public void printBoard() {
		String out = "--------\n";
		
		for (int r = 0; r < numRows(); r++) {
			for (int c = 0; c < numColumns(); c++) {
				IChessPiece p = pieceAt(r, c);
				
				if (p == null) { 
					out += "'";
					continue; 
				}
				
				Player plr = p.player();

				switch (p.type()) {
				case "Pawn": out += "p";
					break;
				case "Rook": out += "R";
					break;
				case "Bishop": out += "B";
					break;
				case "Knight": out += "k";
					break;
				case "Queen": out += "Q";
					break;
				case "King": out += "K";
					break;
				default: out += "-";
					break;
				}
			}
			out += "\n";
		}
		
		System.out.println(out+ "--------");
	}
}
