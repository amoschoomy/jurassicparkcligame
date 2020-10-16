package game;

public class MealKit extends PortableItem {
  private FoodType type;

  public MealKit(FoodType type) {
    super("Meal Kit", 'M');
    if (type != FoodType.CARNIVORES && type != FoodType.HERBIVORES)
      throw new IllegalArgumentException("Type must be Herbivore or Carnivore");
    this.type = type;
    this.addCapability(type);
  }
}
