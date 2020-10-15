package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(),new Grass());
		
		List<String> map = Arrays.asList(
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


		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);
    	for (int i = 0; i < map.size(); i++) {
		int x=0 + (int)(Math.random() * ((map.size() - 1) + 1));
		int y=0 + (int)(Math.random() * ((map.size() - 1) + 1));
		if (Math.random()>0.8)
			gameMap.at(x,y).setGround(new Grass());

	}
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(9, 4));
		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur","adult"));
		gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur","adult"));
		VendingMachine vm=new VendingMachine();
		vm.addItemsToVendingMachine(new Hay(),10);
		vm.addItemsToVendingMachine(new Fruit(),15);
		vm.addItemsToVendingMachine(new MealKit("Carnivore"),500);
		vm.addItemsToVendingMachine(new MealKit("Herbivore"),100);
		vm.addItemsToVendingMachine(new LaserGun(),500);
		gameMap.at(11,11).addItem(vm);

			
		world.run();
	}
}
