package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.plaf.MenuBarUI;

import model.IChessPiece;
import model.Player;
import presenter.Presenter;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Graphical user interface of a chess game.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public class ChessGUI implements IChessGUI {

	/** Image for the white game pieces. */
	private ImageIcon w_bish = loadIcon("images\\w_bish.png"),
	  wKing = loadIcon("images\\w_king.png"),
	  wKnight = loadIcon("images\\w_knight.png"),
	  wPawn = loadIcon("images\\w_pawn.png"),
	  wQueen = loadIcon("images\\w_queen.png"),
	  wRook = loadIcon("images\\w_rook.png");

	/** Image for the black game pieces */
	private ImageIcon b_bish = loadIcon("images\\b_bish.png"),
	 bKing = loadIcon("images\\b_king.png"),
	 bKnight = loadIcon("images\\b_knight.png"),
	 bPawn = loadIcon("images\\b_pawn.png"),
	 bQueen = loadIcon("images\\b_queen.png"),
	 bRook = loadIcon("images\\b_rook.png");
	
	private ImageIcon gvsu = loadIcon("images\\GVSUlogoSmall.png");
	private ImageIcon kingLogo = loadIcon("images\\kingIconLarge.png");
	
	private JButton[][] board;
	
	private JFrame topWindow;
	
	
	
	/****************************************************************
	 * TODO 
	 ***************************************************************/
	public ChessGUI(int numRows, int numCols) {
		topWindow = new JFrame();
		topWindow.setLayout(new GridLayout(8, 8));
		
		board = new JButton[numRows][numCols];
		
//		setupBlankBoard(); TODO
		setupMenu();
		setupFrame();
	}

	private void setupFrame() {
		List<Image> al = new ArrayList<Image>();
		al.add(gvsu.getImage());
		al.add(kingLogo.getImage());
		
		topWindow.setTitle("CIS 350: Chess Game");
		topWindow.setIconImages(al);
		topWindow.pack();
		topWindow.setResizable(false);
		topWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setupMenu() {
		JMenu menu = new JMenu("File");
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.setBackground(new Color(53, 28, 28));
		
		JMenuItem item = new JMenuItem("GVSU", gvsu);
		menu.setForeground(Color.WHITE);
		
		menuBar.setLayout(new BorderLayout());
		
		menu.add(item);
		menuBar.add(menu);
//		menuBar.add(new JLabel("testing"), BorderLayout.LINE_END);
		
		topWindow.setJMenuBar(menuBar);
	}
	
	/****************************************************************
	 * Static method to load the ImageIcon from the given location.
	 * 
	 * @param name Name of the file.
	 * @return the requested image.
	 ***************************************************************/
	public static  ImageIcon loadIcon(String name) {
		java.net.URL imgURL = ChessGUI.class.getResource(name);
		if (imgURL == null) {
			throw new RuntimeException("Icon resource not found.");
		}  

		return new ImageIcon(imgURL);
	}
	
	@Override
	public void initializeBoard(JButton[][] pBoard) {
		board = pBoard;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				topWindow.add(board[r][c]);
			}
		}
		
		topWindow.pack();
		topWindow.setVisible(true);
	}

	@Override
	public void changeColor(int row, int col, Color c) {
		board[row][col].setBackground(c);
	}
	
	@Override
	public ImageIcon imageFinder(String type, boolean white) {		
		ImageIcon image = null;
				
		if (white) {
			
			/* Assigns proper image */
			switch(type) {
			case "Bishop":
				image = w_bish;
				break;
			case "King":
				image = wKing;
				break;
			case "Knight":
				image = wKnight;
				break;
			case "Pawn":
				image = wPawn;
				break;
			case "Queen":
				image = wQueen;
				break;
			case "Rook":
				image = wRook;
				break;
			default:
				image = null;
				break;
			}
		} else {
			
			/* Assigns proper image */
			switch(type) {
			case "Bishop":
				image = b_bish;
				break;
			case "King":
				image = bKing;
				break;
			case "Knight":
				image = bKnight;
				break;
			case "Pawn":
				image = bPawn;
				break;
			case "Queen":
				image = bQueen;
				break;
			case "Rook":
				image = bRook;
				break;
			default:
				image = null;
				break;
			}
		}
		
		return image;
	}
	
}
