package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Dialog window for selecting the game size and quality.
 *
 * @author John O'Brien
 * @version Apr 15, 2014
 *******************************************************************/
public class ResizeDialog extends JDialog {

	/** Default serial UID. */
	private static final long serialVersionUID = 1L;

	/** Size of a small board's pieces. */
	public static final int SMALL = 30;
	
	/** Size of a standard board's pieces. */
	public static final int STD_SIZE = 60;
	
	/** Size of a large board's pieces. */
	public static final int LARGE = 80;
	
	/** Low quality multiplier. */
	public static final double LOW = .25;
	
	/** Medium quality multiplier. */
	public static final double MEDIUM = .7;
	
	/** High quality multiplier. */
	public static final double HIGH = 1;
	
	/** Button to select small game size. */
	private JRadioButton small;
	
	/** Button to select standard game size. */
	private JRadioButton stdSize;
	
	/** Button to select large game size. */
	private JRadioButton large;
	
	/** Button to select low quality. */
	private JRadioButton low;
	
	/** Button to select medium quality. */
	private JRadioButton medium;
	
	/** Button to select high quality. */
	private JRadioButton high;
	
	/** Buttons to select the game's size. */
	private ButtonGroup sizeGroup;
	
	/** Buttons to select the game's quality. */
	private ButtonGroup qualityGroup;
	
	/** The current size of the board.  */
	private static int size = STD_SIZE;
	
	/** The current quality of the board. */
	private static double quality = HIGH;
	
	/****************************************************************
	 * Constructor for ResizeDialog. Sets the current size and quality.
	 * 
	 * @param s current size.
	 * @param q current quality.
	 ***************************************************************/
	public ResizeDialog(final int s, final double q) {
		setLayout(new BorderLayout());
		
		setupButtons();
		
		setSizeSelected();
		setQualitySelected();
		
		final int four = 4;
		final int fontSize = 16;
		
		Font font = new Font("Calibri", Font.BOLD, fontSize);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel left = new JPanel(new GridLayout(four, 0));
		JLabel sLabel = new JLabel("Size");
		sLabel.setFont(font);
		left.add(sLabel);
		left.add(small);
		left.add(stdSize);
		left.add(large);
		
		JPanel right = new JPanel(new GridLayout(four, 0));
		JLabel rLabel = new JLabel("Quality");
		rLabel.setFont(font);
		right.add(rLabel);
		right.add(low);
		right.add(medium);
		right.add(high);
		
		buttonPanel.add(left);
		buttonPanel.add(right);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(listener);
		ok.setActionCommand(ok.getText());
		
		add(buttonPanel, BorderLayout.CENTER);
		add(ok, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setModal(true);
		setVisible(true);
	}

	/****************************************************************
	 * Helper to set up the radio buttons.
	 ***************************************************************/
	private void setupButtons() {
		sizeGroup = new ButtonGroup();
		small = new JRadioButton("Small");
		small.addActionListener(listener);
		small.setActionCommand(small.getText());
		sizeGroup.add(small);
		stdSize = new JRadioButton("Standard");
		stdSize.addActionListener(listener);
		stdSize.setActionCommand(stdSize.getText());
		sizeGroup.add(stdSize);
		large = new JRadioButton("Large");
		large.addActionListener(listener);
		large.setActionCommand(large.getText());
		sizeGroup.add(large);
		
		qualityGroup = new ButtonGroup();
		low = new JRadioButton("Low");
		low.addActionListener(listener);
		low.setActionCommand(low.getText());
		qualityGroup.add(low);
		medium = new JRadioButton("Medium");
		medium.addActionListener(listener);
		medium.setActionCommand(medium.getText());
		qualityGroup.add(medium);
		high = new JRadioButton("High");
		high.addActionListener(listener);
		high.setActionCommand(high.getText());
		qualityGroup.add(high);
	}
	
	/****************************************************************
	 * Helper to set which size button should be selected.
	 ***************************************************************/
	private void setSizeSelected() {
		
		switch (size) {
		case SMALL:
			small.setSelected(true);
			stdSize.setSelected(false);
			large.setSelected(false);
			break;
		case LARGE:
			small.setSelected(false);
			stdSize.setSelected(false);
			large.setSelected(true);
			break;
		default:
			small.setSelected(false);
			stdSize.setSelected(true);
			large.setSelected(false);
			break;
		}
	}
	

	/****************************************************************
	 * Helper to set which quality button should be selected.
	 ***************************************************************/
	private void setQualitySelected() {
		low.setSelected(false);
		medium.setSelected(false);
		high.setSelected(false);
		
		if (quality == LOW) {
			low.setSelected(true);
		} else if (quality == MEDIUM) {
			medium.setSelected(true);
		} else {
			high.setSelected(true);
		}
	}
	
	/****************************************************************
	 * @return the size of the board.
	 ***************************************************************/
	public static final int getBoardSize() {
		return size;
	}
	
	/****************************************************************
	 * @return the quality of the board.
	 ***************************************************************/
	public static final double getBoardQuality() {
		return quality;
	}
	
	/** Sets the proper size and quality. Exits the dialog when
	 * the user presses the "OK" button. */
	private ActionListener listener = new ActionListener() {
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			String source = e.getActionCommand();
			
			if (source.equals("Ok")) {
				dispose();
				return;
			}
			
			switch (source) {
			case "Small":
				size = SMALL;
				break;
			case "Standard":
				size = STD_SIZE;
				break;
			case "Large":
				size = LARGE;
				break;
			case "Low":
				quality = LOW;
				break;
			case "Medium":
				quality = MEDIUM;
				break;
			case "High":
				quality = HIGH;
				break;
			default:
				size = STD_SIZE;
				quality = HIGH;
				break;
			}
		}
	};
}
