package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.LayerUI;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Graphical user interface of a chess game.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public class ChessGUI implements IChessUI {

	/** Name of the folder containing the images of the game pieces. */
	private final String path = "images\\";
	
	/** Image for the white game pieces. */
	private ImageIcon wBish = loadIcon(path + "w_bish.png"),
	  wKing = loadIcon(path + "w_king.png"),
	  wKnight = loadIcon(path + "w_knight.png"),
	  wPawn = loadIcon(path + "w_pawn.png"),
	  wQueen = loadIcon(path + "w_queen.png"),
	  wRook = loadIcon(path + "w_rook.png");

	/** Image for the black game pieces. */
	private ImageIcon bBish = loadIcon(path + "b_bish.png"),
	 bKing = loadIcon(path + "b_king.png"),
	 bKnight = loadIcon(path + "b_knight.png"),
	 bPawn = loadIcon(path + "b_pawn.png"),
	 bQueen = loadIcon(path + "b_queen.png"),
	 bRook = loadIcon(path + "b_rook.png");
	
	/** Image of a very small GVSU logo. */
	private ImageIcon gvsu = loadIcon(path + "GVSUlogoSmall.png");
	
	/** Image of a small exit sign. */
	private ImageIcon exit = loadIcon(path + "exit.png");
	
	/** The size of the images and buttons on the board. Default 60. */
	private static final int IMG_SIZE = 60;
	
	/** The amount of extra space between the edge of the button 
	 * and the edge of the image. Default 5. */
	private final int borderBuffer = 5;
	
	/** The color for the light spaces on the board. */
	private Color light;
	
	/** The color for the dark spaces on the board. */
	private Color dark;
	
	/** The color used for pieces that are selected. */
	private Color selected;
	
	/** The color for highlighted squares such as valid moves. */
	private Color highlighted;
	
	/** The background color for the promotion options. */
	private Color promotion;
	
	/** An accent color used for the menu bar and promotion dialog. */
	private Color accent;
	
	/** The game board represented as buttons. */
	private JButton[][] board;
	
	/** The panel that contains the board. */
	private JPanel buttonPanel;
	
	/** The frame containing the entire game. */
	private JFrame topWindow;
	
	/** LayerUI to blur the board. */
	private LayerUI<JComponent> layerUI;
	
	/** JLayer to blur the board. */
	private JLayer<JComponent> jLayer;
	
	/** MouseListener that will bevel buttons when interacted with. */
	private BevelOnHover mouseListener;
	
	/** ActionListiner for the promotion options. */
	private ActionListener promotionListener;
	
	/****************************************************************
	 * The graphical user interface for Chess made up of JButtons. 
	 * 
	 * @param numRows the number of rows on the board.
	 * @param numCols the number of columns on the board.
	 ***************************************************************/
	public ChessGUI(int numRows, int numCols) {
		topWindow = new JFrame();
		
		// Doesn't allow the color to change when pressed
		UIManager.put("Button.select", Color.TRANSLUCENT);
		
		board = new JButton[numRows][numCols];
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(numRows, numCols));
				
		// Define default colors.
		light = BoardColors.LIGHT;
		dark = BoardColors.DARK;
		selected = BoardColors.SELECTED;
		highlighted = BoardColors.HIGHLIGHTED;
		promotion = BoardColors.PROMOTION;
		accent = BoardColors.ACCENT;
		
		layerUI = new BlurLayerUI();
		
		mouseListener = new BevelOnHover(Color.BLACK);
		
		setupBlankBoard();
		setupMenu();
		setupFrame();
	}

	/****************************************************************
	 * Sets up the board for the start of a game.
	 ***************************************************************/
	private void setupBlankBoard() {
		
		// First square is light.
		boolean lightSquare = true;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				
				// Creates the JButton with default style
				JButton button = createDefaultButton();
				
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
				buttonPanel.add(button);
								
				// Switches from dark to light or light to dark to 
				// prepare for the next column.
				lightSquare = !lightSquare;
			}
			
			// Switches from dark to light or light to dark to 
			// prepare for the next row.
			lightSquare = !lightSquare;
		}
		
		topWindow.add(buttonPanel); 
	}
	
	/****************************************************************
	 * Sets up the JFrame.
	 ***************************************************************/
	private void setupFrame() {
		final int kingLogoSize = 50;
		
		List<Image> al = new ArrayList<Image>();
		al.add(gvsu.getImage());
		al.add(resizeImage(wKing, kingLogoSize).getImage());
		
		topWindow.addFocusListener(focusListener);
		
		topWindow.setTitle("CIS 350: Chess Game");
		topWindow.setIconImages(al);
		topWindow.pack();
		topWindow.setResizable(false);
		topWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		topWindow.setVisible(true);
	}
	
	/****************************************************************
	 * Sets up the frame's menus.
	 ***************************************************************/
	private void setupMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.setBackground(accent);
		final int iconSize = 20;
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setIcon(resizeImage(exit, iconSize));
		exitItem.addActionListener(menuListener);
		exitItem.setActionCommand("Exit");
		
		fileMenu.setForeground(Color.WHITE);
		
		menuBar.setLayout(new BorderLayout());
		menuBar.setBorderPainted(false);
		
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		topWindow.setJMenuBar(menuBar);
	}
	
	/****************************************************************
	 * Creates a default button with no image.
	 * 
	 * @return a fully setup JButton with no image.
	 ***************************************************************/
	private JButton createDefaultButton() {
		return createDefaultButton(null);
	}
	
	/****************************************************************
	 * Creates a default button with the given image.
	 * 
	 * @param icon image to be displayed on the button.
	 * @return a fully setup JButton with the given image.
	 ***************************************************************/
	private JButton createDefaultButton(ImageIcon icon) {
		JButton button = new JButton(icon);
		button.setPreferredSize(new Dimension(IMG_SIZE, IMG_SIZE));
		button.setSize(new Dimension(IMG_SIZE, IMG_SIZE));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addMouseListener(mouseListener);
		button.setOpaque(true);
		button.setFocusable(false);
		
		Border line = BorderFactory.createLineBorder(Color.BLACK, 1);
		button.setBorder(line);
		
		return button;
	}
	
	/****************************************************************
	 * Static method to load the ImageIcon from the given location.
	 * 
	 * @param name Name of the file.
	 * @return the requested image.
	 ***************************************************************/
	private static  ImageIcon loadIcon(String name) {
		java.net.URL imgURL = ChessGUI.class.getResource(name);
		if (imgURL == null) {
			throw new RuntimeException("Icon resource not found.");
		}  

		return new ImageIcon(imgURL);
	}
	

	/****************************************************************
	 * Takes an ImageIcon and changes its size.
	 * 
	 * @param icon the ImageIcon to be changed.
	 * @param size the new width and height of the image.
	 * @return the resized ImageIcon.
	 ***************************************************************/
	private ImageIcon resizeImage(ImageIcon icon, int size) {
		
		size -= borderBuffer;
		
		if (icon == null) { return icon; }
		
		Image img = icon.getImage();
				
		Image resized = img.getScaledInstance(size, size, 
				Image.SCALE_AREA_AVERAGING);
		return new ImageIcon(resized);
	}

	@Override
	public void setSelected(int row, int col) {
		board[row][col].setBackground(selected);
	}
	
	/****************************************************************
	 * Changes the background of the button at the given location
	 * back to its default color.
	 * 
	 * @param row row location of the button.
	 * @param col column location of the button.
	 ***************************************************************/
	private void setDeselected(int row, int col) {
		Color bg = light;
		
		/* Checks for dark square. */
		if ((row + col) % 2 != 0) {
			bg = dark;
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
		
		img = resizeImage(img, IMG_SIZE);
		
		board[row][col].setIcon(img);
	}
	
	/****************************************************************
	 * Helper method to return the image that matches the
	 * described chess piece.
	 * 
	 * @param type the name of the piece, i.e. "King".
	 * @param white tells if the piece is white or not.
	 * @return  image corresponding to the piece described.
	 ***************************************************************/
	private ImageIcon imageFinder(String type, boolean white) {		
		ImageIcon image = null;
				
		if (white) {
			image = assignWhiteImage(type);
		} else {
			image = assignBlackImage(type);
		}
		
		return image;
	}
	
	/****************************************************************
	 * Finds the proper white piece for the given type.
	 * 
	 * TODO: Remove this for Release 3.
	 * 
	 * @param type name of the piece's type.
	 * @return the proper image of the described piece.
	 ***************************************************************/
	private ImageIcon assignWhiteImage(String type) {
		ImageIcon image;
		
		switch(type) {
		case "Bishop":
			image = wBish;
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
		
		return image;
	}
	
	/****************************************************************
	 * Finds the proper black piece for the given type.
	 * 
	 * TODO: Remove this for Release 3.
	 * 
	 * @param type name of the piece's type.
	 * @return the proper image of the described piece.
	 ***************************************************************/
	private ImageIcon assignBlackImage(String type) {
		ImageIcon image;

		switch(type) {
		case "Bishop":
			image = bBish;
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
	
	@Override
	public void setPromotionHandler(ActionListener ph) {
		promotionListener = ph;
	}
	
	@Override
	public void pawnPromotion(int row, int col, boolean white) {
		final double sizeModifier = .667;
		final int size = (int) (IMG_SIZE * sizeModifier);
		
		PromotionDialog dialog = new PromotionDialog(white, size, 
				promotion, accent);
		
		// Find all relevant images
		ImageIcon rookIcon = imageFinder("Rook", white);
		ImageIcon knightIcon = imageFinder("Knight", white);
		ImageIcon bishopIcon = imageFinder("Bishop", white);
		ImageIcon queenIcon = imageFinder("Queen", white);
		
		// Re-size all found images
		rookIcon = resizeImage(rookIcon, size);
		knightIcon = resizeImage(knightIcon, size);
		bishopIcon = resizeImage(bishopIcon, size);
		queenIcon = resizeImage(queenIcon, size);
		
		// Set all relevant images for the dialog 
		dialog.setRookImage(rookIcon);
		dialog.setKnightImage(knightIcon);
		dialog.setBishopImage(bishopIcon);
		dialog.setQueenImage(queenIcon);
		
		// Add the ActionListener to the dialog's buttons
		dialog.setActionListener(promotionListener);
						
		int direction = white ? 1 : -1;
		final int offset = 4;
		
		dialog.setLocationRelativeTo(board[row][col]);
		int x = (int) dialog.getLocation().getX();
		int y = (int) dialog.getLocation().getY();
		y += (IMG_SIZE + offset) * direction;
		dialog.setLocation(x, y);
		
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	@Override
	public void gameInCheck(boolean white) {
		String message = "White ";
		ImageIcon icon = wKing;
		final int extraSize = 10;
		
		if (!white) {
			message = "Black ";
			icon = bKing;
		}
		message += "is in check!";
		String title = "Check";
		icon = resizeImage(icon, IMG_SIZE + extraSize);
		
		JOptionPane.showMessageDialog(buttonPanel, message, title, 
				JOptionPane.INFORMATION_MESSAGE, icon);
	}
	
	@Override
	public void gameOver(boolean white) {
		String message = "Checkmate! ";
		ImageIcon icon = wKing;
		final int extraSize = 10;
		
		if (!white) {
			message += "Black ";
			icon = bKing;
		} else {
			message += "White ";
		}
		message += "won the game!";
		String title = "Game Over";
		icon = resizeImage(icon, IMG_SIZE + extraSize);
		
		JButton playAgain = new JButton("Play Again");
		playAgain.setEnabled(false);
		JButton quit = new JButton("Quit");
		JButton[] options = {playAgain, quit};
		
		JOptionPane.showOptionDialog(buttonPanel, message, title, 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, 
				options, quit);
	}
	
	/** Listener for the menu items. */
	private ActionListener menuListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String source = e.getActionCommand();
			
			if (source.equals("Exit")) {
				System.exit(0);
			}
			
		}
	};
	
	/** Blurs the board when it goes out of focus. */
	private FocusListener focusListener = new FocusListener() {
		
		@Override
		public void focusLost(FocusEvent e) {						
			
			jLayer = new JLayer<JComponent>(buttonPanel, layerUI);
			
			topWindow.remove(buttonPanel);
			topWindow.add(jLayer);
			topWindow.validate();
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			Dimension d = topWindow.getSize();
			
			try {
				topWindow.remove(jLayer);
			} catch (NullPointerException exc) {
				return;
			}
			topWindow.add(buttonPanel);
			topWindow.pack();
			topWindow.setSize(d);
		}
	};
}

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Will blur the panel it is applied to.
 *
 * @author http://docs.oracle.com/javase/tutorial/uiswing/misc/jlayer.html
 * @version Mar 15, 2014
 *******************************************************************/
class BlurLayerUI extends LayerUI<JComponent> {
	
	/** Default version UID. */
	private static final long serialVersionUID = 1L;
	
	/** Off-screen image. */
	private BufferedImage mOffscreenImage;
	
	/** Operation Buffered Image. */
	private BufferedImageOp mOperation;

	/****************************************************************
	 * Constructor for the LayerUI.
	 ***************************************************************/
	public BlurLayerUI() {
		float amount = 0.4f / 11.0f;
		float[] blurKernel = {
				amount, amount, amount,
				amount, amount, amount,
				amount, amount, amount
		};
		final int kernal = 3;
		mOperation = new ConvolveOp(
				new Kernel(kernal, kernal, blurKernel),
				ConvolveOp.EDGE_NO_OP, null);
	}

	@Override
	public void paint (Graphics g, JComponent c) {
		int w = c.getWidth();
		int h = c.getHeight();

		if (w == 0 || h == 0) {
			return;
		}

		// Only create the off-screen image if the one we have
		// is the wrong size.
		if (mOffscreenImage == null 
				|| mOffscreenImage.getWidth() != w 
				|| mOffscreenImage.getHeight() != h) {
			mOffscreenImage = new BufferedImage(w, h, 
					BufferedImage.TYPE_INT_RGB);
		}

		Graphics2D ig2 = mOffscreenImage.createGraphics();
		ig2.setClip(g.getClip());
		super.paint(ig2, c);
		ig2.dispose();

		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(mOffscreenImage, mOperation, 0, 0);
	}
}

