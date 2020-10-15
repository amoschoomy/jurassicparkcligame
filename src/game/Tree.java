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
      addFruitToTree();
    }
    if (Math.random() >= 0.3 && !fruits.isEmpty()) {
      location.addItem(fruits.remove(0));
    }
    if(location.x()+3<=80){
    if (location.map().at(location.x() + 1, location.y()).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.7) {
      location.map().at(location.x() + 2, location.y()).setGround(new Grass());
    }}
    if(location.y()+2<=24){
    if (location.map().at(location.x(), location.y() + 1).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.7) {
      location.map().at(location.x() , location.y()+2).setGround(new Grass());
    }}
if (location.x()+2>=0){
    if (location.map().at(location.x() - 1, location.y()).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.7) {
      location.map().at(location.x() - 2, location.y()).setGround(new Grass());
    }}
if (location.y()-2>=0){
    if (location.map().at(location.x(), location.y() - 1).getGround().getDisplayChar() == '.'
        && Math.random() >= 0.7) {
      location.map().at(location.x(), location.y()-2).setGround(new Grass());
    }
  }}

  public void addFruitToTree() {
    fruits.add(new Fruit());
  }

  public void removeFruitFromTree(Fruit fruit) {
    fruits.remove(fruit);
  }

  public ArrayList<Fruit> getFruits() {
    return new ArrayList<>(fruits);
  }
}
