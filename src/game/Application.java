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
		String[] newArray=new String[map.size()];
    	for (int i = 0; i< map.size(); i++) {
    	String x=map.get(i);
    	boolean modified=false;
			for (int j = 0; j <x.length() ; j++) {
		  		char c=x.charAt(j);
		  			if(c=='.'&& Math.random()>0.99){
		  				modified=true;
		  				char[] mapIn=x.toCharArray();
		  				mapIn[j]='g';
		  				String newMap=String.valueOf(mapIn);
		  				x=newMap;
		  				newArray[i]=newMap; }

      }if (!modified) newArray[i]=x; }
		List<String> newMap = Arrays.asList(newArray);

				GameMap gameMap = new GameMap(groundFactory, newMap );
		world.addGameMap(gameMap);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(9, 4));
		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur"));
		gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur"));
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
