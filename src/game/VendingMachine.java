package game;

import edu.monash.fit2099.engine.Item;

import java.util.HashMap;

public class VendingMachine extends Item {
    private HashMap<Item,Integer> itemsSold=new HashMap<>();
    public VendingMachine(){
        super("Vending Machine",'v',false);

    }
    public String displayItems(){
    String description = "";
        for (HashMap.Entry<Item,Integer> entry : itemsSold.entrySet()) {
            description+="Items = " + entry.getKey() + ", Price = " + entry.getValue()+"\n";
        }
        return description;
    }
    public void addItemsToVendingMachine(Item item,int price){
        itemsSold.put(item,price);
    }
}
