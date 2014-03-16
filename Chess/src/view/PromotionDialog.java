package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
 * CIS 350 - 01
 * Chess
 *
 * 
 *
 * @author John O'Brien
 * @version Mar 15, 2014
 *******************************************************************/
public class PromotionDialog extends JDialog implements ActionListener {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;

	private JButton rook;
	private JButton knight;
	private JButton bishop;
	private JButton queen;
	private JButton okButton;
	
	/**  */
	public static final int IMAGE_SIZE = 40;
	
	private final int BUTTON_SIZE = 45;
	
	private BevelOnHover mouseListener;

	private final int NUM_OPTIONS = 4;
	
	private JPanel optionsPanel;
	
	private boolean white;
	
	public PromotionDialog(boolean w, Color promo, Color acc) {		
		mouseListener = new BevelOnHover(Color.WHITE);
		
		white = w;
		
		rook = createDefaultButton(promo);
		knight = createDefaultButton(promo);
		bishop = createDefaultButton(promo);
		queen = createDefaultButton(promo);
		
		okButton = createDefaultButton(acc);
		okButton.setText("Choose a piece for promotion");
		okButton.setPreferredSize(new Dimension(20, 22));
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
	 * TODO
	 ***************************************************************/
	private void setUpPanel() {
		optionsPanel = new JPanel(new GridLayout(1, NUM_OPTIONS));
		
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

	private JButton createDefaultButton(Color bg) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
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
	public void setRookImage(ImageIcon r) {
		rook.setIcon(r);
	}

	/****************************************************************
	 * Sets the image used for the Knight option.
	 * 
	 * @param k icon for the Knight.
	 ***************************************************************/
	public void setKnightImage(ImageIcon k) {
		knight.setIcon(k);
	}

	/****************************************************************
	 * Sets the image used for the Bishop option.
	 * 
	 * @param b icon for the Bishop.
	 ***************************************************************/
	public void setBishopImage(ImageIcon b) {
		bishop.setIcon(b);
	}

	/****************************************************************
	 * Sets the image used for the Queen option.
	 * 
	 * @param q icon for the Queen.
	 ***************************************************************/
	public void setQueenImage(ImageIcon q) {
		queen.setIcon(q);
	}
	
	public void setActionListener(ActionListener al) {
		rook.addActionListener(al);
		knight.addActionListener(al);
		bishop.addActionListener(al);
		queen.addActionListener(al);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		String whenSelected = "Press Here";
		
		if (source.equals("Ok") && okButton.getText().equals(whenSelected)) {
			dispose();
		}
		
		okButton.setText(whenSelected);		
	}
}
