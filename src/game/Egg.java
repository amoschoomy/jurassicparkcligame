package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/** A class to represent dinosaur egg. */
public class Egg extends PortableItem {
  private int age = 0;
  private String species;
  private boolean hatchable;
  private Player owner;

  public Egg(String species, boolean hatchable, Player owner) {
    super("Egg", 'E');
    if (species == "Stegosaur" || species == "Allosaur") {
      this.species = species;
    } else {
      throw new IllegalArgumentException("Species only can be stegosaur or allosaur");
    }
    this.hatchable = hatchable;
    this.owner = owner;
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);

    if (!hatchable) {
      for (Item item : currentLocation.getItems()) {
        // meaning that the egg is dropped to ground so current location itemList contain it
        if (item == this) {
          hatchable = true;
        }
      }
    }

    if (hatchable) {
      age += 1;
    }

    if (age >= 20) {
      if (!currentLocation.containsAnActor()) {
        // need to wait for no dinosaur then only hatched a new dinosaur
        currentLocation.removeItem(this); //
        if (this.species == "Stegosaur") {
          // it is stegosaur
          Stegosaur newStegosaurBaby = new Stegosaur("Stegosaur", "baby", owner);
          currentLocation.addActor(newStegosaurBaby);
          owner.gainEcopoints(100);
        } else {
          // it is allosaur
          Allosaur newAllosaurBaby = new Allosaur("Allosaur", "baby", owner);
          currentLocation.addActor(newAllosaurBaby);
          owner.gainEcopoints(1000);
        }
      }
    }
  }
}
