package game;

import edu.monash.fit2099.engine.*;

/** Class representing the Player. */
public class Player extends Actor {

  private Menu menu = new Menu();
  private Ecopoints ecopoints = new Ecopoints();

  /**
   * Constructor.
   *
   * @param name Name to call the player in the UI
   * @param displayChar Character to represent the player in the UI
   * @param hitPoints Player's starting number of hitpoints
   */
  public Player(String name, char displayChar, int hitPoints) {
    super(name, displayChar, hitPoints);
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
            || destination.getActor() instanceof Allosaur) {
          closeDinosaur++;
        }
      }
    }
    if (closeDinosaur > 0) {
      actions.add(new FeedingAction());
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
}
