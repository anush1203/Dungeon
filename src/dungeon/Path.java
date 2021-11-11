package dungeon;

/**
 * A path is basically an edge that connects 2 nodes in the dungeon.
 * A path will have a start node and an end node. 
 * 
 * @author anush
 *
 */
public class Path implements IPath {

  private final INode start;
  private final INode end;

  /**
   * Construct a path from start to end nodes.
   * 
   * @param start the start node.
   * @param end the end node.
   */
  public Path(INode start, INode end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException("Enter valid inputs");
    }
    this.start = start;
    this.end = end;
  }

  // public static Path formEdges(Node start, Node end) {
  // return new Path(start, end);
  // }

  @Override
  public INode getStart() {
    return this.start;
  }

  @Override
  public INode getEnd() {
    return this.end;
  }

  @Override
  public String toString() {
    return "Edge: {" + this.start.toString() + ", " + this.end.toString() + "}";
  }
}
