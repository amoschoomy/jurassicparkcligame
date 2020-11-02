package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

public class Water extends Ground {
  /** Constructor. */
  public Water() {
    super('~');
  }

  @Override
  public boolean canActorEnter(Actor actor) {
    return actor instanceof Archaeopteryx;
  }
}
