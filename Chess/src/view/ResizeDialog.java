package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
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

	private static int size;
	
	private static double quality;
	
	public static final int SMALL = 30;
	
	public static final int STD_SIZE = 60;
	
	public static final int LARGE = 80;
	
	public static final double LOW = .3;
	
	public static final double MEDIUM = .7;
	
	public static final double HIGH = 1;
	
	private JRadioButton small;
	
	private JRadioButton stdSize;
	
	private JRadioButton large;
	
	private JRadioButton low;
	
	private JRadioButton medium;
	
	private JRadioButton high;
	
	ButtonGroup sizeGroup;
	
	ButtonGroup qualityGroup;
	
	public ResizeDialog(int s, double q) {
		setLayout(new BorderLayout());
		
		setupButtons();
		
		setSizeSelected(s);
		setQualitySelected(q);
		
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
	
	private void setSizeSelected(int s) {
		
		switch (s) {
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
	 * @param q
	 ***************************************************************/
	private void setQualitySelected(final double q) {
		low.setSelected(false);
		medium.setSelected(false);
		high.setSelected(false);
		
		if (q == LOW) {
			low.setSelected(true);
		} else if (q == MEDIUM) {
			medium.setSelected(true);
		} else {
			high.setSelected(true);
		}
	}
	
	public static final int getBoardSize() {
		return size;
	}
	
	public static final double getBoardQuality() {
		return quality;
	}
	
	private ActionListener listener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
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
