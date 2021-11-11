package dungeon;

import java.util.List;

/**
 * A blueprint for the player class. In the future if we or another 
 * programmer wants to extend or implement a player they can use this 
 * to reduce bugs.
 * A player can traverse from a given start node to the given end node.
 * 
 * @author anush
 *
 */
public interface IPlayer {

  /**
   * Gets the current position of a player.
   * @return The node in which the player currently resides.
   */
  INode getCurrent();
  
  /**
   * Provides the user with all the nodes in the dungeon. 
   * This is used to traverse.
   * 
   * @param x A list of nodes in the dungeon.
   */
  void setAllNodes(List<INode> x);
  
  /**
   * Providing the ability to move.
   * We set the rules as to how a player can move in a dungeon.
   * 
   * @param d The direction in which we want the player to move.
   * @return Returns a string showing from where to where the player has moved.
   */
  String move(Directions d);

  /**
   * This is a list that maintains all the treasures that are picked up on the 
   * way to the end node.
   * @return A list of all treasures that are picked.
   */
  List<List<Treasure>> getAllPickedTreasures();

  /**
   * This gives the player treasures if any are in the start node.
   * 
   * @param startTreasure The list of treasures in the start node.
   */
  void setPickedTreasures(List<Treasure> startTreasure);
}
