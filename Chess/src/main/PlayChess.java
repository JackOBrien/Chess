package main;

import presenter.Presenter;
import model.ChessModel;
import model.IChessModel;
import view.ChessGUI;
import view.IChessUI;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Utility class that creates and runs the entire project.
 *
 * @author John O'Brien
 * @version Mar 10, 2014
 *******************************************************************/
final class PlayChess {
	
	/****************************************************************
	 * Private, non-default constructor to satisfy style.
	 ***************************************************************/
	private PlayChess() { }
	
	/****************************************************************
	 * Main method to create the Model and View and then 
	 * pass them to the Presenter.
	 * 
	 * @param args main method arguments.
	 ***************************************************************/
	public static void main(final String[] args) {
		IChessModel model = new ChessModel();
		
		// board size
		int numRows = model.numRows();
		int numCols = model.numColumns();
		
		IChessUI view = new ChessGUI(numRows, numCols);
		new Presenter(model, view);
	}

}
