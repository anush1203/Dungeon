package dungeon;

/**
 * A dungeon can have 2 different types of nodes.
 * They can either be cave or a tunnel.
 * A tunnel must only have 2 path and a cave can have 1,3 or 4.
 * 
 * @author anush
 *
 */
public enum LocationType {

  CAVE("cave"),
  TUNNEL("tunnel");
  
  String type;
  
  private LocationType(String type) {
    this.type = type;
  }
  
  public String getType() {
    return this.type;
  }
}
