package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import presenter.Presenter;

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

	private ImageIcon gvsu = Presenter.loadIcon("images\\GVSUlogoSmall.png");
	private ImageIcon kingLogo = Presenter.loadIcon("images\\kingIconLarge.png");
	
	private JButton[][] board;
	
	private JFrame topWindow;
	
	
	
	/****************************************************************
	 * TODO 
	 ***************************************************************/
	public ChessGUI() {
		topWindow = new JFrame();
		topWindow.setLayout(new GridLayout(8, 8));
		
		setUpFrame();
	}

	private void setUpFrame() {
		List<Image> al = new ArrayList<Image>();
		al.add(gvsu.getImage());
		al.add(kingLogo.getImage());
		
		topWindow.setTitle("CIS 350: Chess Game");
		topWindow.setIconImages(al);
		topWindow.setCursor(new Cursor(Cursor.HAND_CURSOR));
		topWindow.setVisible(true);
		topWindow.pack();
		topWindow.setResizable(false);
		topWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void initializeBoard(JButton[][] pBoard) {
		board = pBoard;
		
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				topWindow.add(board[r][c]);
			}
		}
		
		topWindow.pack();
	}

	
}
