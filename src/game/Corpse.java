package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Corpse extends Item {
	public Corpse() {
		super("Corpse" , 'C' , false); 
	}
	
	 @Override
	  public void tick(Location currentLocation) {
	    super.tick(currentLocation);
	    
	   
	  }
}
