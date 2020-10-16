package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Grass extends Ground {

  public Grass() {
    super('g');
  }

  @Override
  public void tick(Location location) {
    super.tick(location);
    if (location.x() + 3 <= 80) {
      if (location.map().at(location.x() + 1, location.y()).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x() + 2, location.y()).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x() + 3, location.y()).setGround(new Grass());
      }
    }

    if (location.y() + 3 <= 24) {
      if (location.map().at(location.x(), location.y() + 1).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x(), location.y() + 2).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x(), location.y() + 3).setGround(new Grass());
      }
    }
    if (location.x() - 3 >= 0) {

      if (location.map().at(location.x() - 1, location.y()).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x() - 3, location.y()).setGround(new Grass());
      }
    }

    if (location.y() - 3 >= 0) {
      if (location.map().at(location.x(), location.y() - 1).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x(), location.y() - 3).setGround(new Grass());
      }
    }
  }

  public void harvest(Location location, Player player) {
    player.addItemToInventory(new Hay());
  }
}
