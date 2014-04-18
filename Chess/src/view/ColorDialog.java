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

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/** ActionListener to handle the selection of colors. */
	private ActionListener listener;
	
	/** The group of radio buttons which control color selection. */
	private ButtonGroup group;
	
	/** The current selected color palette. */
	private int palette;
	
	/****************************************************************
	 * Constructor of the ColorDialog.
	 * 
	 * @param color the current selected color palette.
	 * @param al the ActionListener to handle the selection of colors.
	 ***************************************************************/
	public ColorDialog(final int color, final ActionListener al) {
		
		palette = color;
		listener = al;
		
		final int numColors = 5;
		
		setLayout(new GridLayout(numColors, 1, 0, 2));
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
	
	/****************************************************************
	 * Creates the JPanel to hold the radio button and color sample
	 * for the given color.
	 * 
	 * @param name name of the color.
	 * @param selected tells if the radio button should be selected.
	 * @param light tells what the light color from the palette is.
	 * @param dark tells what the dark color for the palette is.
	 * @return the created JPanel.
	 ***************************************************************/
	private JPanel createPanel(final String name, final boolean selected, 
			final Color light, final Color dark) {

		JRadioButton button = new JRadioButton();
		button.setSelected(selected);
		button.setText(name);
		button.setActionCommand(name);
		button.addActionListener(listener);		
		group.add(button);
		
		final int width = 80;
		
		JLabel lightLabel = createColorLabel(light);
		JLabel darkLabel = createColorLabel(dark);
		JPanel colorPanel = new JPanel();
		colorPanel.setPreferredSize(new Dimension(width, 0));
		colorPanel.setLayout(new GridLayout(1, 2, 0, 0));
		colorPanel.add(lightLabel);
		colorPanel.add(darkLabel);
		
		final int w = 180;
		final int h = 25;
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(w, h));
		
		panel.add(button, BorderLayout.LINE_START);
		panel.add(colorPanel, BorderLayout.LINE_END);
		
		return panel;
	}
	
	/****************************************************************
	 * Creates the JPanel to hold the radio button and color sample
	 * for the given color.
	 * 
	 * @param name name of the color.
	 * @return the created JPanel.
	 ***************************************************************/
	private JPanel createPanel(final String name) {
		
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
	
	/****************************************************************
	 * Creates a JLabel with the specified color and applies the
	 * default look.
	 * 
	 * @param c background color of the label.
	 * @return the created JLabel.
	 ***************************************************************/
	private JLabel createColorLabel(final Color c) {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(c);
		label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		return label;
	}
}
