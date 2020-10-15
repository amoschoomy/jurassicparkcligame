package game;

import java.util.Scanner;

import edu.monash.fit2099.engine.Location;

public class Egg extends PortableItem {
  private int age = 0;
  private String species  ; 
  private boolean hatchable ;

  public Egg(String species,boolean hatchable) {
    super("Egg", 'E');
    if(species == "Stegosaur" || species == "Allosaur") {
		this.species = species;
	}else {
		throw new IllegalArgumentException("Species only can be stegosaur or allosaur");
	}
    this.hatchable = hatchable ; 
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    if(hatchable) {
    	age += 1;
    }
    
    
    if (age == 20) {
    	currentLocation.removeItem(this);
    
    	if(this.species == "Stegosaur") {
    		// it is stegosaur
    		Stegosaur newStegosaurBaby = new Stegosaur("Stegosaur","baby") ; 
    		currentLocation.addActor(newStegosaurBaby);
    	}else {
    		// it is allosaur
    		Allosaur newAllosaurBaby = new Allosaur("Allosaur","baby") ; 
    		currentLocation.addActor(newAllosaurBaby);
    	}
    	
    	
    }
    
  }
}
