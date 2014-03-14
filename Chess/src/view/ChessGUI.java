package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
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
	
	private final int IMG_SIZE = 60;
	
	private Color light;
	private Color dark;
	private Color selected;
	private Color highlighted;
	
	private JButton[][] board;
	
	private JFrame topWindow;
	
	
	
	/****************************************************************
	 * TODO 
	 ***************************************************************/
	public ChessGUI(int numRows, int numCols) {
		topWindow = new JFrame();
		topWindow.setLayout(new GridLayout(numRows, numCols));
		
		// Doesn't allow the color to change when pressed
		UIManager.put("Button.select", Color.TRANSLUCENT);
		
		board = new JButton[numRows][numCols];
		
		light = new Color(196, 177, 143);
		dark = new Color(49, 46, 40);
//		selected = new Color(255, 179, 47);
		selected = new Color(146, 0, 17);
		highlighted = new Color(214, 55, 57);
		
		setupBlankBoard();
		setupMenu();
		setupFrame();
	}

	private void setupBlankBoard() {
		
		// First square is light.
		boolean lightSquare = true;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				
				// Creates the JButton with default style
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
				button.addMouseListener(mouseListner);
				button.setOpaque(true);
				button.setFocusable(false);
				
				Border line = BorderFactory.createLineBorder(Color.BLACK, 1);
				
				button.setBorder(line);
				
				// Adds an actionCommand for the button with the
				// coordinates of its location on the board: r,c.
				String name = r + "," + c;
				button.setActionCommand(name);
				
				// Chooses the proper background color (light or dark)
				// depending on the boolean value.
				Color bg = dark;
				
				if (lightSquare) {
					bg = light;
				}
				
				button.setBackground(bg);
				
				board[r][c] = button;
				topWindow.add(button);
								
				// Switches from dark to light or light to dark to 
				// prepare for the next column.
				lightSquare = !lightSquare;
			}
			
			// Switches from dark to light or light to dark to 
			// prepare for the next row.
			lightSquare = !lightSquare;
		}
		
		topWindow.pack();
		topWindow.setVisible(true);
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
		menuBar.setBorderPainted(false);
		
		menu.add(item);
		menuBar.add(menu);
		
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
	public void setSelected(int row, int col) {
		board[row][col].setBackground(selected);
	}
	
	private void setDeselected(int row, int col) {
		Color bg = light;
		
		/* Checks for even or odd row. */
		if (row % 2 == 0) {
			
			/* Checks for odd column. */
			if (col % 2 != 0) {
				bg = dark;
			}
		} else {
			
			/* Checks for even column. */
			if (col % 2 == 0) {
				bg = dark;
			}
		}
		
		board[row][col].setBackground(bg);
	}
	
	@Override
	public void deselectAll() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				setDeselected(r, c);
			}
		}
	}
	
	@Override
	public void setHighlighted(int row, int col) {
		board[row][col].setBackground(highlighted);
	}
	
	@Override
	public void changeImage(int row, int col, String type, boolean white) {
		ImageIcon img = imageFinder(type, white);
		
		// The amount of extra space between the edge of 
		// the button an the edge of the image. Default 5.
		final int borderSpace = 5;
		
		img = resizeImage(img, IMG_SIZE - borderSpace);
		
		board[row][col].setIcon(img);
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
	
	@Override
	public void setMoveHandler(ActionListener mh) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c].addActionListener(mh);
			}
		}
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
	
	/** Adds new outlines to buttons when they're being interacted with. */
	private MouseListener mouseListner = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			JButton button = (JButton) e.getComponent();
			
			Border border = BorderFactory.createRaisedSoftBevelBorder();
			button.setBorder(border);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			JButton button = (JButton) e.getComponent();	
			
			Border border = BorderFactory.createLoweredSoftBevelBorder();
			button.setBorder(border);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			JButton button = (JButton) e.getComponent();
			
			Border line = BorderFactory.createLineBorder(Color.BLACK, 1);
			button.setBorder(line);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			JButton button = (JButton) e.getComponent();
			
			Border border = BorderFactory.createRaisedSoftBevelBorder();
			button.setBorder(border);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) { }
	};
}
