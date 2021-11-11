package dungeon;

import java.util.Random;

/**
 * Generates a random number within the specified range.
 * Used for implementation purposes.
 * 
 * @author anush
 *
 */
public class RandomGenerator implements RandomInterface {

  
    
  @Override
  public int getInt(int x) {
    Random r = new Random();
    return r.nextInt(x);
  }
}
