package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A player of a chess game. 
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @author Zachary Kurmas
 * @version Feb 20, 2014
 *******************************************************************/
public enum Player {
   BLACK, WHITE;

/****************************************************************
 * Return the Player whose turn is next.
 * 
 * @return the Player whose turn is next.
 ***************************************************************/
public Player next() {
      return this == BLACK ? WHITE : BLACK;
   }
}