package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {
		super('.');
	}

	@Override
	public void tick(Location location) {
		super.tick(location);
		if (Math.random()>0.1){
			location.map().at(location.x() - 3, location.y()).setGround(new Grass());
		}
	}
}
