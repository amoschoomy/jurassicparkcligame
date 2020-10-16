package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.Scanner;

public class VendingMachineAction extends Action {
  private VendingMachine vendingMachine;

  public VendingMachineAction(VendingMachine vendingMachine) {
    this.vendingMachine = vendingMachine;
  }

  @Override
  public String execute(Actor player, GameMap map) {
    boolean status = true;
    boolean success = false;
    String userItem = null;
    while (status) {
      try {
        Scanner sc = new Scanner(System.in);
        System.out.println(vendingMachine.displayItems());
        System.out.println("Type 'Exit' to exit and waste a turn!");
        System.out.println("Ecopoints available: "+((Player)player).getEcopoints().getPoints());
        System.out.println("Which item do you want to buy?");
        userItem = sc.nextLine();
        if (userItem.equals("Exit"))
          break;
        if (vendingMachine.sellItem(userItem, (Player) player) != null) {
          if (((Player) player).getEcopoints().getPoints()
              < vendingMachine.getItemPrice(userItem)) {
            throw new IllegalArgumentException("Not enough ecopoints to buy item");
          } else {
            ((Player) player).getEcopoints().spend(vendingMachine.getItemPrice(userItem));
            player.addItemToInventory(vendingMachine.sellItem(userItem, (Player) player));
            map.locationOf(player).removeItem(vendingMachine);
            map.locationOf(player).addItem(vendingMachine);
            status = false;
            success = true;
          }
        } else {
          throw new IllegalArgumentException("Item not found!");
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Sorry error occured, please try again");
        continue;
      }
    }
    if (success) {
      Item itemBought = vendingMachine.sellItem(userItem, (Player) player);
      return player.toString()
          + " bought "
          + itemBought
          + " for the price of "
          + vendingMachine.getItemPrice(userItem);
    } else {
      return "No item bought";
    }
  }

  @Override
  public String menuDescription(Actor actor) {
    return "Buy an item from Vending Machine";
  }

  @Override
  public String hotkey() {
    return "V";
  }
}
