package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

public class WanderBehaviour implements Behaviour {

  private Random random = new Random();

  /**
   * Returns a MoveAction to wander to a random location, if possible. If no movement is possible,
   * returns null.
   *
   * @param actor the Actor enacting the behaviour
   * @param map the map that actor is currently on
   * @return an Action, or null if no MoveAction is possible
   */
  @Override
  public Action getAction(Actor actor, GameMap map) {
    ArrayList<Action> actions = new ArrayList<Action>();
    if (map.locationOf(actor)!=null){
      for (Exit exit : map.locationOf(actor).getExits()) {
      Location destination = exit.getDestination();
      if (destination.canActorEnter(actor)) {
        actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
      }
    }}
    if (map.locationOf(actor)!=null && actor.hasCapability(FlyAbility.FLY)){
      int x = 0 + (int) (Math.random() * ((map.getXRange().max() - 1) + 1));
      int y = 0 + (int) (Math.random() * ((map.getYRange().max() - 1) + 1));
      actions.add(map.at(x,y).getMoveAction(actor,"around","FLY"));
    }


      if (!actions.isEmpty()) {
      return actions.get(random.nextInt(actions.size()));
    } else {
      return null;
    }
  }
}
