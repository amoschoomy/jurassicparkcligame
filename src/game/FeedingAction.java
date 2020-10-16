package game;


import java.util.ArrayList;
import java.util.Scanner;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class FeedingAction extends Action {
	private Actor feedTarget ; 
	private ArrayList<Location> feedTargetLocationList = new ArrayList<Location>() ; 
	
	  private String displayItemsToFeed(Actor actor) {
		  int egg = 0  ;
	      int carnivoreMealkit = 0 ;
	      int herbivoreMealkit = 0 ;
	      int hay = 0 ;
	      int fruit = 0 ;
	      
	      String description = "" ; 
		  
	      //loop thru the player inventory list to get amount of each kind of items 
		  for(Item item : actor.getInventory()) {
			  if(item instanceof Fruit) {
				  fruit++ ; 
			  }
			  if(item instanceof Hay) {
				  hay++ ; 
			  }
			  if(item instanceof Egg) {
				  egg++ ; 
			  }
			  
			  if(item instanceof MealKit) {
				  MealKit mealkit = (MealKit) item ; 
				  if(mealkit.hasCapability(FoodType.HERBIVORES)) {
					  herbivoreMealkit ++ ; 
				  }else {
					  carnivoreMealkit++ ;
				  }
				   
			  }  
			  
		  }
		  //some menu to prepare to show for different dinosaur
		  String carnivoreMenu = "1) CarnivoreMealkit: " + carnivoreMealkit + "\n" +"2) Egg: " + egg + "\n" ;
		  String herbivoreMenu = "1) Hay :" + hay + "\n"  + "2) Fruit: " + fruit + "\n" + "3) HerbivoreMealkit: " + herbivoreMealkit+ "\n" ;
		  
		  if(feedTarget instanceof Stegosaur) {
			  description += herbivoreMenu ; 
		  }else if(feedTarget instanceof Allosaur) {
			  description += carnivoreMenu ; 
		  }else {
			  description+= "Only dinosaurs can be feed!" ; 
		  }
		  return description ; 
	  }

	  @Override
	  public String execute(Actor actor, GameMap map) {
		  
	  if(actor instanceof Player) {
		  
	  // First ask the actor the target of feeding   
	  for(Exit exit: map.locationOf(actor).getExits()) {
    		Location destination = exit.getDestination() ; 
    		if(destination.containsAnActor()) {
    			//if theres an actor in the location 
    			if(destination.getActor() instanceof Stegosaur) {
    				feedTargetLocationList.add(destination) ; 
    				
    			}else if(destination.getActor() instanceof Allosaur) {
    				feedTargetLocationList.add(destination) ;
    			}	
    		}
    	}
    	
    	//
    	
    	String whoToFeed = "" ; 
    	for(int i = 0 ; i < feedTargetLocationList.size() ; i ++) {
    		whoToFeed += i +") (" + feedTargetLocationList.get(i).x() + "," +feedTargetLocationList.get(i).y() + ")" + "\n"  ; 
    	}
    	
    	boolean targetStatus = true ; 
    	while(targetStatus) {
    		try {
    			Scanner scanOne = new Scanner(System.in) ; 
    	    	System.out.println("Who you wanna feed? , type the index to chose the dinosaur to feed") ;  
    	    	System.out.println(whoToFeed) ;
    	    	int indexChosen = scanOne.nextInt() ;
    	    	if(indexChosen >feedTargetLocationList.size() || indexChosen < 0 ) {
    	    		throw new IllegalArgumentException("Index chosen out of range!"); 
    	    	}
    	    	
    	    	targetStatus = false ; 
    	    	feedTarget = feedTargetLocationList.get(indexChosen).getActor();
    			
    			
    		}catch(IllegalArgumentException e) {
				targetStatus = true ; 
			}
    	}
    	
    
		  
		// Asking player what item he want to use for feed 
		boolean itemStatus = true  ; 
		String selectedItem = ""; 
		while(itemStatus) {
			try {
				Scanner scanTwo = new Scanner(System.in);
				System.out.println(displayItemsToFeed(actor)) ; 
				System.out.println("Select which item you wanna use to feed the dinosaur? Type their item index :");
				int selectedItemNumber = scanTwo.nextInt() ;
				if(feedTarget instanceof Stegosaur) {
					if(selectedItemNumber == 1) {
						// hay
						itemStatus = false ;
						selectedItem = "Hay";
					}
					else if(selectedItemNumber == 2) {
						// fruit
						itemStatus = false ;
						selectedItem = "Fruit";
					}
					else if(selectedItemNumber == 3) {
						// herbivore mealkit
						itemStatus = false ;
						selectedItem = "HerbivoreMealkit";
					}else {
						throw new IllegalArgumentException("Wrong input, chose number 1 - 3"); 
					}
					
				}else {
					//Allosaur
					
					if(selectedItemNumber == 1) {
						// Carnivore Mealkit
						itemStatus = false ;
						selectedItem = "CarnivoreMealkit";
					}
					else if(selectedItemNumber == 2) {
						// Egg
						itemStatus = false ; 
						selectedItem = "Egg";
					}else {
						throw new IllegalArgumentException("Wrong input, chose number 1 - 2"); 
					}
					
				}
			}catch(IllegalArgumentException e) {
				itemStatus = true ; 
			}
		}
		
		/*scanner end here*/
		
	    //if (actor instanceof Player) {
	    	if(feedTarget instanceof Stegosaur) {
	    		if(selectedItem == "Hay") {
	    			for(Item item: actor.getInventory()) {
	    				if(item instanceof Hay) {
	    					Hay hay = (Hay) item ; 
	    					((Stegosaur) feedTarget).eatHay(hay);    			
	    	    			actor.removeItemFromInventory(item);
	    	    			((Player) actor).getEcopoints().gain(10);
	    	    			return actor.toString() + "fed the " + feedTarget.toString() +" with Hay! Gained 10 eco points";
	    				}
	    			}
	    			
	    		}
	    		if(selectedItem == "Fruit") {
	    			for(Item item: actor.getInventory()) {
	    				if(item instanceof Fruit) {
	    					Fruit fruit = (Fruit) item ; 
	    					((Stegosaur) feedTarget).eatFruit(fruit);    			
	    	    			actor.removeItemFromInventory(item);
	    	    			((Player) actor).getEcopoints().gain(15);
	    	    			return actor.toString() + "fed the " + feedTarget.toString() +" with Fruit! Gained 15 eco points";
	    				}
	    			}
	    			
	    		}
	    		if(selectedItem == "HerbivoreMealKit") {
	    			//mealkit will not gain ecopoints
	    			for(Item item: actor.getInventory()) {
	    				if(item instanceof MealKit && item.hasCapability(FoodType.HERBIVORES)) {
	    					MealKit harbivoreMealkit = (MealKit) item ; 
	    					((Stegosaur) feedTarget).eatMealKit(harbivoreMealkit);    			
	    	    			actor.removeItemFromInventory(item);
	    	    			return actor.toString() + "fed the " + feedTarget.toString() +" with Herbivore MealKit! The stegosaur foodLevel become 100 now!";
	    				}
	    			}
	    			
	    		}
	    	}else {
	    		if(selectedItem == "CarnivoreMealKit") {
	    			//mealkit will not gain ecopoints
	    			for(Item item: actor.getInventory()) {
	    				if(item instanceof MealKit && item.hasCapability(FoodType.CARNIVORES)) {
	    					MealKit carnivoreMealkit = (MealKit) item ; 
	    					((Allosaur) feedTarget).eatMealKit(carnivoreMealkit);;    			
	    	    			actor.removeItemFromInventory(item);
	    	    			return actor.toString() + "fed the " + feedTarget.toString() +" with Carnivore MealKit! The allosaur foodLevel become 100 now!";
	    				}
	    			}
	    			
	    		}
	    		if(selectedItem == "Egg") {
	    			//egg will not gain ecopoints
	    			for(Item item: actor.getInventory()) {
	    				if(item instanceof Egg) {
	    					Egg egg = (Egg) item ; 
	    					((Allosaur) feedTarget).eatEgg(egg);    			
	    	    			actor.removeItemFromInventory(item);
	    	    			return actor.toString() + "fed the " + feedTarget.toString() +" with Eggs!";
	    				}
	    			}
	    			
	    		}
	    		
	    	}
	    } else {
	    	
	      throw new IllegalArgumentException("Only Players allowed to feed ");
	    }
	    
	  	return "Feed Errors!" ; 
	  }

	  @Override
	  public String menuDescription(Actor actor) {
	    return "Feed a dinosaur !";
	  }

	  @Override
	  public String hotkey() {
	    return "F";
	  }

}
