package game;

import edu.monash.fit2099.engine.WeaponItem;

/** Class for Laser Gun weapon in the game. Extends Weapon Item class */
public class LaserGun extends WeaponItem {
  /** Uses superclass constructor */
  public LaserGun() {
    super("Laser Gun", 'L', 50, "shoots");
  }
}
