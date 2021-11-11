package dungeon;

/**
 * A blue print for random classes. 
 * This is used to ensure we are able to test our implementation.
 * 
 * @author anush
 *
 */
public interface RandomInterface {

  /**
   * To get a random integer value between the specified value.
   * 
   * @param x The range in between which we want the random number.
   * @return A random number.
   */
  int getInt(int x);
}
