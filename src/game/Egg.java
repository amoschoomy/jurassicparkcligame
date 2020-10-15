package game;

import java.util.Scanner;

import edu.monash.fit2099.engine.Location;

public class Egg extends PortableItem {
  private int age = 0;
  private String species  ; 

  public Egg(String species) throws Exception {
    super("Egg", 'E');
    if(species == "Stegosaur" || species == "Allosaur") {
		this.species = species;
	}else {
		throw new Exception("Species only can be stegosaur or allosaur"); 
	}
  }

  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);
    age += 1;
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
