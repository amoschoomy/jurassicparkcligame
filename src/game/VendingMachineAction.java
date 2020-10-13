package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class VendingMachineAction extends Action {
  private VendingMachine vendingMachine;

  public VendingMachineAction(VendingMachine vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  public String execute(Player player, GameMap map, Item item) {
    if (player.getEcopoints().getPoints() < vendingMachine.getItemPrice(item)) {
      throw new IllegalArgumentException("Not enough ecopoints to buy item");
    } else {
      player.getEcopoints().spend(vendingMachine.getItemPrice(item));
      player.addItemToInventory(item);
    }
    return player.toString() + "bought " + item.toString() + " for the price of";
  }

  @Override
  public String execute(Actor actor, GameMap map) {
    return null;
  }

  @Override
  public String menuDescription(Actor actor) {
    return actor.toString() + "wants to buy an item";
  }
}
