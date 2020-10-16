package game;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

import java.util.Arrays;
import java.util.List;

/** The main class for the Jurassic World game. */
public class Application {

  public static void main(String[] args) {
    World world = new World(new Display());

    FancyGroundFactory groundFactory =
        new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Grass());

    List<String> map =
        Arrays.asList(
            "................................................................................",
            "................................................................................",
            ".....#######....................................................................",
            ".....#_____#....................................................................",
            ".....#_____#....................................................................",
            ".....###.###....................................................................",
            "................................................................................",
            "......................................+++.......................................",
            ".......................................++++.....................................",
            "...................................+++++........................................",
            ".....................................++++++.....................................",
            "......................................+++.......................................",
            ".....................................+++........................................",
            "................................................................................",
            "............+++.................................................................",
            ".............+++++..............................................................",
            "...............++........................................+++++..................",
            ".............+++....................................++++++++....................",
            "............+++.......................................+++.......................",
            "................................................................................",
            ".........................................................................++.....",
            "........................................................................++.++...",
            ".........................................................................++++...",
            "..........................................................................++....",
            "................................................................................");

    GameMap gameMap = new GameMap(groundFactory, map);
    world.addGameMap(gameMap);
    for (int i = 0; i < map.size(); i++) {
      int x = 0 + (int) (Math.random() * ((map.size() - 1) + 1));
      int y = 0 + (int) (Math.random() * ((map.size() - 1) + 1));
      if (Math.random() > 0.8) gameMap.at(x, y).setGround(new Grass());
    }
    Player player = new Player("Player", '@', 100);
    Player enemy= new Player("CPU",'|',1000);
    world.addPlayer(player, gameMap.at(9, 4));
    world.addPlayer(enemy,gameMap.at(10,4));
    player.gainEcopoints(10000);
    // Place a pair of stegosaurs in the middle of the map
    gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur", "adult",player));
    gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur", "adult",player));
    gameMap.at(31,12).addActor(new Allosaur("Allosaur","adult",enemy));
    VendingMachine vm = new VendingMachine();
    vm.addItemsToVendingMachine("Hay", 10);
    vm.addItemsToVendingMachine("Fruit", 15);
    vm.addItemsToVendingMachine("Carnivore", 500);
    vm.addItemsToVendingMachine("Herbivore", 100);
    vm.addItemsToVendingMachine("LaserGun", 500);

    vm.addItemsToVendingMachine("Stegosaur Egg", 200);
    vm.addItemsToVendingMachine("Allosaur Egg", 1000);
    gameMap.at(9, 6).addItem(vm);

    world.run();
  }
}
