package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Grass class for the game. Extends Ground class in Engine
 *
 * @author Amos
 */
public class Grass extends Ground {

  /** Uses superclass constructor */
  public Grass() {
    super('g');
  }

  @Override
  public void tick(Location location) {
    super.tick(location);
    if (location.x() + 3 <= location.map().getXRange().max()
        && location.map().at(location.x() + 3, location.y()).getGround().getDisplayChar() != '~') {
      if (location.map().at(location.x() + 1, location.y()).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x() + 2, location.y()).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x() + 3, location.y()).setGround(new Grass());
      }
    }

    if (location.y() + 3 <= location.map().getYRange().max()
        && location.map().at(location.x() , location.y()+3).getGround().getDisplayChar() != '~') {
      if (location.map().at(location.x(), location.y() + 1).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x(), location.y() + 2).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x(), location.y() + 3).setGround(new Grass());
      }
    }
    if (location.x() - 3 >= location.map().getXRange().min()
        && location.map().at(location.x() - 3, location.y()).getGround().getDisplayChar() != '~') {

      if (location.map().at(location.x() - 1, location.y()).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x() - 3, location.y()).setGround(new Grass());
      }
    }

    if (location.y() - 3 >= location.map().getYRange().min()
        && location.map().at(location.x(), location.y()-3).getGround().getDisplayChar() != '~') {
      if (location.map().at(location.x(), location.y() - 1).getGround().getDisplayChar() == 'g'
          && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == 'g'
          && Math.random() >= 0.8) {
        location.map().at(location.x(), location.y() - 3).setGround(new Grass());
      }
    }
  }

  /**
   * Harvest grass at the location. Adds Hay into inventory
   *
   * @param location Location of grass
   * @param player Current Player
   */
  public void harvest(Location location, Player player) {
    player.addItemToInventory(new Hay());
  }
}
