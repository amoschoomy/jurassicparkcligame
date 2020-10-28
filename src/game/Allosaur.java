package game;

import edu.monash.fit2099.engine.*;

/** A carnivore dinosaur. */
public class Allosaur extends Actor {
  private Behaviour behaviour;
  private int age = 0;
  private int foodLevel; // baby foodLevel start range
  private int starvationLevel = 0;
  private int waterLevel=0;
  private int thirstLevel=0;
  private Player owner;
  private int pregnantPeriodCount = 0;
  /**
   * Constructor. All Allosaur are represented by a 'a' and have 100 hit points.
   *
   * @param name the name of this Allosaur
   * @param lifeStage the life stage of this Allosaur
   * @param owner the player who own the Allosaur
   */
  public Allosaur(String name, String lifeStage, Player owner) {
    super(name, 'a', 100);

    this.owner = owner;

    behaviour = new WanderBehaviour();

    this.addCapability(LiveStatus.LIVE);
    this.addCapability(FoodType.CARNIVORES); // Stegosaur is a carnivores

    if (lifeStage == "adult") {
      this.age = 30;
      this.addCapability(LifeStage.ADULT);
      this.foodLevel = 50;
      this.waterLevel=50;
      this.displayChar = 'A';
    } else if (lifeStage == "baby") {
      // baby stegosaur
      this.addCapability(LifeStage.BABY);
      this.foodLevel = 10;
      this.waterLevel=10;
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

  /** This method is used to accumulate how many turns the allosaur is starving */
  private void Starving() {
    this.starvationLevel += 1;
  }

  private void Thirsting(){
    this.thirstLevel+=1;
  }

  /**
   * This method is used to raise the food level of allosaur
   *
   * @param x an int amount to be add into the allosaur food level
   */
  private void raiseFoodLevel(int x) {
    if (this.foodLevel + x <= 100) {
      this.foodLevel += x;
    } else {
      this.foodLevel = 100;
    }
  }

  private void raiseWaterLevel(int x){
    if(this.waterLevel+x<=100){
      this.waterLevel+=x;
    }
    else{
      this.waterLevel=100;
    }
  }

  /**
   * This method is used to eat egg
   *
   * @param egg a Egg object to be eaten by the stegosaur
   */
  public void eatEgg(Egg egg) {
    raiseFoodLevel(10);
  }

  /**
   * This method is used to eat corpse
   *
   * @param corpse a Corpse object to be eaten by the allosaur
   */
  public void eatCorpse(Corpse corpse) {
    raiseFoodLevel(50);
  }

  public void drinkWater(){
    raiseWaterLevel(20);
  }

  /** This method is used to show if the allosaur ready to breed */
  private boolean readyBreed() {
    boolean retVal = false;
    if (this.foodLevel > 60) {
      retVal = true;
    }

    return retVal;
  }

  /**
   * This method is used to show if there are an opponent around the allosaur
   *
   * @param actor the actor which indicates the allosaur
   * @param map the gameMap
   */
  private boolean isOpponentAround(Actor actor, GameMap map) {
    Location currentPosition = map.locationOf(this);
    int closeOpponent = 0;
    for (Exit exit : currentPosition.getExits()) {
      Location destination = exit.getDestination();
      if (destination.containsAnActor()) {
        if (destination.getActor() instanceof Stegosaur) {
          closeOpponent++;
        }
      }
    }
    return closeOpponent > 0;
  }

  /** This method is used to update the allosaur state every turn */
  private void updateAllosaurState() {
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

    if (this.waterLevel>0){
      this.waterLevel--;
    }

    if (this.foodLevel < 30) {
      this.behaviour = new HungryBehaviour();
    }

    if (this.foodLevel > 50) {
      this.behaviour = new BreedBehaviour();
    }

    if (this.foodLevel == 0) {
      this.behaviour = null;
      this.Starving();
    } else {
      this.starvationLevel = 0;
    }

    if(this.waterLevel<30){
      this.behaviour=new ThirstBehaviour();
    }

    if(this.waterLevel==0){
      this.behaviour=null;
      this.Thirsting();
    }else{
      this.thirstLevel=0;
    }

  }

  /** This method is used to show if the allosaur is pregnant */
  private boolean isPregnant() {
    return this.hasCapability(LifeStage.PREGNANT);
  }

  /**
   * This method is used to eat carnivore mealkit
   *
   * @param mealkit a MealKit object to be eaten by the allosaur
   */
  public void eatMealKit(MealKit mealkit) {
    raiseFoodLevel(100);
  }

  @Override
  public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
    return new Actions(new AttackAction(this));
  }

  /**
   * Figure out what to do next.
   *
   * <p>Allosaur wanders around at random, or if no suitable MoveActions are available, it just
   * stands there. When foodLevel lower , allosaur will become hungry and callout hungry behaviour
   * action. When it it ready to breed, it will mate as well. It will attack stegosaur whose
   * adjacent to him.
   *
   * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
   */
  @Override
  public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

    updateAllosaurState(); // update allosaur state
    if (isOpponentAround(this, map)) {
      Location currentPosition = map.locationOf(this);
      int closeOpponent = 0;
      for (Exit exit : currentPosition.getExits()) {
        Location destination = exit.getDestination();
        if (destination.containsAnActor()) {
          if (destination.getActor() instanceof Stegosaur
              && closeOpponent < 1
              && Math.random() > 0.1) {
            destination.getActor().hurt(50);
            closeOpponent += 1;
            System.out.println(
                this.toString() + " hurt " + destination.getActor().toString() + " by 50 points");
          } else {
            System.out.println(
                this.toString()
                    + " tries to hurt "
                    + destination.getActor().toString()
                    + " but failed");
          }
        }
      }
    }

    if (isPregnant()) {
      pregnantPeriodCount++;
      if (pregnantPeriodCount == 10) {
        // prenant after 10 turns
        this.removeCapability(LifeStage.PREGNANT);
        this.removeCapability(LifeStage.ADULT);
        Egg egg = new Egg("Allosaur", true, owner);
        map.locationOf(this).addItem(egg);
        ;
        pregnantPeriodCount = 0;
      }
    }

    if (this.starvationLevel == 20 || this.hitPoints == 0|this.thirstLevel==20) {
      this.removeCapability(LiveStatus.LIVE);
      this.addCapability(LiveStatus.DEAD);
      map.locationOf(this).addItem(new Corpse());
      map.removeActor(this);
    }
    Action wander = behaviour.getAction(this, map);
    if (wander != null) {
      return wander;
    } else {
      return new DoNothingAction();
    }
  }
}
