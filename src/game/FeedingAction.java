package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Scanner;

/** An action subclass to allow player to feed dinosaur and gain eco points */
public class FeedingAction extends Action {
  private Actor feedTarget;
  private ArrayList<Location> feedTargetLocationList = new ArrayList<Location>();

  /**
   * A method to show calculate the amount of the items in the player inventory and to show the
   * items menu for feed.
   *
   * @param actor an Actor pointing the dinosaur
   */
  private String displayItemsToFeed(Actor actor) {
    int egg = 0;
    int carnivoreMealkit = 0;
    int herbivoreMealkit = 0;
    int hay = 0;
    int fruit = 0;

    String description = "";

    // loop thru the player inventory list to get amount of each kind of items
    for (Item item : actor.getInventory()) {
      if (item instanceof Fruit) {
        fruit++;
      }
      if (item instanceof Hay) {
        hay++;
      }
      if (item instanceof Egg) {
        egg++;
      }

      if (item instanceof MealKit) {
        MealKit mealkit = (MealKit) item;
        if (mealkit.hasCapability(FoodType.HERBIVORES)) {
          herbivoreMealkit++;
        } else {
          carnivoreMealkit++;
        }
      }
    }
    // some menu to prepare to show for different dinosaur
    String carnivoreMenu =
        "1) CarnivoreMealkit: " + carnivoreMealkit + "\n" + "2) Egg: " + egg + "\n";
    String herbivoreMenu =
        "1) Hay :"
            + hay
            + "\n"
            + "2) Fruit: "
            + fruit
            + "\n"
            + "3) HerbivoreMealkit: "
            + herbivoreMealkit
            + "\n";
    
    
    String omnivoreMenu =
    		"1) Hay :"
    	            + hay
    	            + "\n"
    	            + "2) Fruit: "
    	            + fruit
    	            + "\n"
    	            + "3) HerbivoreMealkit: "
    	            + herbivoreMealkit
    	            + "\n"
    	            + "4) CarnivoreMealkit: "
    	            + carnivoreMealkit
    	            + "\n"
    	            + "5) Egg: "
    	            + egg
    	            + "\n";

    if (feedTarget instanceof Stegosaur) {
      description += herbivoreMenu;
    } else if (feedTarget instanceof Allosaur) {
      description += carnivoreMenu;
    } else if (feedTarget instanceof Agilisaurus) {
        description += omnivoreMenu;
    } else {
      description += "Only dinosaurs can be feed!";
    }
    return description;
  }

  @Override
  public String execute(Actor actor, GameMap map) {

    if (actor instanceof Player) {

      // First ask the actor the target of feeding
      for (Exit exit : map.locationOf(actor).getExits()) {
        Location destination = exit.getDestination();
        if (destination.containsAnActor()) {
          // if theres an actor in the location
          if (destination.getActor() instanceof Stegosaur || destination.getActor() instanceof Allosaur || destination.getActor() instanceof Agilisaurus) {
            feedTargetLocationList.add(destination);

          } 
        }
      }

      //

      String whoToFeed = "";
      for (int i = 0; i < feedTargetLocationList.size(); i++) {
        whoToFeed +=
            i
                + ") ("
                + feedTargetLocationList.get(i).x()
                + ","
                + feedTargetLocationList.get(i).y()
                + ")"
                + "\n";
      }

      boolean targetStatus = true;
      while (targetStatus) {
        try {
          Scanner scanOne = new Scanner(System.in);
          System.out.println("Who you wanna feed? , type the index to chose the dinosaur to feed");
          System.out.println(whoToFeed);
          int indexChosen = scanOne.nextInt();
          if (indexChosen > feedTargetLocationList.size() || indexChosen < 0) {
            throw new IllegalArgumentException("Index chosen out of range!");
          }

          targetStatus = false;
          feedTarget = feedTargetLocationList.get(indexChosen).getActor();

        } catch (IllegalArgumentException e) {
          targetStatus = true;
        }
      }

      // Asking player what item he want to use for feed
      boolean itemStatus = true;
      String selectedItem = "";
      while (itemStatus) {
        try {
          Scanner scanTwo = new Scanner(System.in);
          System.out.println(displayItemsToFeed(actor));
          System.out.println(
              "Select which item you wanna use to feed the dinosaur? Type their item index :");
          int selectedItemNumber = scanTwo.nextInt();
          if (feedTarget instanceof Stegosaur) {
            if (selectedItemNumber == 1) {
              // hay
              itemStatus = false;
              selectedItem = "Hay";
            } else if (selectedItemNumber == 2) {
              // fruit
              itemStatus = false;
              selectedItem = "Fruit";
            } else if (selectedItemNumber == 3) {
              // herbivore mealkit
              itemStatus = false;
              selectedItem = "HerbivoreMealkit";
            } else {
              throw new IllegalArgumentException("Wrong input, chose number 1 - 3");
            }

          } else if(feedTarget instanceof Allosaur) {
            // Allosaur

            if (selectedItemNumber == 1) {
              // Carnivore Mealkit
              itemStatus = false;
              selectedItem = "CarnivoreMealkit";
            } else if (selectedItemNumber == 2) {
              // Egg
              itemStatus = false;
              selectedItem = "Egg";
            } else {
              throw new IllegalArgumentException("Wrong input, chose number 1 - 2");
            }
            
          }else if(feedTarget instanceof Agilisaurus) {
              // Agilisaurus
        	  if (selectedItemNumber == 1) {
                  // hay
                  itemStatus = false;
                  selectedItem = "Hay";
                } else if (selectedItemNumber == 2) {
                  // fruit
                  itemStatus = false;
                  selectedItem = "Fruit";
                } else if (selectedItemNumber == 3) {
                  // herbivore mealkit
                  itemStatus = false;
                  selectedItem = "HerbivoreMealkit";
                } else if (selectedItemNumber == 4) {
                  // Carnivore Mealkit
                itemStatus = false;
                selectedItem = "CarnivoreMealkit";
                } else if (selectedItemNumber == 5) {
                // Egg
                itemStatus = false;
                selectedItem = "Egg";
              } else {
                throw new IllegalArgumentException("Wrong input, chose number 1 - 2");
              }
            }
        } catch (IllegalArgumentException e) {
          itemStatus = true;
        }
      }

      /*scanner end here*/

      
      
        if (selectedItem.equals("Hay")) {
          for (Item item : actor.getInventory()) {
            if (item instanceof Hay) {
              Hay hay = (Hay) item;
              if (feedTarget instanceof Stegosaur) {
            	  ((Stegosaur) feedTarget).eatHay(hay);
              }else if(feedTarget instanceof Agilisaurus) {
            	  ((Agilisaurus) feedTarget).eatHay(hay);
              }
              actor.removeItemFromInventory(item);
              ((Player) actor).getEcopoints().gain(10);
              return actor.toString()
                  + " fed the "
                  + feedTarget.toString()
                  + " with Hay! Gained 10 eco points";
            }
          }
          return "Can't find Fruit inside the inventory!";
        }
        if (selectedItem.equals("Fruit")) {
          for (Item item : actor.getInventory()) {
            if (item instanceof Fruit) {
              Fruit fruit = (Fruit) item;
              
              if (feedTarget instanceof Stegosaur) {
            	  ((Stegosaur) feedTarget).eatFruit(fruit);
              }else if(feedTarget instanceof Agilisaurus) {
            	  ((Agilisaurus) feedTarget).eatFruit(fruit);
              }
              actor.removeItemFromInventory(item);
              ((Player) actor).getEcopoints().gain(15);
              return actor.toString()
                  + " fed the "
                  + feedTarget.toString()
                  + " with Fruit! Gained 15 eco points";
            }
          }
          return "Can't find Fruit inside the inventory!";
        }
        if (selectedItem.equals("HerbivoreMealkit")) {
          // mealkit will not gain ecopoints
          for (Item item : actor.getInventory()) {
            if (item instanceof MealKit && item.hasCapability(FoodType.HERBIVORES)) {
              MealKit harbivoreMealkit = (MealKit) item;
              
              if (feedTarget instanceof Stegosaur) {
            	  ((Stegosaur) feedTarget).eatMealKit(harbivoreMealkit);
              }else if(feedTarget instanceof Agilisaurus) {
            	  ((Agilisaurus) feedTarget).eatMealKit(harbivoreMealkit);
              }
              actor.removeItemFromInventory(item);
              return actor.toString()
                  + " fed the "
                  + feedTarget.toString()
                  + " with Herbivore MealKit! The stegosaur foodLevel become 100 now!";
            }
          }

          return "Can't find HerbivoreMealKit inside the inventory!";
        }
     
        if (selectedItem.equals("CarnivoreMealkit")) {
          // mealkit will not gain ecopoints
          for (Item item : actor.getInventory()) {
            if (item instanceof MealKit && item.hasCapability(FoodType.CARNIVORES)) {
              MealKit carnivoreMealkit = (MealKit) item;
              
              if (feedTarget instanceof Allosaur) {
            	  ((Allosaur) feedTarget).eatMealKit(carnivoreMealkit);
              }else if(feedTarget instanceof Agilisaurus) {
            	  ((Agilisaurus) feedTarget).eatMealKit(carnivoreMealkit);
              }
              actor.removeItemFromInventory(item);
              return actor.toString()
                  + " fed the "
                  + feedTarget.toString()
                  + " with Carnivore MealKit! The allosaur foodLevel become 100 now!";
            }
          }
          return "Can't find CarnivoreMealKit inside the inventory!";
        }
        if (selectedItem.equals("Egg")) {
          // egg will not gain ecopoints
          for (Item item : actor.getInventory()) {
            if (item instanceof Egg) {
              Egg egg = (Egg) item;             
              if (feedTarget instanceof Allosaur) {
            	  ((Allosaur) feedTarget).eatEgg(egg);
              }else if(feedTarget instanceof Agilisaurus) {
            	  ((Agilisaurus) feedTarget).eatEgg(egg);
              }
              actor.removeItemFromInventory(item);
              return actor.toString() + " fed the " + feedTarget.toString() + " with Eggs!";
            }
          }
          return "Can't find Egg inside the inventory!";
        }
      
    } else {

      throw new IllegalArgumentException("Only Players allowed to feed ");
    }

    return "Feed Errors!";
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
