package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/** Class representing the Player. */
public class Player extends Actor {

  private Menu menu = new Menu();
  private Ecopoints ecopoints = new Ecopoints();
  private ArrayList<GameMap> maps=new ArrayList<>();

  /**
   * Constructor.
   *
   * @param name Name to call the player in the UI
   * @param displayChar Character to represent the player in the UI
   * @param hitPoints Player's starting number of hitpoints
   */
  public Player(String name, char displayChar, int hitPoints,GameMap currentMap) {
    super(name, displayChar, hitPoints);
    maps.add(currentMap);
  }

  @Override
  public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
    // Handle multi-turn Actions
    if (map.locationOf(this).getGround() instanceof Grass) {
      actions.add(new HarvestAction((Grass) map.locationOf(this).getGround()));
    }
    for (Item i : map.locationOf(this).getItems()) {
      if (i instanceof VendingMachine) {
        actions.add(new VendingMachineAction((VendingMachine) i));
      }
    }
    if (map.locationOf(this).getGround() instanceof Tree) {
      actions.add(new PluckFruitAction((Tree) map.locationOf(this).getGround()));
    }

    // check if player close to a dinosaur
    Location currentPosition = map.locationOf(this);
    int closeDinosaur = 0;
    for (Exit exit : currentPosition.getExits()) {
      Location destination = exit.getDestination();
      if (destination.containsAnActor()) {
        if (destination.getActor() instanceof Stegosaur
            || destination.getActor() instanceof Allosaur || destination.getActor() instanceof Agilisaurus || destination.getActor() instanceof Archaeopteryx) {
          closeDinosaur++;
        }
      }
    }
    if (closeDinosaur > 0) {
      actions.add(new FeedingAction());
    }

    if (currentPosition.x()==map.getXRange().min()&&currentPosition.y()==map.getYRange().min() && map==maps.get(0)){

      actions.add(new MoveActorAction(maps.get(1).at(maps.get(1).getXRange().max(),maps.get(1).getYRange().max()),"Left","Q"));
    }

    if (currentPosition.x()==map.getXRange().max()&&currentPosition.y()==map.getYRange().max() && map==maps.get(1)){
      actions.add(new MoveActorAction(maps.get(0).at(maps.get(0).getXRange().min(),maps.get(0).getYRange().min()),"Right","Q"));
    }

    if (lastAction.getNextAction() != null) return lastAction.getNextAction();
    return menu.showMenu(this, actions, display);
  }

  /**
   * Get Player Ecopoints object
   *
   * @return Ecopoints object
   */
  public Ecopoints getEcopoints() {
    return ecopoints;
  }

  /**
   * Gain ecopoints
   *
   * @param val int value for Ecopoints to be gained
   */
  public void gainEcopoints(int val) {
    ecopoints.gain(val);
  }
  public void addMapToGameMaps(GameMap map){
    maps.add(map);
  }

}
