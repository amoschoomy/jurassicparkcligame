package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class HarvestAction extends Action {
  private Grass grass;

  public HarvestAction(Grass grass) {
    this.grass = grass;
  }

  @Override
  public String execute(Actor actor, GameMap map) {
    if (actor instanceof Player){
      ((Player) actor).getEcopoints().gain(1);
      grass.harvest(map.locationOf(actor));
      String action = "harvesting grass... at" + map.locationOf(actor).toString();
      return action;
    }
    else{
      throw  new IllegalArgumentException("Only Players allowed to harvest");
    }
  }

  @Override
  public String menuDescription(Actor actor) {
    return actor.toString() + "harvested grass to become hay and gained 1 ecopoint";
  }
}