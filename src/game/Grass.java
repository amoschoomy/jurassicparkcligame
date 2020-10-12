package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Grass extends Ground {

  private boolean harvested = false;

  public Grass() {
    super('g');
  }

  @Override
  public void tick(Location location) {
    super.tick(location);
    if (location.map().at(location.x() + 1, location.y()).getGround().getDisplayChar() == 'g'
        && location.map().at(location.x() + 2, location.y()).getGround().getDisplayChar() == 'g'
        && Math.random() >= 0.4) {
      location.map().at(location.x() + 3, location.y()).setGround(new Grass());
    }

    if (location.map().at(location.x(), location.y() + 1).getGround().getDisplayChar() == 'g'
        && location.map().at(location.x(), location.y() + 2).getGround().getDisplayChar() == 'g'
        && Math.random() >= 0.4) {
      location.map().at(location.x() + 3, location.y()).setGround(new Grass());
    }

    if (location.map().at(location.x() - 1, location.y()).getGround().getDisplayChar() == 'g'
        && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == 'g'
        && Math.random() >= 0.4) {
      location.map().at(location.x() - 3, location.y()).setGround(new Grass());
    }

    if (location.map().at(location.x(), location.y() - 1).getGround().getDisplayChar() == 'g'
        && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == 'g'
        && Math.random() >= 0.4) {
      location.map().at(location.x() - 3, location.y()).setGround(new Grass());
    }
  }
}