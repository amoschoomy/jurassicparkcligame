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
    grass.harvest(map.locationOf(actor));
    String action = "harvesting grass... at" + map.locationOf(actor).toString();
    return action;
  }

  @Override
  public String menuDescription(Actor actor) {
    return actor.toString() + "harvested grass to become hay";
  }
}
