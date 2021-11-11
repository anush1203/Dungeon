package driver;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

import dungeon.Dungeon;
import dungeon.IDungeon;
import dungeon.RandomGenerator;
import dungeon.RandomInterface;

import java.util.Scanner;

/**
 * The Driver is where the entire implementation comes together. We build the dungeon and move the
 * player in this class.
 * 
 * @author anush
 *
 */
public class Driver {

  /**
   * This is a method that allows us to run our dungeon game.
   * 
   * @param args used to pass dungeon parameters.
   */
  public static void main(String[] args) {
    
    if (args.length < 5 ) {
      throw new IllegalArgumentException("\n Invalid number of arguments are passed. "
              + "Please enter a valid row, column, "
              + "wrapping condition, interconnectivity value and treasure percentage value.\n");
    }

    if (!(parseInt(args[0]) >= 5 && parseInt(args[0]) <= 100)
            || !(parseInt(args[1]) >= 5 && parseInt(args[1]) <= 100)
            || !(args[2].equals("false") || args[2].equals("true"))
            || parseInt(args[3]) < 0
            || !(parseInt(args[4]) >= 0 && parseInt(args[4]) <= 100)) {
      throw new IllegalArgumentException("Invalid agruments please enter the correct inputs");
    }
    
    RandomInterface r = new RandomGenerator();

    IDungeon d = new Dungeon(parseInt(args[0]), parseInt(args[1]), parseBoolean(args[2]),
        parseInt(args[3]), parseInt(args[4]), r);
    //IDungeon d = new Dungeon(5, 5, true, 4, 50, r);
    // System.out.println(d.getRows());
    // System.out.println(d.getCols());
    d.createDungeon();
    //System.out.println(d.printDungeon());
    d.potentialPaths();
    if (d.getIsWrapping()) {
      d.wrappingPotentialPath();
    }
    //    System.out
    //        .println("ALL POSSIBLE PATHS AFTER GENERATING OUR DUNGEONN AND BEFORE RUNNING
    //KRUSKAL\n");
    //System.out.println(d.printPotentialPaths());
    d.kruskalAlgorithms();
    //System.out.println("***********************************************");
    //System.out.println(d.printUniquePath());
    //System.out.println(d.printLeftOverPath());
    //    System.out
    //        .println("LIST OF POTENTIAL PATHS AFTER CHOOSING ALL PATHS FOR KRUSKAL 
    //AND LEFTOVER..."
    //            + "HAS TO BE EMPTY\n");
    //System.out.println(d.printPotentialPaths());
    d.addInterconnectivity();
    //    System.out.println("PATHS IN DUNGEON AFTER ADDING INTERCONNECTIVITY");
    //    System.out.println(d.printUniquePath());
    d.updateDirections();
    d.setLocationType();
    d.setTreasures();
    //System.out.println(d.printPotentialPathsDirections());
    d.adjlist();
 
    d.setStartEnd();

    System.out.println(d.displayDungeon());
    //System.out.println(d.printMatrix());

    System.out.println(d.setPlayerStartAndEnd());
    Scanner sc = new Scanner(System.in);

    do {
      System.out.println(d.possibleMovements());
      int move = sc.nextInt();
      System.out.println(d.playerMovement(move));
      //System.out.println(d.playerDetails());
    } 
    while (d.hasEnded());
    System.out.println(d.gameEnded());
    sc.close();
  }

}
