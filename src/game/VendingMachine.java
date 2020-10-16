package game;

import edu.monash.fit2099.engine.Item;

import java.util.HashMap;

/** Class to represent Vending Machine in the game. Extends Item class */
public class VendingMachine extends Item {
  private HashMap<String, Integer> itemsSold = new HashMap<>();

  /** Uses superclass constructor */
  public VendingMachine() {
    super("Vending Machine", 'V', false);
  }

  /**
   * Display items sold in vending machine
   *
   * @return String of items and their respective prices in Vending Machine
   */
  public String displayItems() {
    String description = "";
    for (HashMap.Entry<String, Integer> entry : itemsSold.entrySet()) {
      description += "Items = " + entry.getKey() + ", Price = " + entry.getValue() + "\n";
    }
    return description;
  }

  /**
   * Add items to vendingMachine
   *
   * @param item Item String representation
   * @param price Price of the object
   */
  public void addItemsToVendingMachine(String item, int price) {
    itemsSold.put(item, price);
  }

  /**
   * Get item price of the vending machine
   *
   * @param item String reprsentation of Item
   * @return Integer value of item price
   * @throws IllegalArgumentException if Item is not found in vending machine
   */
  public int getItemPrice(String item) {
    if (itemsSold.get(item) == null) {
      throw new IllegalArgumentException("Item is not sold");
    } else return itemsSold.get(item);
  }

  /**
   * Sell item to player
   *
   * @param item String representation of item to be sold
   * @param owner Owner of that soon to be bought item
   * @return Item object, otherwise null if item not in Vending Machine
   */
  public Item sellItem(String item, Player owner) {
    Item item1;
    if (item.equals("Hay")) {
      item1 = new Hay();
    } else if (item.equals("Fruit")) item1 = new Fruit();
    else if (item.equals("LaserGun")) item1 = new LaserGun();
    else if (item.equals("Carnivore")) item1 = new MealKit(FoodType.CARNIVORES);
    else if (item.equals("Herbivore")) item1 = new MealKit(FoodType.HERBIVORES);
    else if (item.equals("Allosaur Egg")) item1 = new Egg("Allosaur", false, owner);
    else if (item.equals("Stegosaur Egg")) item1 = new Egg("Stegosaur", false, owner);
    else {
      item1 = null;
    }
    return item1;
  }
}
