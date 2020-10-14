package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class VendingMachineAction extends Action {
  private VendingMachine vendingMachine;
  private Item selectedItem;

  public VendingMachineAction(VendingMachine vendingMachine,Item selectedItem) {
    this.vendingMachine = vendingMachine;
    if(vendingMachine.getListOfItemsSold().get(selectedItem)!=null)
        this.selectedItem=selectedItem;
    else{
      throw new IllegalArgumentException("Item not sold here");
    }
  }
  @Override
  public String execute(Actor player, GameMap map) {
    if (((Player) player).getEcopoints().getPoints() < vendingMachine.getItemPrice(selectedItem)) {
      throw new IllegalArgumentException("Not enough ecopoints to buy item");
    } else {
      ((Player) player).getEcopoints().spend(vendingMachine.getItemPrice(selectedItem));
      player.addItemToInventory(selectedItem);
    }
    return player.toString() + "bought " + selectedItem.toString() + " for the price of";
  }


  @Override
  public String menuDescription(Actor actor) {
    return actor.toString() + "wants to buy an item";
  }
}
