package game;


import edu.monash.fit2099.engine.Location;

/**
 * Class for Fruit in the game. Extends Portable Item class
 * @author Amos
 */
public class Fruit extends PortableItem {
  private boolean riped = false;
  private int age = 0;

  /**
   * Uses superclass constructor
   */
  public Fruit() {
    super("Fruit", 'F');
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    age += 1;
    if (age == 20) displayChar = 'X';
    riped = true;
    if (age > 20 && Math.random() >= 0.5) currentLocation.removeItem(this);
  }
}
