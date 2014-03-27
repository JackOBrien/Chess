package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.LayerUI;

import view.colors.ColorController;

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

	/** Name of the folder containing the images of GUI icons. */
	private final String path = "images/";
	
	/** Image of a very small GVSU logo. */
	private ImageIcon gvsu = ImageLoader.loadIcon(path + "GVSUlogoSmall.png");
	
	/** Image of a small exit sign. */
	private ImageIcon exit = ImageLoader.loadIcon(path + "exit.png");
	
	/** The size of the images and buttons on the board. Default 60. */
	private static final int IMG_SIZE = 60;
	
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
	private ChessTile[][] board;
	
	/** The panel that contains the board. */
	private JPanel buttonPanel;
	
	/** The panel that contains captured pieces. */
	private JPanel capturedPanel;
	
	/** The frame containing the entire game. */
	private JFrame topWindow;
	
	/** LayerUI to blur the board. */
	private LayerUI<JComponent> layerUI;
	
	/** JLayer to blur the board. */
	private JLayer<JComponent> jLayer;
	
	/** ActionListiner for the promotion options. */
	private ActionListener promotionListener;
	
	/****************************************************************
	 * The graphical user interface for Chess made up of JButtons. 
	 * 
	 * @param numRows the number of rows on the board.
	 * @param numCols the number of columns on the board.
	 ***************************************************************/
	public ChessGUI(final int numRows, final int numCols) {
		topWindow = new JFrame();
		topWindow.setLayout(new BorderLayout());
		
		// Doesn't allow the color to change when pressed
		UIManager.put("Button.select", Color.TRANSLUCENT);
		
		board = new ChessTile[numRows][numCols];
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(numRows, numCols));
		
		// Starting board color
		setBoardColors(ColorController.RED);
		
		layerUI = new BlurLayerUI();
		
		setupBlankBoard();
		setupCapturedPanel();
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
				
				// Creates the chess tile with default style
				ChessTile button = new ChessTile(IMG_SIZE);
				
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
				
				button.setDefaultBackground(bg);
//				button.setSpecialBackground(accent); TODO
				
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
		
		topWindow.add(buttonPanel, BorderLayout.LINE_START); 
	}
	
	/****************************************************************
	 * Sets up the JFrame.
	 ***************************************************************/
	private void setupFrame() {
		final int kingLogoSize = 50;
		
		List<Image> al = new ArrayList<Image>();
		al.add(gvsu.getImage());
		ImageIcon icon = ChessIcon.W_KING.getIcon();
		al.add(ImageLoader.resizeImage(icon, kingLogoSize).getImage());
				
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
		exitItem.setIcon(ImageLoader.resizeImage(exit, iconSize));
		exitItem.addActionListener(menuListener);
		exitItem.setActionCommand("Exit");
		
		fileMenu.setForeground(Color.WHITE);
		
		menuBar.setLayout(new BorderLayout());
		menuBar.setBorderPainted(false);
		
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		topWindow.setJMenuBar(menuBar);
	}
	
	private void setupCapturedPanel() {
		capturedPanel = new JPanel();
		capturedPanel.setLayout(new GridLayout(10, 0));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		capturedPanel.setBorder(border);
		capturedPanel.setBackground(promotion);
		
		topWindow.add(capturedPanel, BorderLayout.CENTER);
	}
	
	private void setBoardColors(int color) {
		ColorController palette = new ColorController(color);

		// Define default colors.
		light = palette.getLight();
		dark = palette.getDark();
		selected = palette.getSelected();
		highlighted = palette.getHighlighted();
		promotion = palette.getPromotion();
		accent = palette.getAccent();
	}	

	@Override
	public final void setSelected(final int row, final int col) {
		board[row][col].setBackground(selected);
		
		JButton b = new JButton();
		ImageIcon ico = ImageLoader.resizeImage(
				ChessIcon.B_BISH.getIcon(), 45);
		b.setDisabledIcon(ico);
		b.setIcon(ico);
		b.setEnabled(false);
		b.setBackground(promotion);
		b.setFocusable(false);
		b.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		capturedPanel.add(b);
		topWindow.pack();
	}
	
	/****************************************************************
	 * Changes the background of the button at the given location
	 * back to its default color.
	 * 
	 * @param row row location of the button.
	 * @param col column location of the button.
	 ***************************************************************/
	private void setDeselected(final int row, final int col) {
		board[row][col].resetColor();
	}
	
	@Override
	public final void deselectAll() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				setDeselected(r, c);
			}
		}
	}
	
	@Override
	public final void setHighlighted(final int row, final int col) {
		board[row][col].setBackground(highlighted);
	}
	
	@Override
	public final void changeImage(final int row, final int col, 
			final String type, final boolean white) {
		ImageIcon img = ChessIcon.findIcon(type, white);
		
		img = ImageLoader.resizeImage(img, IMG_SIZE);
		
		board[row][col].setIcon(img);
	}
	
	@Override
	public final void setMoveHandler(final ActionListener mh) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c].addActionListener(mh);
			}
		}
	}
	
	@Override
	public final void setPromotionHandler(final ActionListener ph) {
		promotionListener = ph;
	}
	
	@Override
	public final void pawnPromotion(final int row, final int col, 
			final boolean white) {
		final double sizeModifier = .667;
		final int size = (int) (IMG_SIZE * sizeModifier);
		
		PromotionDialog dialog = new PromotionDialog(white, size, 
				promotion, accent);
		dialog.addFocusListener(focusListener);
		
		// Find all relevant images
		ImageIcon rookIcon = ChessIcon.findIcon("Rook", white);
		ImageIcon knightIcon = ChessIcon.findIcon("Knight", white);
		ImageIcon bishopIcon = ChessIcon.findIcon("Bishop", white);
		ImageIcon queenIcon = ChessIcon.findIcon("Queen", white);
		
		// Re-size all found images
		rookIcon = ImageLoader.resizeImage(rookIcon, size);
		knightIcon = ImageLoader.resizeImage(knightIcon, size);
		bishopIcon = ImageLoader.resizeImage(bishopIcon, size);
		queenIcon = ImageLoader.resizeImage(queenIcon, size);
		
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
	public final void gameInCheck(final boolean white) {
		String message = "White ";
		ImageIcon icon = ChessIcon.W_KING.getIcon();
		final int extraSize = 10;
		
		if (!white) {
			message = "Black ";
			icon = ChessIcon.B_KING.getIcon();
		}
		message += "is in check!";
		String title = "Check";
		icon = ImageLoader.resizeImage(icon, IMG_SIZE + extraSize);
		
		JOptionPane.showMessageDialog(buttonPanel, message, title, 
				JOptionPane.INFORMATION_MESSAGE, icon);
	}
	
	@Override
	public final void gameOver(final boolean white) {
		String message = "Checkmate! ";
		ImageIcon icon = ChessIcon.W_KING.getIcon();
		final int extraSize = 10;
		
		if (!white) {
			message += "Black ";
			icon = ChessIcon.B_KING.getIcon();
		} else {
			message += "White ";
		}
		message += "won the game!";
		String title = "Game Over";
		icon = ImageLoader.resizeImage(icon, IMG_SIZE + extraSize);
		
		JButton playAgain = new JButton("Play Again");
		playAgain.setEnabled(false);
		JButton quit = new JButton("Quit");
		quit.addActionListener(menuListener);
		quit.setActionCommand("Exit");
		JButton[] options = {playAgain, quit};
		
		JOptionPane.showOptionDialog(buttonPanel, message, title, 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, 
				options, quit);
	}
	
	/** Listener for the menu items. */
	private ActionListener menuListener = new ActionListener() {
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			String source = e.getActionCommand();
			
			if (source.equals("Exit")) {
				System.exit(0);
			}			
		}
	};
	
	private void blurBoard() {
		jLayer = new JLayer<JComponent>(buttonPanel, layerUI);
		
		topWindow.remove(buttonPanel);
		topWindow.add(jLayer);
		topWindow.validate();
	}
	
	private void unBlurBoard() {
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
	
	/** Blurs the board when it goes out of focus. */
	private FocusListener focusListener = new FocusListener() {
		
		@Override
		public void focusLost(final FocusEvent e) {						
			unBlurBoard();
		}
		
		@Override
		public void focusGained(final FocusEvent e) {
			blurBoard();
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
	public void paint(final Graphics g, final JComponent c) {
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

