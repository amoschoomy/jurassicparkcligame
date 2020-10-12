package game;

public class MealKit {
  private String type;

  public MealKit(String type) {
    if (type != "Carnivores" && type != "Herbivore")
      throw new IllegalArgumentException("Type must be Herbivore or Carnivore");
    this.type = type;
  }
}
