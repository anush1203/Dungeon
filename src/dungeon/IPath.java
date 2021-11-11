package dungeon;

/**
 * IPath is a blueprint for the Path class.
 * A programmer in the future can use this a reference to avoid bugs.
 * A path represents an edge in the dungeon.
 * 
 * @author anush
 *
 */
public interface IPath {

  /**
   * A method that takes 2 nodes and creates a new path object.
   * This is basically an edge.
   * 
   * @param start The start node.
   * @param end The end node.
   * @return
   */
  static IPath formEdges(INode start, INode end) {
    return new Path(start, end);
  }
  
  /**
   * A method to get the start node.
   * 
   * @return the start node.
   */
  INode getStart();
  
  /**
   * A method to get the end node.
   * 
   * @return the end node.
   */
  INode getEnd();
}
