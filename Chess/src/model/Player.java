package model;

/**
 * A player of a chess game.
 *
 * @author Zachary Kurmas
 */
 
public enum Player {
   BLACK, WHITE;

   /**
    * Return the Player whose turn is next.
    *
    * @return the Player whose turn is next
    */
   public Player next() {
      return this == BLACK ? WHITE : BLACK;
   }
}