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
		private Player owner ; 
		private int pregnantPeriodCount = 0 ; 
		/** 
		 * Constructor.
		 * All Allosaurs are represented by a 'd' and have 100 hit points.
		 * 
		 * @param name the name of this Allosaur
		 */
		public Allosaur(String name, String lifeStage , Player owner){
			super(name, 'a', 100);
			
			this.owner = owner ; 
			
			behaviour = new WanderBehaviour();
			
			this.addCapability(LiveStatus.LIVE);
			this.addCapability(FoodType.CARNIVORES); // Stegosaur is a carnivores
			
			if(lifeStage == "adult"){
				this.age = 30 ; 
				this.addCapability(LifeStage.ADULT);
				this.foodLevel = 50 ; 
				this.displayChar = 'A' ;
			}else if(lifeStage == "baby"){	
				//baby stegosaur 
				this.addCapability(LifeStage.BABY);
				this.foodLevel = 10;
			}else {
				throw new IllegalArgumentException("life stage only can be adult or baby");
			}
			
			if(Math.random() >0.5) {
				this.addCapability(Gender.MALE) ;// set gender as male ; 
			}else {
				this.addCapability(Gender.FEMALE) ; // set gender as female
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
		
		public boolean readyBreed() {
			boolean retVal = false ; 
			if(this.foodLevel > 60) {
				retVal = true ; 
			}
			
			return retVal ; 
		}
		
		public void updateAllosaurState() {
			this.age ++ ; 
			
			if(this.age == 30 && this.hasCapability(LifeStage.BABY)) {
				//grow up
				this.removeCapability(LifeStage.BABY);
				this.addCapability(LifeStage.ADULT);
				this.displayChar = 'D';
			}
			
			if(this.foodLevel >0) {
				this.foodLevel -- ;
			}
			
			if(this.foodLevel <30) {
				this.behaviour = new HungryBehaviour() ; 
			}
			
			if(this.foodLevel > 60) {
				this.behaviour = new BreedBehaviour() ; 
			}
		
			if(this.foodLevel == 0) {
				this.behaviour = null ; 
				this.Starving();
			}else {
				this.starvationLevel = 0 ; 
			}
		}
		
		private boolean isPregnant() {
			return this.hasCapability(LifeStage.PREGNANT) ; 
		}
		
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
		 * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
		 * just stands there.  That's boring.
		 * 
		 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
		 */
		@Override
		public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
			
			updateAllosaurState() ; //update allosaur state
			
			if(isPregnant()) {
				pregnantPeriodCount ++ ; 
				if(pregnantPeriodCount == 10) {
					//prenant after 10 turns 
					this.removeCapability(LifeStage.PREGNANT);
					this.removeCapability(LifeStage.ADULT);
					Egg egg = new Egg("Allosaur",true , owner) ; 
					map.locationOf(this).addItem(egg);;
					pregnantPeriodCount = 0 ;
				}
			}
			
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
