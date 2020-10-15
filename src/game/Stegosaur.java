package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Actor {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
	private Behaviour behaviour;
	private int age = 0 ; 
	private int foodLevel ; 
	private int starvationLevel = 0 ; 
	private String lifeStage ;
	private char gender ;   

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 * 
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name , String lifeStage) {
		super(name, 'd', 100);
		
		behaviour = new WanderBehaviour();
		
		if(lifeStage == "baby" || lifeStage == "adult") {
			this.lifeStage = lifeStage;
			if(lifeStage == "adult"){
				this.foodLevel = 50 ; 
				this.displayChar = 'D' ;
			}else {
				//baby stegosaur 
				this.foodLevel = 10;
			}
		}
		
		if(Math.random() >0.5) {
			this.gender = 'm' ; // set gender as male ; 
		}else {
			this.gender = 'f' ; // set gender as female
		}

	}
	
	public void Starving() {
		this.starvationLevel +=1 ; 
	
	}
	
	public void raiseFoodLevel(int x){
		if(this.foodLevel+x<=100) {
			this.foodLevel += x ; 
		}else {
			this.foodLevel = 100 ; 
		}
	}
	
	
	public void grazeGrass(Grass grass) {
		raiseFoodLevel(5) ; 
		
	}
	
	public void eatHay(Hay hay) {
		raiseFoodLevel(20) ; 
		
	}
	
	public void eatFruit(Fruit fruit) {
		raiseFoodLevel(30) ; 
	}
	
	public void hungryStegosaur(Allosaur allosaur) {
		if(this.foodLevel < 50) {
			//behaviour = new HungryBehaviour() ; 
		}
	}
	
	public void updateStegosaurState() {
		this.age ++ ; 
		
		if(this.foodLevel >0) {
			this.foodLevel -- ;
		}
	
		if(this.foodLevel == 0) {
			this.behaviour = null ; 
			this.Starving();
		}else {
			this.starvationLevel = 0 ; 
		}
	}
	
	
	

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new AttackAction(this));
	}

	/**
	 * Figure out what to do next.
	 * 
	 * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
	 * just stands there.  That's boring.
	 * 
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		updateStegosaurState() ; //update stegosaur state
		if(this.starvationLevel == 20) {
			map.removeActor(this); // stegosaur die
		}
		
		Action wander = behaviour.getAction(this, map);
		if (wander != null)
			return wander;
		
		return new DoNothingAction();
	}

}
