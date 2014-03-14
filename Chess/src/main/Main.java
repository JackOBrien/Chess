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
		
		// board size
		int numRows = model.getBoard().numRows();
		int numCols = model.getBoard().numColumns();
		
		IChessGUI view = new ChessGUI(numRows, numCols);
		new Presenter(model, view);
	}

}
