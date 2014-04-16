package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.colors.ColorController;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Dialog to choose colors for the board.
 *
 * @author John O'Brien
 * @version Mar 31, 2014
 *******************************************************************/
public class ColorDialog extends JDialog {

	/**  */
	private static final long serialVersionUID = 1L;

	private ActionListener listener;
	
	private ButtonGroup group;
	
	private int palette;
	
	public ColorDialog(int color, ActionListener al) {
		
		palette = color;
		listener = al;
		
		setLayout(new GridLayout(5, 1, 0, 2));
		setTitle("Choose a Theme");
		
		group = new ButtonGroup();
		add(createPanel("Red"));
		add(createPanel("Blue"));
		add(createPanel("Green"));
		add(createPanel("Gray"));
		add(createPanel("Rainbow"));

		pack();
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private JPanel createPanel(String name, boolean selected, 
			Color light, Color dark) {

		JRadioButton button = new JRadioButton();
		button.setSelected(selected);
		button.setText(name);
		button.setActionCommand(name);
		button.addActionListener(listener);		
		group.add(button);
		
		JLabel lightLabel = createColorLabel(light);
		JLabel darkLabel = createColorLabel(dark);
		JPanel colorPanel = new JPanel();
		colorPanel.setPreferredSize(new Dimension(80, 0));
		colorPanel.setLayout(new GridLayout(1, 2, 0, 0));
		colorPanel.add(lightLabel);
		colorPanel.add(darkLabel);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(180, 25));
		
		panel.add(button, BorderLayout.LINE_START);
		panel.add(colorPanel, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPanel createPanel(String name) {
		
		int color;
		boolean selected = false;
		
		switch (name) {
		case "Red":
			color = ColorController.RED;
			break;
		case "Blue":
			color = ColorController.BLUE;
			break;
		case "Green":
			color = ColorController.GREEN;
			break;
		case "Rainbow":
			color = ColorController.RAINBOW;
			break;
		case "Gray":
			color = ColorController.GRAY;
			break;
		default:
			color = -1;
			break;
		}
		
		ColorController cc = new ColorController(color);
		Color light = cc.getLight();
		Color dark = cc.getDark();
		
		if (palette == color) {
			selected = true;
		}
		
		return createPanel(name, selected, light, dark);
	}
	
	private JLabel createColorLabel(Color c) {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(c);
		label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		return label;
	}
}
