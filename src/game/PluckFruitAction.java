package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class PluckFruitAction extends Action {
  private Tree tree;

  public PluckFruitAction(Tree tree) {
    this.tree = tree;
  }

  @Override
  public String execute(Actor actor, GameMap map) {
    String result = "";
    if (Math.random() >= 0.7 && !tree.getFruits().isEmpty()) {
      actor.addItemToInventory(tree.getFruits().get(0));
      tree.removeFruitFromTree(tree.getFruits().get(0));
      result = "Congratulations, fruit plucked and added to inventory";
    } else {
      result = "You search the tree for fruit, but you can’t find any ripe ones.";
    }
    return result;
  }

  @Override
  public String menuDescription(Actor actor) {
    return "Try to pluck fruit from Tree";
  }

  @Override
  public String hotkey() {
    return "P";
  }
}
