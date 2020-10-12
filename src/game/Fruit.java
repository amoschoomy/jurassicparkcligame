package game;

import edu.monash.fit2099.engine.Location;

public class Fruit extends PortableItem {
  private boolean riped = false;
  private int age = 0;

  public Fruit(String name, char displayChar) {
    super(name, displayChar);
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    age += 1;
    if (age == 20) displayChar = 'X';
    if (age > 20 && Math.random()>=0.5) currentLocation.removeItem(this);
  }
}
