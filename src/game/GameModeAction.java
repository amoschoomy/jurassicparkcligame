package game;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Just an action to exit/change the Game Mode when in game
 *
 * @author Foong Shee Yao
 */
public class GameModeAction extends Action {
  
  @Override
  public String execute(Actor actor, GameMap map) {
    if (actor instanceof Player) {
    	Scanner scanner = new Scanner(System.in) ; 
    	System.out.println("Are you sure you want to exit the current game mode? Type 1 as Yes,0 as No");   	 
    	boolean stat = false ; 
    	String s = "";
    	while(!stat) {
    		try {
	    	int DoYouWantToExit = scanner.nextInt() ;
	    	if(DoYouWantToExit == 0) {
	    		s = "Back to the Game!" ; 
	    		stat = true ;
	    	}else if(DoYouWantToExit == 1) {
	    		System.out.println("Game Mode Exit Successfully!");
	    		stat = true ;
	    		map.removeActor(actor);
	    	}else {
	    		System.out.println("Please type 0 or 1 only as game mode changing commands...type again"); 
	    	}
    		}
    		catch (InputMismatchException exception) 
    		{ 
    		    System.out.println("Integers only, please."); 
    		    scanner.next();
    		} 
    	}
    	return s ;
    } else {
      throw new IllegalArgumentException("Only Players allowed to have game mode settings");
    }
  }

  @Override
  public String menuDescription(Actor actor) {
    return "Exit current Game Mode";
  }

  @Override
  public String hotkey() {
    return "E";
  }
}
