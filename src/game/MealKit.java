package game;

import edu.monash.fit2099.engine.Item;

public class MealKit extends PortableItem {
  private String type;

  public MealKit(String type) {
    super("Meal Kit",'M');
    if (type != "Carnivore" && type != "Herbivore")
      throw new IllegalArgumentException("Type must be Herbivore or Carnivore");
    this.type = type;
  }
}
