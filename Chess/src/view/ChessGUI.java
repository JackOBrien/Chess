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
public class ChessGUI implements IChessGUI {

	private final String PATH = "images\\";
	
	/** Image for the white game pieces. */
	private ImageIcon wBish = loadIcon(PATH + "w_bish.png"),
	  wKing = loadIcon(PATH + "w_king.png"),
	  wKnight = loadIcon(PATH + "w_knight.png"),
	  wPawn = loadIcon(PATH + "w_pawn.png"),
	  wQueen = loadIcon(PATH + "w_queen.png"),
	  wRook = loadIcon(PATH + "w_rook.png");

	/** Image for the black game pieces */
	private ImageIcon bBish = loadIcon(PATH + "b_bish.png"),
	 bKing = loadIcon(PATH + "b_king.png"),
	 bKnight = loadIcon(PATH + "b_knight.png"),
	 bPawn = loadIcon(PATH + "b_pawn.png"),
	 bQueen = loadIcon(PATH + "b_queen.png"),
	 bRook = loadIcon(PATH + "b_rook.png");
	
	private ImageIcon gvsu = loadIcon(PATH + "GVSUlogoSmall.png");
	private ImageIcon exit = loadIcon(PATH + "exit.png");
	
	private final int IMG_SIZE = 60;
	
	/** The amount of extra space between the edge of the button 
	 * and the edge of the image. Default 5. */
	private final int BORDER_TOLERANCE = 5;
	
	private Color light;
	private Color dark;
	private Color selected;
	private Color highlighted;
	private Color promotion;
	private Color accent;
	
	private JButton[][] board;
	
	private JPanel buttonPanel;
	
	private JFrame topWindow;
	
	LayerUI<JComponent> layerUI;
	JLayer<JComponent> jLayer;
	
	private BevelOnHover mouseListener;
	
	private ActionListener promotionListener;
	
	/****************************************************************
	 * TODO 
	 ***************************************************************/
	public ChessGUI(int numRows, int numCols) {
		topWindow = new JFrame();
		
		// Doesn't allow the color to change when pressed
		UIManager.put("Button.select", Color.TRANSLUCENT);
		
		board = new JButton[numRows][numCols];
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(numRows, numCols));
				
		light = new Color(196, 177, 143);
		dark = new Color(49, 46, 40);
		selected = new Color(146, 0, 17);
		highlighted = new Color(214, 55, 57);
		promotion = new Color(160, 160, 155);
		accent = new Color(53, 28, 28);
		
		layerUI = new BlurLayerUI();
		
		mouseListener = new BevelOnHover(Color.BLACK);
		
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
	
	private void setupFrame() {
		int kingLogoSize = 50;
		
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
	
	private void setupMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.setBackground(accent);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setIcon(resizeImage(exit, 20));
		exitItem.addActionListener(menuListener);
		exitItem.setActionCommand("Exit");
		
		fileMenu.setForeground(Color.WHITE);
		
		menuBar.setLayout(new BorderLayout());
		menuBar.setBorderPainted(false);
		
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		topWindow.setJMenuBar(menuBar);
	}
	
	private JButton createDefaultButton() {
		return createDefaultButton(null);
	}
	
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
		
		size -= BORDER_TOLERANCE;
		
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
	 * @param row TODO
	 * @param col
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
	
	@Override
	public ImageIcon imageFinder(String type, boolean white) {		
		ImageIcon image = null;
				
		if (white) {
			
			/* Assigns proper image */
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
		} else {
			
			/* Assigns proper image */
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
		PromotionDialog dialog = new PromotionDialog(white, promotion, accent);
		
		// Find all relevant images
		ImageIcon rookIcon = imageFinder("Rook", white);
		ImageIcon knightIcon = imageFinder("Knight", white);
		ImageIcon bishopIcon = imageFinder("Bishop", white);
		ImageIcon queenIcon = imageFinder("Queen", white);
		
		// Re-size all found images
		rookIcon = resizeImage(rookIcon, PromotionDialog.IMAGE_SIZE);
		knightIcon = resizeImage(knightIcon, PromotionDialog.IMAGE_SIZE);
		bishopIcon = resizeImage(bishopIcon, PromotionDialog.IMAGE_SIZE);
		queenIcon = resizeImage(queenIcon, PromotionDialog.IMAGE_SIZE);
		
		// Set all relevant images for the dialog 
		dialog.setRookImage(rookIcon);
		dialog.setKnightImage(knightIcon);
		dialog.setBishopImage(bishopIcon);
		dialog.setQueenImage(queenIcon);
		
		// Add the ActionListener to the dialog's buttons
		dialog.setActionListener(promotionListener);
						
		int direction = white ? 1 : -1;
		int offset = IMG_SIZE + 4;
		
		dialog.setLocationRelativeTo(board[row][col]);
		int x = (int) dialog.getLocation().getX();
		int y = (int) dialog.getLocation().getY();
		y += offset * direction;
		dialog.setLocation(x, y);
		
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	@Override
	public void gameInCheck(boolean white) {
		String message = "White ";
		ImageIcon icon = wKing;
		int imageSize = 70;
		
		if (!white) {
			message = "Black ";
			icon = bKing;
		}
		message += "is in check!";
		String title = "Check";
		icon = resizeImage(icon, imageSize);
		
		JOptionPane.showMessageDialog(buttonPanel, message, title, 
				JOptionPane.INFORMATION_MESSAGE, icon);
	}
	
	@Override
	public void gameOver(boolean white) {
		String message = "Checkmate! ";
		ImageIcon icon = wKing;
		int imageSize = 70;
		
		if (!white) {
			message += "Black ";
			icon = bKing;
		} else {
			message += "White ";
		}
		message += "won the game!";
		String title = "Game Over";
		icon = resizeImage(icon, imageSize);
		
		JButton playAgain = new JButton("Play Again");
		playAgain.setEnabled(false);
		JButton quit = new JButton("Quit");
		JButton[] options = {playAgain, quit};
		
		JOptionPane.showOptionDialog(buttonPanel, message, title, 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, 
				options, quit);
	}
	
	private ActionListener menuListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String source = e.getActionCommand();
			
			if (source.equals("Exit")) {
				System.exit(0);
			}
			
		}
	};
	
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
			} catch (NullPointerException exc) { }
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
	private static final long serialVersionUID = 1L;
	private BufferedImage mOffscreenImage;
	private BufferedImageOp mOperation;

	public BlurLayerUI() {
		float amount = 0.4f / 11.0f;
		float[] blurKernel = {
				amount, amount, amount,
				amount, amount, amount,
				amount, amount, amount
		};
		mOperation = new ConvolveOp(
				new Kernel(3, 3, blurKernel),
				ConvolveOp.EDGE_NO_OP, null);
	}

	@Override
	public void paint (Graphics g, JComponent c) {
		int w = c.getWidth();
		int h = c.getHeight();

		if (w == 0 || h == 0) {
			return;
		}

		// Only create the offscreen image if the one we have
		// is the wrong size.
		if (mOffscreenImage == null ||
				mOffscreenImage.getWidth() != w ||
				mOffscreenImage.getHeight() != h) {
			mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		}

		Graphics2D ig2 = mOffscreenImage.createGraphics();
		ig2.setClip(g.getClip());
		super.paint(ig2, c);
		ig2.dispose();

		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(mOffscreenImage, mOperation, 0, 0);
	}
}

