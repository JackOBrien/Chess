package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
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
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.LayerUI;

import model.Move;
import view.colors.ColorController;
import view.sound.SoundEffect;

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
	
	/** Image of a painter's palette. */
	private ImageIcon paint = ImageLoader.loadIcon(path + "palette.png");
	
	/** Image of a reset icon. */
	private ImageIcon reset = ImageLoader.loadIcon(path + "reset.png");
	
	/** The size of the images and buttons on the board. Default 60. */
	private int imageSize;
	
	/** The quality of the images as a percentage. */
	private double imageQuality;
	
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
	
	/** JFrames menu bar. */
	private JMenuBar menuBar;
	
	/** Menu item to reset the game. */
	private JMenuItem resetItem;
	
	/** Menu item to change the size of the game. */
	private JMenuItem sizeItem;
	
	/** The frame containing the entire game. */
	private JFrame topWindow;
	
	/** Timer to count 1/10 of a second for white. */
	private Timer wTimer;
	
	/** Timer to count 1/10 of a second for black. */
	private Timer bTimer;
	
	/** Tells how long it has been white's turn. */
	private JLabel whiteTime;
	
	/** Tells how long it has been black's turn. */
	private JLabel blackTime;
	
	/** Clock to handle white's time. */
	private Clock wClock;
	
	/** Clock to handle black's time. */
	private Clock bClock;
	
	/** LayerUI to blur the board. */
	private LayerUI<JComponent> layerUI;
	
	/** JLayer to blur the board. */
	private JLayer<JComponent> jLayer;
	
	/** ActionListener for resetting the game. */
	private ActionListener resetListener;
	
	/** ActionListener for the promotion options. */
	private ActionListener promotionListener;
	
	/** Font that changes size with the board. */
	private Font dynFont;
	
	/** The board's current color palette. */
	private int colorPalette;
	
	/** Tells if the game has started or not. */
	private boolean started;
	
	/** Tells if the board should highlight valid moves or not. */
	private boolean highlightValid;
	
	/** NUmber of rows on the board. */
	private int numRows;
	
	/** NUmber of columns on the board. */
	private int numCols;
	
	/****************************************************************
	 * The graphical user interface for Chess made up of JButtons. 
	 * 
	 * @param rows the number of rows on the board.
	 * @param cols the number of columns on the board.
	 ***************************************************************/
	public ChessGUI(final int rows, final int cols) {
		
		// Doesn't allow the color to change when pressed
		UIManager.put("Button.select", Color.TRANSLUCENT);
		
		// Record size of the board
		numRows = rows;
		numCols = cols;
		
		imageSize = ResizeDialog.STD_SIZE;
		imageQuality = 1;
		
		// Starting board color
		setBoardColors(ColorController.RED);
		
		// Dynamic font size
		final double multi = .25;
		final int fontSize = (int) (imageSize * multi);
		dynFont = new Font("Calibri", Font.BOLD, fontSize);
				
		layerUI = new BlurLayerUI();
		
		resetGame();
	}
	
	@Override
	public final void resetGame() {
		
		boolean needsLocation = false;
		Point p = null;
		if (topWindow != null) {
			p = topWindow.getLocationOnScreen();
			topWindow.dispose();
			needsLocation = true;
		}
		topWindow = new JFrame();
		
		if (needsLocation) {
			topWindow.setLocation(p);
		}

		started = false;
		highlightValid = true;
		
		setupBlankBoard();
		setupCapturedPanel();
		setupMenu();
		setupTimers();
		setupFrame();
	}

	/****************************************************************
	 * Sets up the board for the start of a game.
	 ***************************************************************/
	private void setupBlankBoard() {
		
		board = new ChessTile[numRows][numCols];
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(numRows, numCols));
		
		// First square is light.
		boolean lightSquare = true;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				
				// Creates the chess tile with default style
				ChessTile button = new ChessTile(imageSize);
				
				// Adds an actionCommand for the button with the
				// coordinates of its location on the board: r,c.
				String name = r + "," + c;
				button.setActionCommand(name);
				
				// Chooses the proper background color (light or dark)
				// depending on the boolean value.
				Color bg = dark;
				boolean isLight = false;
				
				if (lightSquare) {
					bg = light;
					isLight = true;
				}
				
				button.setDefaultBackground(bg);
				button.setIsLight(isLight);
				
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
		ImageIcon icon = ChessIcon.W_KING.getIcon();
		al.add(ImageLoader.resizeImage(icon, kingLogoSize,
				imageQuality).getImage());
				
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
		fileMenu.setFont(dynFont);
		menuBar = new JMenuBar();
		menuBar.setLayout(new BorderLayout());
		
		menuBar.setBackground(accent);
		final int iconSize = 20;
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setIcon(ImageLoader.resizeImage(exit, iconSize, imageQuality));
		exitItem.addActionListener(menuListener);
		exitItem.setActionCommand("Exit");
		
		resetItem = new JMenuItem("Reset Game");
		resetItem.setIcon(ImageLoader.resizeImage(reset, 
				iconSize, imageQuality));
		resetItem.setActionCommand("menuReset");
		
		JMenuItem colorItem = new JMenuItem("Change Colors");
		colorItem.setIcon(ImageLoader.resizeImage(paint, 
				iconSize, imageQuality));
		colorItem.addActionListener(menuListener);
		colorItem.setActionCommand("Color");
		
		sizeItem = new JMenuItem("Change Size");
		sizeItem.setIcon(ImageLoader.resizeImage(ChessIcon.W_KING.getIcon(), 
				iconSize, imageQuality));
		sizeItem.setActionCommand("Size");
		
		JCheckBoxMenuItem validItem = new JCheckBoxMenuItem("Show Valid Moves");
		validItem.addActionListener(menuListener);
		validItem.setActionCommand("Valid");
		validItem.setSelected(true);
		
		fileMenu.setForeground(Color.WHITE);
		
		menuBar.setLayout(new BorderLayout());
		menuBar.setBorderPainted(false);
		
		fileMenu.add(exitItem);
		fileMenu.add(resetItem);
		fileMenu.add(colorItem);
		fileMenu.add(sizeItem);
		fileMenu.add(validItem);
		
		menuBar.add(fileMenu, BorderLayout.LINE_START);
		
		topWindow.setJMenuBar(menuBar);
	}
	
	/****************************************************************
	 * Sets up the panel which displays pieces that have been captured.
	 * 
	 * THIS FEATURE IS NOT IMPLEMENTED IN RELEASE 3.
	 ***************************************************************/
	private void setupCapturedPanel() {
		capturedPanel = new JPanel();
		final int ten = 10;
		capturedPanel.setLayout(new GridLayout(ten, 0));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		capturedPanel.setBorder(border);
		capturedPanel.setBackground(promotion);
	}
	
	/****************************************************************
	 * Sets up the game timers for both players.
	 ***************************************************************/
	private void setupTimers() {
		final int delay = 100; // 1/10th second
		wTimer = new Timer(delay, timeListener);
		bTimer = new Timer(delay, timeListener);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setOpaque(false);
		final float panelMultiplier = 4.166f;
		final int panelWidth = (int) (panelMultiplier * imageSize);
		labelPanel.setPreferredSize(new Dimension(panelWidth, 0));
		
		wClock = new Clock();
		whiteTime = new JLabel("White:  " + wClock.toString());
		whiteTime.setForeground(Color.LIGHT_GRAY);
		whiteTime.setFont(dynFont);
		
		bClock = new Clock();
		blackTime = new JLabel("Black:  " + bClock.toString());
		blackTime.setForeground(Color.LIGHT_GRAY);		
		blackTime.setFont(dynFont);
		
		labelPanel.add(whiteTime);
		
		// Filler space
		final int divisor = 6;
		final int twelve = 12;
		final int fillerSize = (panelWidth / divisor) - twelve;
		labelPanel.add(Box.createRigidArea(new Dimension(fillerSize, 0))); 
		
		labelPanel.add(blackTime);
		
		menuBar.add(labelPanel, BorderLayout.LINE_END);
	}
	
	/****************************************************************
	 * Sets the colors of the board based on the given color paltte.
	 * 
	 * @param color the color palette to be used.
	 ***************************************************************/
	private void setBoardColors(final int color) {
		ColorController palette = new ColorController(color);
		colorPalette = color;

		// Define default colors.
		light = palette.getLight();
		dark = palette.getDark();
		selected = palette.getSelected();
		highlighted = palette.getHighlighted();
		promotion = palette.getPromotion();
		accent = palette.getAccent();
	}	
	
	/****************************************************************
	 * Updates the colors of the board.
	 ***************************************************************/
	private void updateBoardColor() {
		
		menuBar.setBackground(accent);
		
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				ChessTile tile = board[r][c];
				
				if (tile == null) { continue; }
				
				if (tile.isLight()) {
					tile.setDefaultBackground(light);
				} else {
					tile.setDefaultBackground(dark);
				}
				
				if (tile.isState(ChessTile.SELECTED)) {
					tile.setBackground(selected);
				} else if (tile.isState(ChessTile.HIGHLIGHTED)) {
					tile.setBackground(highlighted);
				}
				
				tile.validate();
			}
		}
	}

	@Override
	public final void setSelected(final int row, final int col) {
		board[row][col].setBackground(selected);
		board[row][col].setState(ChessTile.SELECTED);
		
		started = true;
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
	
	/****************************************************************
	 * Toggles the highlight of all tiles.
	 * 
	 * @param highlight if true, tiles are highlighted. If false, 
	 * highlighted tiles have their color reset.
	 ***************************************************************/
	private void highlightAll(final boolean highlight) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].isState(ChessTile.HIGHLIGHTED)) {
					
					if (!highlight) {
						setDeselected(r, c);
					} else {
						setHighlighted(r, c);
					}
				}
			}
		}
	}
	
	@Override
	public final void setHighlighted(final int row, final int col) {
		board[row][col].setState(ChessTile.HIGHLIGHTED);
		
		if (highlightValid) { 
			board[row][col].setBackground(highlighted);
		}
	}
	
	@Override
	public final void changeImage(final int row, final int col, 
			final String type, final boolean white) {
		
		if (type.isEmpty()) {
			board[row][col].setIcon(null);
			return;
		}
		
		ImageIcon img = ChessIcon.findIcon(type, white);
		
		img = ImageLoader.resizeImage(img, imageSize, imageQuality);
		
		board[row][col].setIcon(img);
	}
	
	@Override
	public final void move(final Move m, String type
			, final boolean white) {
		
		boolean attacking = board[m.toRow()][m.toColumn()].getIcon() != null;
		
		if (type.equals("passant")) {
			type = "Pawn";
			attacking  = true;
		}
		
		if (attacking) {
			SoundEffect.CAPTURE.play();
		} else if (white) {
			SoundEffect.W_MOVE.play();
		} else {
			SoundEffect.B_MOVE.play();
		}
		
		changeImage(m.toRow(), m.toColumn(), type, white);
		changeImage(m.fromRow(), m.fromColumn(), "", white);
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
	public final void setResetHandler(final ActionListener rh) {
		resetItem.addActionListener(rh);
		sizeItem.addActionListener(rh);
		resetListener = rh;
	}
	
	@Override
	public final void setFocusHandler(final FocusListener fh) {
		topWindow.addFocusListener(fh);
	}
	
	@Override
	public final void setPromotionHandler(final ActionListener ph) {
		promotionListener = ph;
	}
	
	@Override
	public final void pawnPromotion(final int row, final int col, 
			final boolean white) {
		final double sizeModifier = .667;
		final int size = (int) (imageSize * sizeModifier);
		
		PromotionDialog dialog = new PromotionDialog(white, size, 
				promotion, accent);
		dialog.addFocusListener(blurListener);
		
		// Find all relevant images
		ImageIcon rookIcon = ChessIcon.findIcon("Rook", white);
		ImageIcon knightIcon = ChessIcon.findIcon("Knight", white);
		ImageIcon bishopIcon = ChessIcon.findIcon("Bishop", white);
		ImageIcon queenIcon = ChessIcon.findIcon("Queen", white);
		
		// Re-size all found images
		rookIcon = ImageLoader.resizeImage(rookIcon, size, imageQuality);
		knightIcon = ImageLoader.resizeImage(knightIcon, size, imageQuality);
		bishopIcon = ImageLoader.resizeImage(bishopIcon, size, imageQuality);
		queenIcon = ImageLoader.resizeImage(queenIcon, size, imageQuality);
		
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
		y += (imageSize + offset) * direction;
		dialog.setLocation(x, y);
		
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	@Override
	public final void changeBoardSize() {
		
		new ResizeDialog(imageSize, imageQuality);
		
		imageSize = ResizeDialog.getBoardSize();
		imageQuality = ResizeDialog.getBoardQuality();
		
		
	}
	
	@Override
	public final void gameInCheck(final boolean white) {
		String message = "White ";
		ImageIcon icon = ChessIcon.W_KING.getIcon();
		final int extraSize = 10;
		
		SoundEffect.CHECK.play();
		
		if (!white) {
			message = "Black ";
			icon = ChessIcon.B_KING.getIcon();
		}
		message += "is in check!";
		String title = "Check";
		icon = ImageLoader.resizeImage(icon, imageSize + extraSize,
				imageQuality);
		
		JOptionPane.showMessageDialog(buttonPanel, message, title, 
				JOptionPane.INFORMATION_MESSAGE, icon);
	}
	
	@Override
	public final void gameOver(final boolean white) {		
		String message = "Checkmate! ";
		ImageIcon icon = ChessIcon.W_KING.getIcon();
		final int extraSize = 10;
		
		SoundEffect.GAME_OVER.play();
		
		if (!white) {
			message += "Black ";
			icon = ChessIcon.B_KING.getIcon();
		} else {
			message += "White ";
		}
		message += "won the game!";
		String title = "Game Over";
		icon = ImageLoader.resizeImage(icon, imageSize + extraSize, 
				imageQuality);
		
		JButton playAgain = new JButton("Play Again");
		playAgain.addActionListener(resetListener);
		playAgain.setActionCommand("gameOver");
		JButton quit = new JButton("Quit");
		quit.addActionListener(menuListener);
		quit.setActionCommand("Exit");
		JButton[] options = {playAgain, quit};
		
		JOptionPane.showOptionDialog(buttonPanel, message, title, 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, 
				options, quit);
	}
	
	@Override
	public final void startTimer(final boolean white) {
		
		if (!started) { return; }
		
		if (white) {
			wTimer.start();
			bTimer.stop();
		} else {
			wTimer.stop();
			bTimer.start();
		}
	}
	
	@Override
	public final void stopTimers() {
		wTimer.stop();
		bTimer.stop();
	}
	
	/** Listener for the menu items. */
	private ActionListener menuListener = new ActionListener() {
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			String source = e.getActionCommand();
			
			if (source.equals("Exit")) {
				System.exit(0);
			}			
			
			if (source.equals("Color")) {
				ColorDialog dialog  = new ColorDialog(
						colorPalette, colorChanger);
				dialog.setLocationRelativeTo(topWindow);
				dialog.setVisible(true);
			}
			
			if (source.equals("Valid")) {
				JCheckBoxMenuItem b = (JCheckBoxMenuItem) e.getSource();
				
				if (b.isSelected()) {
					highlightValid = true;
				} else {
					highlightValid = false;
				}
				
				highlightAll(highlightValid);
			}
		}
	};
	
	/** Handles the changing of colors. */
	private ActionListener colorChanger = new ActionListener() {
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			String source = e.getActionCommand();

			int color;
			
			switch (source) {
			case "Red":
				color = ColorController.RED;
				break;
			case "Blue":
				color = ColorController.BLUE;
				break;
			case "Green":
				color = ColorController.GREEN;
				break;
			case "Gray":
				color = ColorController.GRAY;
				break;
			case "Rainbow":
				color = ColorController.RAINBOW;
				break;
			default:
				return;
			}
			
			setBoardColors(color);
			updateBoardColor();
		}
	};
	
	/****************************************************************
	 * Helper to blur the board.
	 ***************************************************************/
	private void blurBoard() {
		jLayer = new JLayer<JComponent>(buttonPanel, layerUI);
		
		topWindow.remove(buttonPanel);
		topWindow.add(jLayer);
		topWindow.validate();
	}
	
	/****************************************************************
	 * Helper to unblur the board.
	 ***************************************************************/
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
	private FocusListener blurListener = new FocusListener() {
		
		@Override
		public void focusLost(final FocusEvent e) {						
			unBlurBoard();
		}
		
		@Override
		public void focusGained(final FocusEvent e) {
			blurBoard();
		}
	};
	
	/** Increments the proper timer and displays the new time.  */
	private ActionListener timeListener = new ActionListener() {
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource() == wTimer) {
				wClock.count();
				whiteTime.setText("White:  " + wClock.toString());
				whiteTime.setForeground(Color.LIGHT_GRAY);
				blackTime.setForeground(Color.DARK_GRAY);
			} else {
				bClock.count();
				blackTime.setText("Black:  " + bClock.toString());
				blackTime.setForeground(Color.LIGHT_GRAY);
				whiteTime.setForeground(Color.DARK_GRAY);
			}
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
		final float pointFour = 0.4f;
		final float eleven = 11.0f;
		float amount = pointFour / eleven;
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

