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
//    actions.add(new HarvestAction((Grass) map.locationOf(this).getGround()));
//    actions.add(new PluckFruitAction((Tree) map.locationOf(this).getGround()));

    if (lastAction.getNextAction() != null) return lastAction.getNextAction();
    return menu.showMenu(this, actions, display);
  }

  public Ecopoints getEcopoints() {
    return ecopoints;
  }
}
