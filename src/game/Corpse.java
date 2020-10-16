package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/** A class to represent dinosaur dead body. */
public class Corpse extends Item {
  public Corpse() {
    super("Corpse", 'C', false);
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
  }
}
