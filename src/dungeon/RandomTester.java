package dungeon;

import java.util.Random;

/**
 * Generates a fixed number within the given range based on the given 
 * bound.
 * @author anush
 *
 */
public class RandomTester implements RandomInterface {

  @Override
  public int getInt(int x) {
    Random r = new Random(x);
    return r.nextInt(x);
  }
}
