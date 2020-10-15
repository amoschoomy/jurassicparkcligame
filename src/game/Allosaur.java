package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

public class Allosaur extends Actor {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
		private Behaviour behaviour;
		private int age = 0 ; 
		private int foodLevel ;  // baby foodLevel start range
		private int starvationLevel = 0 ; 
		private String lifeStage ; 
		private char gender ; 
		/** 
		 * Constructor.
		 * All Allosaurs are represented by a 'd' and have 100 hit points.
		 * 
		 * @param name the name of this Allosaur
		 */
		public Allosaur(String name, String lifeStage){
			super(name, 'a', 100);
			
			behaviour = new WanderBehaviour();
			
			this.addCapability(LiveStatus.LIVE);
			this.addCapability(FoodType.CARNIVORES); // Stegosaur is a carnivores
			
			if(lifeStage == "baby" || lifeStage == "adult") {
				this.lifeStage = lifeStage;
				if(lifeStage == "adult"){
					this.foodLevel = 50 ; 
					this.displayChar = 'A' ;
				}else {
					//baby allosaur 
					this.foodLevel = 10 ; 
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
		
		public void eatEgg(Egg egg) {
			raiseFoodLevel(10) ; 
		}
		
		public void eatCorpse(Corpse corpse) {
			raiseFoodLevel(50) ; 
		}
		
		
		public void updateAllosaurState() {
			this.age ++ ; 
			
			if(this.foodLevel >0) {
				this.foodLevel -- ;
			}
			
			if(this.foodLevel <30) {
				this.behaviour = new HungryBehaviour() ; 
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
			
			updateAllosaurState() ; //update allosaur state
			if(this.starvationLevel == 20 || this.hitPoints == 0) {
				this.removeCapability(LiveStatus.LIVE);
				this.addCapability(LiveStatus.DEAD);
				map.locationOf(this).addItem(new Corpse()); ; 
				map.removeActor(this);
			}
			
			Action wander = behaviour.getAction(this, map);
			if (wander != null)
				return wander;
			
			return new DoNothingAction();
		}
}
