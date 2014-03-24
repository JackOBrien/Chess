package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;

/********************************************************************
 * CIS 350 - 01.
 * Chess
 *
 * 
 *
 * @author John O'Brien
 * @version Mar 15, 2014
 *******************************************************************/
public class PromotionDialog extends JDialog implements ActionListener {

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/** Button for the Rook option. */
	private JButton rook;
	
	/** Button for the Knight option. */
	private JButton knight;
	
	/** Button for the Bishop option. */
	private JButton bishop;
	
	/** Button for the Queen option. */
	private JButton queen;
	
	/** Button to confirm selection. */
	private JButton okButton;
	
	/** MouseListener that will bevel buttons when interacted with. */
	private BevelOnHover mouseListener;

	/** Number of pieces that the pawn can be promoted to. */
	private final int numOptions = 4;
	
	/** Panel with the pieces (options) on it. */
	private JPanel optionsPanel;
	
	/** Tells if the player promoting is white. */
	private boolean white;
	
	/** The size of the buttons. */
	private int size;
	
	/****************************************************************
	 * Constructor for the promotion dialog.
	 * 
	 * @param w tells is the piece is white.
	 * @param pSize the size of the images.
	 * @param promo the background color for the pieces.
	 * @param acc the color for the ok button.
	 ***************************************************************/
	public PromotionDialog(final boolean w, final int pSize, final Color promo,
			final Color acc) {		
		mouseListener = new BevelOnHover(Color.WHITE);
		
		final int border = 5;
		
		white = w;
		size = pSize + border;
		
		rook = createDefaultButton(promo);
		knight = createDefaultButton(promo);
		bishop = createDefaultButton(promo);
		queen = createDefaultButton(promo);
		
		final double fontMultiplier = .2667;
		final int fontSize = (int) (this.size * fontMultiplier);
		Font f = new Font("Dialog", Font.BOLD, fontSize);
		
		okButton = createDefaultButton(acc);
		okButton.setText("Choose a piece for promotion");
		okButton.setFont(f);
		final double dimMultiplier = .4889;
		final int lableSize = (int) (this.size * dimMultiplier);
		okButton.setPreferredSize(new Dimension(lableSize, lableSize));
		okButton.setForeground(Color.WHITE);
		
		rook.addActionListener(this);
		knight.addActionListener(this);
		bishop.addActionListener(this);
		queen.addActionListener(this);
		okButton.addActionListener(this);
		
		setLayout(new BorderLayout());
		
		setUpPanel();
		
		add(optionsPanel);
		
		if (white) {
			add(okButton, BorderLayout.SOUTH);
		} else {
			add(okButton, BorderLayout.NORTH);
		}
		
		setUndecorated(true);
		pack();
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
	}
	
	/****************************************************************
	 * TODO.
	 ***************************************************************/
	private void setUpPanel() {
		optionsPanel = new JPanel(new GridLayout(1, numOptions));
		
		// SetActionCommand for all buttons
		rook.setActionCommand("Rook");
		knight.setActionCommand("Knight");
		bishop.setActionCommand("Bishop");
		queen.setActionCommand("Queen");
		okButton.setActionCommand("Ok");
		
		// Add all found images to the panel
		optionsPanel.add(rook);
		optionsPanel.add(knight);
		optionsPanel.add(bishop);
		optionsPanel.add(queen);

	}

	/****************************************************************
	 * Creates and returns a default with the given background color.
	 * 
	 * @param bg the background color of the button.
	 * @return a default with the given background color.
	 ***************************************************************/
	private JButton createDefaultButton(final Color bg) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(size, size));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addMouseListener(mouseListener);
		button.setBackground(bg);
		button.setOpaque(true);
		button.setFocusable(false);
		
		Border line = BorderFactory.createLineBorder(Color.WHITE, 1);
		button.setBorder(line);
		
		return button;
	}
	
	/****************************************************************
	 * Sets the image used for the Rook option.
	 * 
	 * @param r icon for the Rook.
	 ***************************************************************/
	public final void setRookImage(final ImageIcon r) {
		rook.setIcon(r);
	}

	/****************************************************************
	 * Sets the image used for the Knight option.
	 * 
	 * @param k icon for the Knight.
	 ***************************************************************/
	public final void setKnightImage(final ImageIcon k) {
		knight.setIcon(k);
	}

	/****************************************************************
	 * Sets the image used for the Bishop option.
	 * 
	 * @param b icon for the Bishop.
	 ***************************************************************/
	public final void setBishopImage(final ImageIcon b) {
		bishop.setIcon(b);
	}

	/****************************************************************
	 * Sets the image used for the Queen option.
	 * 
	 * @param q icon for the Queen.
	 ***************************************************************/
	public final void setQueenImage(final ImageIcon q) {
		queen.setIcon(q);
	}
	
	/****************************************************************
	 * Sets the ActionListener for all of the options (all buttons
	 * except for the ok button).
	 * 
	 * @param al the ActionListener to set.
	 ***************************************************************/
	public final void setActionListener(final ActionListener al) {
		rook.addActionListener(al);
		knight.addActionListener(al);
		bishop.addActionListener(al);
		queen.addActionListener(al);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		String source = e.getActionCommand();
		String whenSelected = "Press Here";
		
		if (source.equals("Ok")) {
			if (okButton.getText().equals(whenSelected)) {
				dispose();
			} else {
				return;
			}
		}
		
		okButton.setText(whenSelected);		
	}
}
