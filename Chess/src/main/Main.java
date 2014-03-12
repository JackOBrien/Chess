package main;

import presenter.Presenter;
import model.ChessModel;
import model.IChessModel;
import view.ChessGUI;
import view.IChessGUI;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Main class that creates and runs the entire project.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
public class Main {

	/****************************************************************
	 * @param args
	 ***************************************************************/
	public static void main(String[] args) {
		IChessModel model = new ChessModel();
		IChessGUI view = new ChessGUI();
		new Presenter(model, view);
	}

}
