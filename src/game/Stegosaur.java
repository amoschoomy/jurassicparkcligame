package game;

import edu.monash.fit2099.engine.*;

/** A herbivorous dinosaur. */
public class Stegosaur extends Actor {
  private Behaviour behaviour;
  private int age = 0;
  private int foodLevel;
  private int starvationLevel = 0;
  private int waterLevel = 0;
  private int thirstLevel = 0;
  private final Player owner;
  private int pregnantPeriodCount = 0;

  /**
   * Constructor. All Stegosaurs are represented by a 'd' and have 100 hit points.
   *
   * @param name the name of this Stegosaur
   * @param lifeStage the life stage of this Stegosaur
   * @param owner the player who own the Stegosaur
   */
  public Stegosaur(String name, String lifeStage, Player owner) {
    super(name, 'd', 100);

    this.owner = owner;
    behaviour = new WanderBehaviour();

    this.addCapability(LiveStatus.LIVE);
    this.addCapability(FoodType.HERBIVORES); // Stegosaur is a herbivores

    if (lifeStage == "adult") {
      this.addCapability(LifeStage.ADULT);
      this.age = 30;
      this.foodLevel = 60;
      this.waterLevel = 20;
      this.displayChar = 'D';
    } else if (lifeStage == "baby") {
      // baby stegosaur
      this.addCapability(LifeStage.BABY);
      this.foodLevel = 10;
      this.waterLevel = 10;
    } else {
      throw new IllegalArgumentException("life stage only can be adult or baby");
    }

    if (Math.random() > 0.5) {
      this.addCapability(Gender.MALE); // set gender as male ;
      System.out.println(this.toString() + ": " + Gender.MALE);
    } else {
      this.addCapability(Gender.FEMALE); // set gender as female
      System.out.println(this.toString() + ": " + Gender.FEMALE);
    }
  }

  /** This method is used to accumulate how many turns the dinosaur is starving */
  private void Starving() {
    this.starvationLevel += 1;
  }
  /** This method is used to accumulate how many turns the dinosaur is thirsting */
  private void Thirsting() {
    this.thirstLevel += 1;
  }

  /**
   * Raise water level
   *
   * @param x int value of water to be raised
   */
  private void raiseWaterLevel(int x) {
    if (this.waterLevel + x <= 100) {
      this.waterLevel += x;
    } else {
      this.waterLevel = 100;
    }
  }
  /**
   * This method is used to raise the food level of stegosaur
   *
   * @param x an int amount to be add into the stegosaur food level
   */
  private void raiseFoodLevel(int x) {
    if (this.foodLevel + x <= 100) {
      this.foodLevel += x;
    } else {
      this.foodLevel = 100;
    }
  }

  /** Drink water */
  public void drinkWater() {
    raiseWaterLevel(20);
  }

  /**
   * This method is used to eat grass
   *
   * @param grass a Grass object to be eaten by the stegosaur
   */
  public void grazeGrass(Grass grass) {
    raiseFoodLevel(5);
  }

  /**
   * This method is used to eat hay
   *
   * @param hay a Hay object to be eaten by the stegosaur
   */
  public void eatHay(Hay hay) {
    raiseFoodLevel(20);
  }

  /**
   * This method is used to eat fruit
   *
   * @param fruit a Fruit object to be eaten by the stegosaur
   */
  public void eatFruit(Fruit fruit) {
    raiseFoodLevel(30);
  }

  /**
   * This method is used to eat herbivore mealkit
   *
   * @param mealkit a MealKit object to be eaten by the stegosaur
   */
  public void eatMealKit(MealKit mealkit) {
    raiseFoodLevel(100);
  }

  /** This method is used to show if the stegosaur ready to breed */
  private boolean readyBreed() {
    boolean retVal = false;
    if (this.foodLevel > 60) {
      retVal = true;
    }

    return retVal;
  }

  /** This method is used to update the stegosaur state every turn */
  private void updateStegosaurState() {
    this.age++;

    if (this.age == 30 && this.hasCapability(LifeStage.BABY)) {
      // grow up
      this.removeCapability(LifeStage.BABY);
      this.addCapability(LifeStage.ADULT);
      this.displayChar = 'D';
    }

    if (this.foodLevel > 0) {
      this.foodLevel--;
    }

    if (this.waterLevel > 0) {
      this.waterLevel--;
    }

    if (this.foodLevel < 30) {
      this.behaviour = new HungryBehaviour();
    }

    if (this.foodLevel > 50) {
      this.behaviour = new BreedBehaviour();
    }

    if (this.foodLevel == 0) {
      this.Starving();
    } else {
      this.starvationLevel = 0;
    }

    if (this.waterLevel < 30) {
      this.behaviour = new ThirstBehaviour();
    }

    if (this.waterLevel == 0) {

      this.Thirsting();
    } else {
      this.thirstLevel = 0;
    }
  }

  /** This method is used to show if the stegosaur is pregnant */
  private boolean isPregnant() {
    return this.hasCapability(LifeStage.PREGNANT);
  }

  @Override
  public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
    return new Actions(new AttackAction(this));
  }

  /**
   * Figure out what to do next.
   *
   * <p>Stegosaur wanders around at random, or if no suitable MoveActions are available, it just
   * stands there. When foodLevel lower , stegosaur will become hungry and callout hungry behaviour
   * action. When it it ready to breed, it will mate as well.
   *
   * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
   */
  @Override
  public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

    updateStegosaurState(); // update stegosaur state

    if (isPregnant()) {
      pregnantPeriodCount++;
      if (pregnantPeriodCount == 1) {
        // prenant after 10 turns
        this.removeCapability(LifeStage.PREGNANT);
        this.removeCapability(LifeStage.ADULT);
        Egg egg = new Egg("Stegosaur", true, owner);
        map.locationOf(this).addItem(egg);
        pregnantPeriodCount = 0;
      }
    }

    if (this.starvationLevel == 20 || this.hitPoints == 0 || this.thirstLevel == 20) {
      // stegoosaur die
      this.removeCapability(LiveStatus.LIVE);
      this.addCapability(LiveStatus.DEAD);
      map.locationOf(this).addItem(new Corpse("Stegosaur"));
      map.removeActor(this);
    }

    if (this.foodLevel <= 0) {
      System.out.println("Stegosaur is unwake due to hungry now.");
      return new DoNothingAction();
    }

    Action wander = behaviour.getAction(this, map);
    if (wander != null) {
      return wander;
    } else {
      return new DoNothingAction();
    }
  }
}
