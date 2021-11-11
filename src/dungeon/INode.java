package dungeon;

import java.util.List;

/**
 * INode is a blueprint for the node class. 
 * Based on SOLID principles a programmer must be allowed
 * to extend and not modify a code. This is considered a good 
 * practice. Therefore have a blueprint will allow to 
 * make other nodes without bugs and will give him/her an idea as to how the 
 * Node should be created.
 * 
 * @author anush
 *
 */
public interface INode {

  /**
   * We get the row in which a Node is from a 2D grid.
   * 
   * @return a numeric value for the row position.
   */
  public int getRow();
  
  /**
   * We get the column in which a Node is from a 2D grid.
   * 
   * @return a numeric value for the column position.
   */
  public int getCol();
  
  /**
   * We get the unique identifier for a node.
   * 
   * @return a numeric value for the unique identifier of a node.
   */
  public int getNid();
  
  /**
   * This function tells us if we can go north from the current node.
   * 
   * @return returns true we can go north else false.
   */
  public boolean getNorth();
  
  /**
   * This function tells us if we can go south from the current node.
   * 
   * @return returns true we can go south else false.
   */
  public boolean getSouth();
  
  /**
   * This function tells us if we can go east from the current node.
   * 
   * @return returns true we can go east else false.
   */
  public boolean getEast();
  
  /**
   * This function tells us if we can go west from the current node.
   * 
   * @return returns true we can go west else false.
   */
  public boolean getWest();
  
  /**
   * Update the node's direction to true if it can move in the specified direction.
   */
  public void setNorth();
  
  /**
   * Update the node's direction to true if it can move in the specified direction.
   */
  public void setSouth();
  
  /**
   * Update the node's direction to true if it can move in the specified direction.
   */
  public void setEast();
  
  /**
   * Update the node's direction to true if it can move in the specified direction.
   */
  public void setWest();
  
  /**
   * Every time a direction gets updated to true for a node we increment the count.
   * Count is needed as we will use it to decide whether a particular node is a cave or a tunnel
   */
  public void setCount();
  
  /**
   * Setting the node the type of location. 
   * Whether it is a cave or a tunnel.
   */
  public void setType();
  
  /**
   * Assigning a node if it is a cave with random treasures.
   * 
   * @param t Enumeration type of treasures. Can be diamond, ruby or saphire.
   */
  public void setTreasure(Treasure t);
  
  /**
   * Get the type of node it is. 
   * Whether it is a cave or a tunnel.
   * 
   * @return An enumeration type of loaction.
   */
  public LocationType getType();
  
  /**
   * The possible ways to move from a cave in the dungeon.
   * 
   * @return A list of directions we can move from a node.
   */
  public List<Directions> possibleDirections();
  
  /**
   * Returns the ways to move from a node in the form of a string.
   * 
   * @return A string of possible directions from a node.
   */
  public String directionsOfNode();
  
  /**
   * Returns the type of node as a string.
   * It can be either a tunnel or a dungeon.
   * 
   * @return A string of node type.
   */
  public String locationOfNode();
  
  /**
   * Get all the treasures if any in the current node.
   * 
   * @return A list of treasures if any nodes present.
   */
  public List<Treasure> getTreasures();
}
