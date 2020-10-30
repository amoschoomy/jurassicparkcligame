package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/** A class to represent dinosaur dead body. */
public class Corpse extends Item {
  private String species ;
  public Corpse(String species) {
    super("Corpse", 'C', false);
    this.species = species ; 
  }
  
  public String getSpecies() {
	  return this.species ; 
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
  }
}
