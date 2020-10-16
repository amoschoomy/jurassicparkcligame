package game;

/** Class for MealKit in the game. Extends PortableItem class */
public class MealKit extends PortableItem {
  private FoodType type;

  /**
   * Calls superclass constructor Adds capaobility of Food Type
   *
   * @param type Food Type
   * @throws IllegalArgumentException if type is not Carnivore or Herbivore type
   */
  public MealKit(FoodType type) {
    super("Meal Kit", 'M');
    if (type != FoodType.CARNIVORES && type != FoodType.HERBIVORES)
      throw new IllegalArgumentException("Type must be Herbivore or Carnivore");
    this.type = type;
    this.addCapability(type);
  }
}
