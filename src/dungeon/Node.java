package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A node is a specific object in the dungeon 2D grid.
 * At each position in the 2D grid we have a node. 
 * This represents our cave or tunnel based on path associated with it.
 */
public class Node implements INode {

  //private static final int ArrayList = 0;
  private final int row;
  private final int col;
  private final int nId;
  private boolean north;
  private boolean south;
  private boolean east;
  private boolean west;
  private int count;
  private LocationType type;
  private List<Treasure> treasure;
  private List<Directions> availableDirections;
  private List<Treasure> tempTreasures;
  
  /**
   * Constructing a node.
   * 
   * @param row The row in the matrix where this node is present.
   * @param col The column in the matrix where this node is present.
   * @param nId The unique identifier of a particular node.
   */
  public Node(int row, int col, int nId) {
    this.row = row;
    this.col = col;
    this.nId = nId;
    this.north = false;
    this.south = false;
    this.east = false;
    this.west = false;
    treasure = new ArrayList<>();
    availableDirections = new ArrayList<>();
    tempTreasures = new ArrayList<>();
  }
  
  @Override
  public int getRow() {
    return this.row;
  }
  
  @Override
  public int getCol() {
    return this.col;
  }
  
  @Override
  public int getNid() {
    return this.nId;
  }
  
  @Override
  public boolean getNorth() {
    return this.north;
  }
  
  @Override
  public boolean getSouth() {
    return this.south;
  }
  
  @Override
  public boolean getEast() {
    return this.east;
  }
  
  @Override
  public boolean getWest() {
    return this.west;
  }
  
  @Override
  public void setNorth() {
    this.north = true;
    if (!this.availableDirections.contains(Directions.North)) {
      this.availableDirections.add(Directions.North);
    } 
  }
  
  @Override
  public void setSouth() {
    this.south = true;
    if (!this.availableDirections.contains(Directions.South)) {
      this.availableDirections.add(Directions.South);
    }
  }
  
  @Override
  public void setEast() {
    this.east = true;
    if (!this.availableDirections.contains(Directions.East)) {
      this.availableDirections.add(Directions.East);
    } 
  }
  
  @Override
  public void setWest() {
    this.west = true;
    if (!this.availableDirections.contains(Directions.East)) {
      this.availableDirections.add(Directions.West);
    }
  }
  
  @Override
  public void setCount() {
    this.count++;
  }
  
  @Override
  public void setType() {
    if (this.count == 2) {
      this.type = LocationType.TUNNEL;
    }
    else {
      this.type = LocationType.CAVE;
    }
  }
  
  @Override
  public void setTreasure(Treasure t) {
    this.treasure.add(t);
  }
  
  @Override
  public LocationType getType() {
    return this.type;
  }
  
  @Override
  public List<Directions> possibleDirections() {
    return new ArrayList<>(this.availableDirections);
  }
  
  @Override
  public String directionsOfNode() {
    return String.format(this.availableDirections.toString());
  }
  
  @Override
  public String locationOfNode() {
    StringBuilder sb = new StringBuilder();
    sb.append("Type(" + this.type + ", " + this.count + ")");
    if (this.getType() == LocationType.CAVE) {
      sb.append("\nTreasures: " + this.treasure);
    }
    return sb.toString();
  }
  
  @Override
  public String toString() {
    return String.format("Node(" 
        + this.row 
        + ", " 
        + this.col 
        + ", " 
        + this.nId 
        + ")");
  }
  
  @Override
  public List<Treasure> getTreasures() { 
    tempTreasures.addAll(treasure);
    if (this.treasure.size() > 0) {
      this.treasure.clear();
    }
    return new ArrayList<>(tempTreasures);
  }
  
}
