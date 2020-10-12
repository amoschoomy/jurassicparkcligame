package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;

public class Tree extends Ground {
  private int age = 0;
  private ArrayList<Fruit> fruits = new ArrayList<>();

  public Tree() {
    super('+');
  }

  @Override
  public void tick(Location location) {
    super.tick(location);

    age++;
    if (age == 10) displayChar = 't';
    if (age == 20) displayChar = 'T';
    if (age >= 20 && Math.random() > 0.5) {
      fruits.add(new Fruit());
    }
    if (Math.random() >= 0.3 && !fruits.isEmpty()) {
      location.addItem(fruits.remove(0));
    }
    if (location.map().at(location.x() + 1, location.y()).getGround().getDisplayChar() == '.'
        && location.map().at(location.x() + 2, location.y()).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.2) {
      location.map().at(location.x() + 3, location.y()).setGround(new Grass());
    }

    if (location.map().at(location.x(), location.y() + 1).getGround().getDisplayChar() == '.'
        && location.map().at(location.x(), location.y() + 2).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.2) {
      location.map().at(location.x() + 3, location.y()).setGround(new Grass());
    }

    if (location.map().at(location.x() - 1, location.y()).getGround().getDisplayChar() == '.'
        && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.2) {
      location.map().at(location.x() - 3, location.y()).setGround(new Grass());
    }

    if (location.map().at(location.x(), location.y() - 1).getGround().getDisplayChar() == '.'
        && location.map().at(location.x() - 2, location.y()).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.2) {
      location.map().at(location.x() - 3, location.y()).setGround(new Grass());
    }
  }
}
