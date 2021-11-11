package dungeon;

import java.util.List;

/**
 * A blueprint to create a dungeon.
 * If a programmer wants to create a new dungeon or extend our program 
 * in the future we want to extend the dungeon we can use this interface to 
 * reduce bugs.
 * 
 * @author anush
 *
 */
public interface IDungeon {

  /**
   * Gets the number of rows in the dungeon.
   * @return Returns a numeric value for the number of rows in dungeons.
   */
  int getRows();
  
  /**
   * Gets the number of columns in the dungeon.
   * @return Returns a numeric value for the number of columns in dungeon.
   */
  int getCols();
  
  /**
   * We check if the dungeon is wrapping or not.
   * If Wrapping we add extra edges to go around the dungeon.
   * @return A boolean value.
   */
  boolean getIsWrapping();
  
  /**
   * Creates a dungeon of size rows*columns.
   * Every location has a node object.
   */
  void createDungeon();
  
  /**
   * All possible paths in a given dungeon.
   * We get all the ways we can have an edge from each node.
   */
  void potentialPaths();
  
  /**
   * When wrapping is true we get the extra edges that let us wrap around 
   * the dungeon.
   */
  void wrappingPotentialPath();
  
  /**
   * An algorithm that helps us avoid cycles and get a path from each node to 
   * every other node in the dungeon.
   * We only get unique paths.
   */
  void kruskalAlgorithms();
  
  /**
   * The paths that are left out when we run kruskal's is sorted separately.
   * When we specify a specific number of interconnectivity we pick a 
   * path randomly and add it to the dungeon.
   */
  void addInterconnectivity();
  
  /**
   * Based on the newly formed dungeons after running kruskal and adding 
   * interconnectivity we update the directions that are available from a 
   * node.
   */
  void updateDirections();
  
  /**
   * Assign cave or tunnel based on the number of paths that are 
   * associated with that particular node.
   */
  void setLocationType();
  
  /**
   * This method assigns a node random number of treasures and random type of treasures.
   */
  void setTreasures();
  
  /**
   * We create adjacency list in this method.
   * we document ever path from a node.
   */
  void adjlist();
  
  /**
   * This method creates all possible paths between 2 edges such 
   * that the minimum path length is 5.
   */
  void minFivePath();
    
  /**
   * Setting paths of minimum length 5 from start to end.
   */
  void setStartEnd();
  
  /**
   * Here we set a the player start node and end node 
   * based on the paths we generated above.
   * @return
   */
  String setPlayerStartAndEnd();
  
  /**
   * This allows a player to move by taking in direction 
   * input from the user.
   * 
   * @param move The user input that allows player movement.
   * @return Player movement from current node to new node and the treasures collected in that 
   *        node.
   */
  String playerMovement(int move);
  
  //PRINT METHODS

  /**
   * A method used to print all the nodes in a dungeon.
   * @return A string that shows all nodes in the dungeon.
   */
  String printDungeon();
  
  /**
   * A method used to print all the edges that are possible 
   * from every node.
   * @return A string that displays all the possible paths in a dungeon.
   */
  String printPotentialPaths();
  
  /**
   * The paths generated after running kruskal's algorithm.
   * @return A string of paths such that every node has a unique path 
   *        to every other node.
   */
  String printUniquePath();
  
  /**
   * The edges that were not considered during kruskal's/
   * adding them will form cycles.
   * @return A string of paths that were ignored during kruskal's.
   */
  String printLeftOverPath();
  
  /**
   * Provides complete description of each node.
   * Such as node ID, treasures in the node if any and the possible paths from the node.
   * @return A string with all the above mentioned details.
   */
  String printPotentialPathsDirections();
  
  /**
   * Displays the dungeon in a visually understanding way.
   * Represented like a 2D matrix with paths.
   * @return A visual representation of the dungeon after running
   *        kruskal and adding interconnectivity.
   */
  String displayDungeon();

  /**
   * Prints all the treasures collected and shows that the game has ended.
   * @return A string representing a list of treasures collected and shows an end message.
   */
  String gameEnded();

  /**
   * Checks if the game has ended or not.
   * @return True if game ended. False if game not ended.
   */
  boolean hasEnded();

  /**
   * Shows all possible movements from a given location.
   * @return A list of possile locations.
   */
  String possibleMovements();

  /**
   * Get all path objects from start to end with minimum length 5.
   * @return
   */
  List<IPath> getStartEnd();

  /**
   * The path that is randomly picked to traverse.
   * @return Index value of this path.
   */
  int getStartEndpath();

  /**
   * The number of caves to which we need to assign treasures.
   * @return
   */
  int treasurePercentageValue();

  /**
   * Get the list of caves in the dungeon.
   * Needed for testing purposes.
   * @return A list of caves in the dungeon.
   */
  List<INode> getCaveList();

  /**
   * A method that shows how many of our caves have treasures.
   * @return an integer value for number of caves with treasures in a dungeon.
   */
  int numberOfCavesWithTreasures();

  /**
   * A method to get the location details of the node the player is currently in.
   * @return A string of details about a location.
   */
  String playerLocationDetails();
  
  /**
   * A player details. At the minimum his location and treasures collected so far.
   */
  String playerDetails();

  
}
