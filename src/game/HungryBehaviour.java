package game;

import edu.monash.fit2099.engine.*;

import java.util.HashMap;

/** A behaviour subclass to produce breeding ability to dinosaurs to born child. */
public class HungryBehaviour extends Action implements Behaviour {

  /**
   * A method to check if a fruit is adjacent to the dinosaur
   *
   * @param actor an Actor basically pointing the dinosaur itself
   * @param map the gameMap
   */
  private boolean isFruitAround(Actor actor, GameMap map) {
    Location currentPosition = map.locationOf(actor);
    int closeFruit = 0;
    for (Exit exit : currentPosition.getExits()) {
      Location destination = exit.getDestination();
      if (destination.canActorEnter(actor)) {
        if (destination.getItems() instanceof Fruit) {
          closeFruit++;
        }
      }
    }

    return closeFruit > 0;
  }

  /**
   * A method to check if grass is adjacent to the dinosaur
   *
   * @param actor an Actor basically pointing the dinosaur itself
   * @param map the gameMap
   */
  private boolean isGrassAround(Actor actor, GameMap map) {
    Location currentPosition = map.locationOf(actor);
    int closeGrass = 0;
    for (Exit exit : currentPosition.getExits()) {
      Location destination = exit.getDestination();
      if (destination.canActorEnter(actor)) {
        if (destination.getGround() instanceof Grass) {
          closeGrass++;
        }
      }
    }

    return closeGrass > 0;
  }

  /**
   * A method to check if a dinosaur is adjacent to the dinosaur
   *
   * @param actor an Actor basically pointing the dinosaur itself
   * @param map the gameMap
   */
  private boolean isDinosaurCorpseAround(Actor actor, GameMap map) {
    Location currentPosition = map.locationOf(actor);
    int closeDino = 0;
    for (Exit exit : currentPosition.getExits()) {
      Location destination = exit.getDestination();
      if (destination.canActorEnter(actor)) {
        for (Item item : destination.getItems()) {
          if (item instanceof Corpse) {
            closeDino++;
          }
        }
      }
    }

    return closeDino > 0;
  }
  private HashMap<String,Integer> findDinasour (Actor actor, GameMap map){
    int x=map.getXRange().min();
    int y=map.getYRange().min();
    HashMap<String,Integer> coordinates=new HashMap<String,Integer>(2);
    for(int i = map.getXRange().max(); i >x ; i--) {
      for(int j = map.getYRange().max(); j >y ; j++) {
        if (map.at(i,j).getActor() instanceof Allosaur ||map.at(i,j).getActor() instanceof Stegosaur ||map.at(i,j).getActor() instanceof Archaeopteryx ||map.at(i,j).getActor() instanceof Agilisaurus)
        coordinates.put("x",i);
        coordinates.put("y",j);
      }

    }
    return coordinates;
    }

  @Override
  public String execute(Actor actor, GameMap map) {
    getAction(actor, map);
    return actor
        + " at ("
        + map.locationOf(actor).x()
        + ","
        + map.locationOf(actor).y()
        + ") is getting hungry!";
  }

  @Override
  public Action getAction(Actor actor, GameMap map) {
    Location currentPosition = map.locationOf(actor);

    // For Stegosaur
    if ((actor instanceof Stegosaur) && (isFruitAround(actor, map) || isGrassAround(actor, map))) {
      for (Exit exit : currentPosition.getExits()) {
        Location destination = exit.getDestination();
        if (destination.canActorEnter(actor)) {
          // Hunt for fruit
          for (Item item : destination.getItems()) {
            if (item instanceof Fruit) {
              Fruit fruit = (Fruit) item;
              map.moveActor(actor, destination);
              Stegosaur stegosaur = (Stegosaur) actor;
              stegosaur.eatFruit(fruit);
              System.out.println(stegosaur + " eats Fruit");
              destination.removeItem(item);
              return this;
            }
          }
          // Hunt for grass
          if (destination.getGround() instanceof Grass) {
            Grass grass = (Grass) destination.getGround();
            map.moveActor(actor, destination);
            Stegosaur stegosaur = (Stegosaur) actor;
            stegosaur.grazeGrass(grass);
            System.out.println(stegosaur + " grazed Grass");
            destination.setGround(new Dirt());
            return this;
          }
        }
      }

      // For Allosaur
    } else if ((actor instanceof Allosaur) && isDinosaurCorpseAround(actor, map)) {
      for (Exit exit : currentPosition.getExits()) {
        Location destination = exit.getDestination();
        if (destination.canActorEnter(actor)) {
          // Hunt for corpse
          for (Item item : destination.getItems()) {
            if (item instanceof Corpse) {
              Corpse corpse = (Corpse) item;
              map.moveActor(actor, destination);
              Allosaur allosaur = (Allosaur) actor;
              allosaur.eatCorpse(corpse);
              System.out.println(allosaur + " ate corpse");
              destination.removeItem(item);
              return this;
            }
          }
        }
      }
      	//For Agilisaurus
    }
    else if ((actor instanceof Agilisaurus) && (isDinosaurCorpseAround(actor, map) ||isFruitAround(actor, map) || isGrassAround(actor, map))) {
        for (Exit exit : currentPosition.getExits()) {
          Location destination = exit.getDestination();
          if (destination.canActorEnter(actor)) {
            // Hunt for corpse
            for (Item item : destination.getItems()) {
              if (item instanceof Corpse) {
                Corpse corpse = (Corpse) item;
                map.moveActor(actor, destination);
                Agilisaurus agilisaurus = (Agilisaurus) actor;
                agilisaurus.eatCorpse(corpse);
                System.out.println(agilisaurus + " ate corpse");
                destination.removeItem(item);
                return this;
                
              }else if(item instanceof Fruit) {
            	  // Hunt for fruit
                  Fruit fruit = (Fruit) item;
                  map.moveActor(actor, destination);
                  Agilisaurus agilisaurus = (Agilisaurus) actor;
                  agilisaurus.eatFruit(fruit);
                  System.out.println(agilisaurus + " eats Fruit");
                  destination.removeItem(item);
                  return this;
                }
                  
            }
            
            // Hunt for grass
            if (destination.getGround() instanceof Grass) {
              Grass grass = (Grass) destination.getGround();
              map.moveActor(actor, destination);
              Agilisaurus agilisaurus = (Agilisaurus) actor;
              agilisaurus.grazeGrass(grass);
              System.out.println(agilisaurus + " grazed Grass");
              destination.setGround(new Dirt());
              return this;
            }
            
      
            
          }
        }
      }
    else if(actor.hasCapability(FlyAbility.FLY)){
      int x=findDinasour(actor, map).get("x");
      int y=findDinasour(actor, map).get("y");
      Location destination=map.at(x,y);
      map.moveActor(actor,destination);
      return this;
    }
    
    
    
    
    
    
    
    else {
      // if no foods around , just wander
      WanderBehaviour wander = new WanderBehaviour();
      return wander.getAction(actor, map);
    }
    return null; // if all place occupied and can't go
  }

  @Override
  public String menuDescription(Actor actor) {
    return "";
  }
}
