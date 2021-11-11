package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * The dungeon class that implements the IDungeon interface. A dungeon is like a maze where there
 * are caves and tunnels through which a player can move. There are treasures randomly present which
 * can be collected. A dungeon can be wrapping or non-wrapping. A player must find the exit from the
 * given start node.
 * 
 * @author anush
 *
 */
public class Dungeon implements IDungeon {

  private final int rows;
  private final int cols;
  private final boolean isWrapping;
  private final int interconnectivity;
  private int count;
  private int vertices;
  private final int percentageOfTreasures;
  private int startEndPath;

  private int numberOfCavesToAssignTreasure;
  private int[] parent;
  private Node[][] dungeon;
  private List<INode> allNodes;
  private List<INode> caveList;
  private List<IPath> potentialPaths;
  private List<IPath> uniquePath;
  private List<IPath> leftOverPaths;
  // private List<List<INode>> adjacencyList;
  private List<IPath> startEnd;
  private Vector<Integer>[] adj;
  private RandomInterface r;
  private int minPath;
  private int pCount;
  private IPlayer player;


  /**
   * A constructor to create a dungeon. We provide the size of the dungeon, interconnectivity,
   * whether wrapping or non-wrapping.
   * 
   * @param rows Number of rows we want in a dungeon.
   * @param cols Number of columns we want in a dungeon.
   * @param isWrapping A boolean value that decides whether the dungeon is wrapping or non-wrapping.
   * @param interconnectivity The number of extra connections we want in a dungeon.
   * @param percentageOfTreasure The amount of treasure that needs to be randomly placed in the
   *        dungeon.
   * @param r This is a parameter for randomness. It is used for implementation and testing.
   */
  public Dungeon(int rows, int cols, boolean isWrapping, int interconnectivity,
      int percentageOfTreasure, RandomInterface r) {
    this.rows = rows;
    this.cols = cols;
    this.isWrapping = isWrapping;
    this.interconnectivity = interconnectivity;
    this.percentageOfTreasures = percentageOfTreasure;
    this.vertices = this.cols * this.rows;
    this.parent = new int[this.vertices];
    this.allNodes = new ArrayList<>();
    dungeon = new Node[this.rows][this.cols];
    potentialPaths = new ArrayList<>();
    uniquePath = new ArrayList<>();
    caveList = new ArrayList<>();
    leftOverPaths = new ArrayList<>();
    // adjacencyList = new LinkedList<>();
    startEnd = new ArrayList<>();
    this.r = r;

    adj = new Vector[this.vertices];

    for (int i = 0; i < this.vertices; i++) {
      adj[i] = new Vector<>();
    }
  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getCols() {
    return this.cols;
  }

  @Override
  public boolean getIsWrapping() {
    return this.isWrapping;
  }

  @Override
  public void createDungeon() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        dungeon[i][j] = new Node(i, j, count++);
        this.allNodes.add(this.dungeon[i][j]);
      }
    }
  }

  @Override
  public void potentialPaths() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        Node nodeOg = dungeon[i][j];
        if (i + 1 < this.rows) {
          Node node1 = dungeon[i + 1][j];
          this.potentialPaths.add(IPath.formEdges(nodeOg, node1));
        }
        if (j + 1 < this.cols) {
          Node node2 = dungeon[i][j + 1];
          this.potentialPaths.add(IPath.formEdges(nodeOg, node2));
        }
      }
    }
  }

  @Override
  public void wrappingPotentialPath() {
    for (int i = 0; i < this.rows; i++) {
      this.potentialPaths.add(IPath.formEdges(dungeon[i][this.cols - 1], dungeon[i][0]));
    }
    for (int j = 0; j < this.cols; j++) {
      this.potentialPaths.add(IPath.formEdges(dungeon[this.rows - 1][j], dungeon[0][j]));
    }
  }

  @Override
  public void kruskalAlgorithms() {
    makeSet(parent);

    int index = 0;
    while (index < vertices - 1) {
      IPath edge = this.potentialPaths.get(r.getInt(this.potentialPaths.size()));
      int x_set = find(parent, edge.getStart().getNid());
      int y_set = find(parent, edge.getEnd().getNid());

      if (x_set == y_set) {
        this.leftOverPaths.add(edge);
      } else {
        uniquePath.add(edge);
        index++;
        union(parent, x_set, y_set);
      }
      this.potentialPaths.remove(edge);
    }
    this.leftOverPaths.addAll(this.potentialPaths);
    this.potentialPaths.clear();
  }

  @Override
  public void addInterconnectivity() {
    List<IPath> tempLeftOver = new ArrayList<>();
    tempLeftOver.addAll(leftOverPaths);
    int tempInterConnectivity = this.interconnectivity;
    if (tempInterConnectivity > this.uniquePath.size()) {
      throw new IllegalArgumentException("Not enough edges. Try a lesser number.");
    }

    else {
      int index = 0;
      while (index <= this.interconnectivity - 1) {
        IPath edge = tempLeftOver.get(r.getInt(tempInterConnectivity));
        this.uniquePath.add(edge);
        index++;
        tempInterConnectivity--;
        tempLeftOver.remove(edge);
      }
    }
  }

  @Override
  public void updateDirections() {
    for (IPath p : this.uniquePath) {
      if (p.getStart().getRow() == p.getEnd().getRow()) {
        p.getStart().setEast();
        p.getStart().setCount();
        p.getEnd().setWest();
        p.getEnd().setCount();
      }
      if (p.getStart().getCol() == p.getEnd().getCol()) {
        p.getStart().setSouth();
        p.getStart().setCount();
        p.getEnd().setNorth();
        p.getEnd().setCount();
      }
    }
  }

  @Override
  public void setLocationType() {
    for (INode n : allNodes) {
      n.setType();
    }
  }

  @Override
  public void setTreasures() {
    int shuffleCave;
    for (INode n : allNodes) {
      if (n.getType() == LocationType.CAVE) {
        caveList.add(n);
      }
    }

    List<Treasure> listOfTreasures = new ArrayList<>();
    listOfTreasures.add(Treasure.DIAMOND);
    listOfTreasures.add(Treasure.RUBY);
    listOfTreasures.add(Treasure.SAPHIRE);
    int sizeOfCaveList = this.caveList.size();

    numberOfCavesToAssignTreasure = (sizeOfCaveList * this.percentageOfTreasures) / 100;

    List<INode> tempCaveList = new ArrayList<>();
    tempCaveList.addAll(this.caveList);
    // Collections.shuffle(caveList,);
    for (int i = 0; i < numberOfCavesToAssignTreasure; i++) {

      shuffleCave = r.getInt(tempCaveList.size());
      int number = r.getInt(3) + 1;
      for (int j = 0; j < number; j++) {
        // int abc = r.getInt(3);
        tempCaveList.get(shuffleCave).setTreasure(listOfTreasures.get(j));
      }
      tempCaveList.remove(shuffleCave);
    }
  }

  @Override
  public int numberOfCavesWithTreasures() {
    int count = 0;
    for (INode n : this.caveList) {
      if (n.getTreasures().size() > 0) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int treasurePercentageValue() {
    return this.numberOfCavesToAssignTreasure;
  }

  @Override
  public List<INode> getCaveList() {
    return new ArrayList<>(this.caveList);
  }

  // ------------Setting up list of edges with path length 5 or more------------

  @Override
  public void adjlist() {
    for (IPath path : this.uniquePath) {
      adj[path.getStart().getNid()].add(path.getEnd().getNid());
      adj[path.getEnd().getNid()].add(path.getStart().getNid());
    }
  }

  @Override
  public void minFivePath() {

    List<Integer> tempV = new ArrayList<>();

    for (INode n : this.allNodes) {
      if (n.getType() == LocationType.CAVE) {
        tempV.add(n.getNid());
      }
    }

    for (int k = 0; k < tempV.size(); k++) {
      for (int j = 0; j < tempV.size(); j++) {

        boolean[] visited = new boolean[this.vertices];
        Arrays.fill(visited, false);

        minPath = Integer.MAX_VALUE;
        pCount = 0;

        int u = tempV.get(k);
        int v = tempV.get(j);
        minFivePathHelper(visited, u, v);

        if (minPath >= 5) {
          IPath path = new Path(allNodes.get(u), allNodes.get(v));
          startEnd.add(path);
        }
      }
    }
  }

  private void minFivePathHelper(boolean[] visited, int src, int des) {
    visited[src] = true;

    if (src == des) {
      if (minPath > pCount) {
        minPath = pCount;
      }
    }

    else {
      for (int i : adj[src]) {
        int v = i;

        if (!visited[v]) {
          pCount++;
          minFivePathHelper(visited, v, des);
        }
      }
    }

    visited[src] = false;
    pCount--;
  }

  @Override
  public void setStartEnd() {
    this.minFivePath();
  }

  // -------------------------Moving the player--------------------------------------

  @Override
  public String setPlayerStartAndEnd() {
    // Collections.shuffle(this.startEnd);
    startEndPath = r.getInt(this.startEnd.size());
    StringBuilder sb = new StringBuilder();

    sb.append("MY START LOCATION: " + this.startEnd.get(startEndPath).getStart().getNid() + "\n");
    sb.append("MY END LOCATION: " + this.startEnd.get(startEndPath).getEnd().getNid() + "\n");
    // sb.append("Directions Available: " + this.startEnd.get(0).getStart().directionsOfNode());

    player = new Player(this.startEnd.get(startEndPath).getStart(), this.rows, this.cols);

    if (this.startEnd.get(startEndPath).getStart().getTreasures().size() > 0) {
      player.setPickedTreasures(this.startEnd.get(startEndPath).getStart().getTreasures());
      sb.append(this.startEnd.get(startEndPath).getStart().getTreasures().toString());
    }
    player.setAllNodes(this.allNodes);
    return sb.toString();
  }

  @Override
  public List<IPath> getStartEnd() {
    return new ArrayList<>(this.startEnd);
  }

  @Override
  public int getStartEndpath() {
    return this.startEndPath;
  }

  @Override
  public String possibleMovements() {
    StringBuilder sb = new StringBuilder();
    sb.append("CHOOSE A DIRECTION\n");
    sb.append(player.getCurrent().possibleDirections().toString());
    return sb.toString();
  }

  @Override
  public String playerDetails() {
    StringBuilder sb = new StringBuilder();
    sb.append("The player is currently at location: " + player.getCurrent().getNid() + "\n");
    if (player.getAllPickedTreasures().size() == 0) {
      sb.append("The player has not collected any treasures so far.");
    } else {
      sb.append("The Treasures collected so far by the player are: \n");
      sb.append(player.getAllPickedTreasures().toString() + "\n");
    }
    return sb.toString();
  }

  @Override
  public String playerLocationDetails() {
    StringBuilder sb = new StringBuilder();
    sb.append("Available directions from Node " + player.getCurrent().getNid() + " are:\n");
    sb.append(player.getCurrent().possibleDirections().toString() + "\n");
    if (player.getCurrent().getTreasures().size() == 0) {
      sb.append("There are no Treasures in this location\n");
    } else {
      sb.append("The Treasures available at Node " + player.getCurrent().getNid() + " are:\n");
      sb.append(player.getCurrent().getTreasures().toString() + "\n");
    }
    return sb.toString();
  }

  @Override
  public String playerMovement(int move) {
    if (move > player.getCurrent().possibleDirections().size() || move < 1) {
      return String.format("Enter valid direction\n");
    }
    StringBuilder sb = new StringBuilder();
    int m = move;

    List<Directions> tempAvailableDirections = player.getCurrent().possibleDirections();

    // sb.append(tempAvailableDirections.toString());

    Directions temp = tempAvailableDirections.get(m - 1);
    sb.append(player.move(temp));
    sb.append("\n-------------------------------------------"
        + "--------------------------------------------\n");

    return sb.toString();
  }

  @Override
  public String gameEnded() {
    StringBuilder sb = new StringBuilder();
    sb.append(player.getAllPickedTreasures().toString() + "\n");
    sb.append("GAME ENDED");

    return sb.toString();
  }

  @Override
  public boolean hasEnded() {
    return this.startEnd.get(startEndPath).getEnd().getNid() != player.getCurrent().getNid();
  }


  @Override
  public String printUniquePath() {
    String printUniquePaths = "";
    StringBuilder sb = new StringBuilder();
    sb.append("PATH FROM ONE NODE TO EVERYOTHER NODE AFTER KRUSKAL\n");
    for (IPath p : this.uniquePath) {
      printUniquePaths = String.format(p.toString());
      sb.append(printUniquePaths + "\n");
    }
    return sb.toString();
  }

  @Override
  public String printLeftOverPath() {
    String printLeftOverPaths = "";
    StringBuilder sb = new StringBuilder();
    sb.append("LEFTOVER PATHS AFTER RUNNING KRUSKALS ALGORITHM\n");
    for (IPath p : this.leftOverPaths) {
      printLeftOverPaths = String.format(p.toString());
      sb.append(printLeftOverPaths + "\n");
    }
    return sb.toString();
  }

  @Override
  public String printDungeon() {
    String printDungeon = "";
    StringBuilder sb = new StringBuilder();
    sb.append("ALL THE NODES IN A DUNGEON JUST AFTER ITS CONSTRUCTION\n");
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        printDungeon = String.format(dungeon[i][j].toString());
        sb.append(printDungeon + "\n");
      }
    }
    return sb.toString();
  }

  @Override
  public String printPotentialPaths() {
    String printPotentialPaths = "";
    StringBuilder sb = new StringBuilder();
    for (IPath p : this.potentialPaths) {
      printPotentialPaths = String.format(p.toString());
      sb.append(printPotentialPaths + "\n");
    }
    return sb.toString();
  }

  @Override
  public String printPotentialPathsDirections() {
    String printDungeon = "";
    StringBuilder sb = new StringBuilder();
    sb.append("ALL THE NODES IN DUNGEON AFTER KRUSKAL AND ADDING "
        + "INTERCONNCETIVITY ALONG WITH POSSIBLE DIRECTIONS AND TREASURES\n");
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        printDungeon = String
            .format("\nNODE: " + dungeon[i][j].getNid() + "\n" + "Possible Directions: "
                + dungeon[i][j].directionsOfNode() + "\n" + "Location type of Node: "
                + dungeon[i][j].locationOfNode())
            + "\n" + "-------------------------------------------"
            + "-----------------------------------------------";
        sb.append(printDungeon + "\n");
      }
    }
    return sb.toString();
  }

  private void makeSet(int[] parent) {
    for (int i = 0; i < vertices; i++) {
      parent[i] = i;
    }
  }

  private int find(int[] parent, int vertex) {
    if (parent[vertex] != vertex) {
      return find(parent, parent[vertex]);
    }
    return vertex;
  }

  private void union(int[] parent, int x, int y) {
    int x_set_parent = find(parent, x);
    int y_set_parent = find(parent, y);
    parent[y_set_parent] = x_set_parent;
  }

  @Override
  public String displayDungeon() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < this.rows; i++) {
      for (int k = 0; k < 3; k++) {
        for (int j = 0; j < this.cols; j++) {
          INode temp = dungeon[i][j];
          if (k == 0) {
            if (temp.getNorth()) {
              sb.append("  ").append("|").append("   ");
            } else {
              sb.append("      ");
            }
          } else if (k == 1) {
            if (temp.getWest()) {
              sb.append("- ");
            } else {
              sb.append("  ");
            }
            if (temp.getType() == LocationType.CAVE) {
              if (temp.getNid() < 10) {
                sb.append(" ").append(temp.getNid());
              } else {
                sb.append(temp.getNid());
              }
            } else {
              sb.append("T ");
            }
            if (temp.getEast()) {
              sb.append(" -");
            } else {
              sb.append("  ");
            }
          } else {
            if (temp.getSouth()) {
              sb.append("  ").append("|").append("   ");
            } else {
              sb.append("      ");
            }
          }
          sb.append(" ");
        }
        sb.append("\n");
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  // @Override
  // public String printMatrix() {
  // StringBuilder sb = new StringBuilder();
  // for (int i = 0; i < dungeon.length; i++) {
  // for (int j = 0; j < dungeon[i].length; j++) {
  // if (dungeon[i][j].getNid() < 10) {
  // sb.append("0" + dungeon[i][j].getNid());
  // }
  // if (dungeon[i][j].getNid() > 9) {
  // sb.append(dungeon[i][j].getNid());
  // }
  //
  // if (this.dungeon[i][j].getEast()) {
  // sb.append(" -");
  // }
  // if (!this.dungeon[i][j].getEast()) {
  // sb.append(" ");
  // }
  // if (this.dungeon[i][j].getWest()) {
  // sb.append("- ");
  // }
  // if (!this.dungeon[i][j].getWest()) {
  // sb.append(" ");
  // }
  // }
  // sb.append("\n");
  // }
  // return sb.toString();
  // }
}
