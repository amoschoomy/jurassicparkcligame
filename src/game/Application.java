package game;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    GameMap gameMap1=new GameMap(groundFactory,'.',80,24);
    world.addGameMap(gameMap1);

    for (int i = 0; i < map.size(); i++) {
      int x = 0 + (int) (Math.random() * ((map.size() - 1) + 1));
      int y = 0 + (int) (Math.random() * ((map.size() - 1) + 1));
      if (Math.random() > 0.8) gameMap.at(x, y).setGround(new Grass());
    }
    gameMap.at(17,17).setGround((new Water()));
    gameMap.at(18,17).setGround(new Water());
    gameMap.at(19,17).setGround(new Water());
    gameMap.at(18,18).setGround(new Water());
    Player player = new Player("Player", '@', 100,gameMap);
    player.addMapToGameMaps(gameMap1);
    player.gainEcopoints(10000);
    // Place a pair of stegosaurs in the middle of the map
//    gameMap.at(32, 1).addActor(new Stegosaur("Stegosaur", "adult", player));
    gameMap.at(16, 16).addActor(new Stegosaur("Stegosaur", "adult", player));
//    gameMap.at(18, 20).addActor(new Archaeopteryx("Archaeopteryx", "adult", player));
    gameMap.at(15, 17).addActor(new Agilisaurus("Agilisaurus", "adult", player));
    gameMap.at(15, 18).addActor(new Agilisaurus("Agilisaurus", "adult", player));
    VendingMachine vm = new VendingMachine();
    vm.addItemsToVendingMachine("Hay", 10);
    vm.addItemsToVendingMachine("Fruit", 15);
    vm.addItemsToVendingMachine("Carnivore", 500);
    vm.addItemsToVendingMachine("Herbivore", 100);
    vm.addItemsToVendingMachine("LaserGun", 500);
 
    vm.addItemsToVendingMachine("Stegosaur Egg", 200);
    vm.addItemsToVendingMachine("Allosaur Egg", 1000);
    vm.addItemsToVendingMachine("Agilisaurus Egg", 500);
    vm.addItemsToVendingMachine("Archaeopteryx Egg", 1000);
    gameMap.at(9, 6).addItem(vm);
    
    Scanner scanner = new Scanner(System.in);
    
    boolean status = false ; 
    while(!status) {
    	//Add player
    	world.addPlayer(player, gameMap.at(0, 0));
    	
    	//Chose game mode
    	boolean gameModeStatus = false ; 
    	while(!gameModeStatus) {
	    	System.out.println("Please select a game mode:" + "\n" + "1)Challenge" + "\n" + "2)Sandbox" + "\n" + "3)Quit");
	        int GameMode = scanner.nextInt() ; 
	        System.out.println("Your comamnd is: " + GameMode);
	        if(GameMode == 1) {
	        	//Challenge GameMode
	        	player.setPlayerGameMode("Challenge");
	        	System.out.println("Player chose Challenge mode"); 
	        	System.out.println("What's the winning ecopoints you wanna set:");
	        	int winningEcopoints = scanner.nextInt() ; 
	        	System.out.println("What's the move you wanna set:");
	        	int numberOfMoves = scanner.nextInt() ;
	        	System.out.println("Your Winning Ecopoints: " + winningEcopoints);
	        	System.out.println("Your Limit moves: " + numberOfMoves);
	        	player.setTotalEcoPointToWin(winningEcopoints);
	        	player.setPlayerMove(numberOfMoves);
	        	gameModeStatus = true ; 
	        }
	        else if(GameMode == 2) {
	        	//Sandbox GameMode
	        	player.setPlayerGameMode("Sandbox");
	        	System.out.println("Player chose Sandbox mode");
	        	gameModeStatus = true ;
	        }
	        else if(GameMode == 3) { 
	        	System.out.println("Player exit game");
	        	System.exit(0) ; 
	        }
	        else {
	        	System.out.println("Acceptable input are 1/2/3, please type again...");
	        }
        
    	}
        //Run the game
    	world.run();
    	
    	//ask again when game end
    	boolean playAgainStatus = false ; 
    	while(!playAgainStatus) {
	    	System.out.println("Do you want to play again? Type 1 as Yes,0 as No");
	    	int c = scanner.nextInt() ; 
	    	if(c==1) {
	    		System.out.println("Restarting game...");
	    		playAgainStatus = true ; 
	    	}else if(c==0) {
	    		playAgainStatus = true ;
	    		status = true ; 
	    		System.out.println("Program exit successfully...");
	    	}else {
	    		System.out.println("Acceptable input are 0 or 1, please type again...");
	    	}
    	
    	}
    }
    
    
   
    
  }
}
