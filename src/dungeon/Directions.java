package dungeon;

/**
 * Directions are the ways in which a player is allowed to move.
 * A player can only move north, south, east and west.
 * A player can not move diagonally.
 * 
 * @author anush
 *
 */
public enum Directions {

  North("n"),
  South("s"),
  East("e"),
  West("w");

  String moves;
  
  private Directions(String moves) {
    this.moves = moves;
  }
  
  public String getMove() {
    return this.moves;
  }
  
}
