package dungeon;

/**
 * Treasure are of 3 types. Diamonds, ruby and saphire.
 * They are randomly placed based on percentage of treasures inputted.
 * Treasures can only be placed in caves.
 * 
 * @author anush
 *
 */
public enum Treasure {

  DIAMOND("diamonds"),
  RUBY("ruby"),
  SAPHIRE("saphire");
  
  String treasure;
  
  private Treasure(String treasure) {
    this.treasure = treasure;
  }
  
  public String getTreasure() {
    return this.treasure;
  }
}
