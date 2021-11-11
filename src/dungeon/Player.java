package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A player class implements the IPlayer interface. A player can move from start to end based on
 * user inputs. A player can also collect treasures if he encounters any.
 * 
 * @author anush
 *
 */
public class Player implements IPlayer {

  private INode current;
  private final List<INode> allNodesInDungeon;
  private final int numberOfRowsInDungeon;
  private final int numberOfColumnsInDungeon;
  private List<List<Treasure>> playerTreasures;

  /**
   * A constructor to create a player.
   * 
   * @param current The current node in which the player is residing.
   * @param rows Number of rows in the dungeon.
   * @param cols Number of columns in the dungeon.
   */
  public Player(INode current, int rows, int cols) {
    if (current == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    this.current = current;
    this.numberOfRowsInDungeon = rows;
    this.numberOfColumnsInDungeon = cols;
    allNodesInDungeon = new ArrayList<>();
    playerTreasures = new ArrayList<>();
  }

  @Override
  public String move(Directions d) {
    int prevId = this.current.getNid();

    switch (d.getMove()) {
      case "n":
        if (this.current.getRow() == 0) {
          this.current = this.allNodesInDungeon.get(this.current.getNid()
              + ((this.numberOfRowsInDungeon - 1) * this.numberOfColumnsInDungeon));
        } else {
          this.current =
              this.allNodesInDungeon.get(this.current.getNid() - this.numberOfColumnsInDungeon);
        }
        break;

      case "s":
        if (this.current.getRow() == (this.numberOfRowsInDungeon - 1)) {
          this.current = this.allNodesInDungeon.get(this.current.getNid()
              - ((this.numberOfRowsInDungeon - 1) * this.numberOfColumnsInDungeon));
        } else {
          this.current =
              this.allNodesInDungeon.get(this.current.getNid() + this.numberOfColumnsInDungeon);
        }
        break;

      case "e":
        if (this.current.getCol() == (this.numberOfColumnsInDungeon - 1)) {
          this.current = this.allNodesInDungeon
              .get(this.current.getNid() - (this.numberOfColumnsInDungeon - 1));
        } else {
          this.current = this.allNodesInDungeon.get(this.current.getNid() + 1);
        }

        break;

      case "w":
        if (this.current.getCol() == 0) {
          this.current = this.allNodesInDungeon
              .get(this.current.getNid() + (this.numberOfColumnsInDungeon - 1));
        } else {
          this.current = this.allNodesInDungeon.get(this.current.getNid() - 1);
        }
        break;
        
      default: 
        throw new IllegalArgumentException("Invalid move by the player.");
    }
    List<Treasure> tempTreasure = new ArrayList<>();
    tempTreasure.addAll(this.current.getTreasures());
    if (tempTreasure.size() > 0) {
      this.playerTreasures.add(tempTreasure);
    }

    StringBuilder sb = new StringBuilder();
    
    if (tempTreasure.size() > 0) {
      sb.append("Treasures Available at " + prevId + ": " + this.current.getTreasures().toString() 
          + "\n");
    }
    
    sb.append("Player Moved from: " + prevId + "\nPlayer Moved To: " + this.current.getNid() + "\n" 
        + "Direction Moved: " + d + "\n");


    if (tempTreasure.size() > 0) {
      sb.append("Player collected treasures:" + this.current.getTreasures().toString());
    }
    return sb.toString();
  }

  @Override
  public INode getCurrent() {
    return this.current;
  }

  @Override
  public void setAllNodes(List<INode> x) {
    this.allNodesInDungeon.addAll(x);
  }

  @Override
  public List<List<Treasure>> getAllPickedTreasures() {
    return new ArrayList<>(this.playerTreasures);
  }

  @Override
  public void setPickedTreasures(List<Treasure> startTreasure) {
    this.playerTreasures.add(startTreasure);
  }
}
