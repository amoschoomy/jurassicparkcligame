package game;

import edu.monash.fit2099.engine.Item;

import java.util.HashMap;

public class VendingMachine extends Item {
  private HashMap<String, Integer> itemsSold = new HashMap<>();

  public VendingMachine() {
    super("Vending Machine", 'V', false);
  }

  public String displayItems() {
    String description = "";
    for (HashMap.Entry<String, Integer> entry : itemsSold.entrySet()) {
      description += "Items = " + entry.getKey() + ", Price = " + entry.getValue() + "\n";
    }
    return description;
  }

  public void addItemsToVendingMachine(String item, int price) {
    itemsSold.put(item, price);
  }

  public int getItemPrice(String item) {
    if (itemsSold.get(item) == null) {
      throw new IllegalArgumentException("Item is not sold");
    } else return itemsSold.get(item);
  }

  public HashMap<String, Integer> getListOfItemsSold() {
    return new HashMap<>(itemsSold);
  }
  public Item sellItem(String item) {
  Item item1;
      if (item.equals("Hay")){
        item1=new Hay(); }
      else if (item.equals("Fruit"))
        item1=new Fruit();
      else if (item.equals("LaserGun"))
        item1=new LaserGun();
      else if (item.equals("Carnivore"))
      item1=new MealKit(FoodType.CARNIVORES);
      else if (item.equals("Herbivore"))
        item1=new MealKit(FoodType.HERBIVORES);
      else if(item.equals("Allosaur Egg"))
          item1=new Egg("Allosaur",false);
      else if(item.equals("Stegosour Egg"))
          item1=new Egg("Stegosaur",false);
      else{
        item1=null;
      }
      return item1;
  }}
