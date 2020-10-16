package game;
import edu.monash.fit2099.engine.*;

public class BreedBehaviour extends Action implements Behaviour {
	
	private boolean isOppositeGenderAround(Actor actor,GameMap map) {
		Location currentPosition = map.locationOf(actor) ;
		int oppositeGender = 0 ; 
		for(Exit exit:currentPosition.getExits()) {
			Location destination = exit.getDestination() ; 
			Actor partner = destination.getActor() ;
			//check if they are same species and opposite gender
			if(sameSpeciesDifferentGender(actor,partner)) {
				if(actor.hasCapability(LifeStage.ADULT)&& partner.hasCapability(LifeStage.ADULT)) {
					oppositeGender ++ ;	
				}
			}
		}
		
		return oppositeGender>0 ; 
	}
	
	private boolean sameSpeciesDifferentGender(Actor actor , Actor partner) {
		boolean retVal = false ; 
		if((actor.hasCapability(Gender.MALE) && partner.hasCapability(Gender.FEMALE)) || (actor.hasCapability(Gender.FEMALE) && partner.hasCapability(Gender.MALE))) {
			if(actor instanceof Stegosaur && partner instanceof Stegosaur) {
				retVal = true ; 
			}else if(actor instanceof Allosaur && partner instanceof Allosaur) {
				retVal = true ; 
			}
		}
		return retVal ; 
		
	}
	
	//method to calculate the distance between two 
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + "at " + map.locationOf(actor)+ " is ready to Breed!" ;
	}
	
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location currentPosition = map.locationOf(actor) ;
		if(isOppositeGenderAround(actor,map)) {
			for(Exit exit:currentPosition.getExits()) {
				Location destination = exit.getDestination() ; 
				if(destination.containsAnActor()) {
				Actor partner = destination.getActor() ;
				if(sameSpeciesDifferentGender(actor,partner)) {
					if(actor.hasCapability(LifeStage.ADULT)&& partner.hasCapability(LifeStage.ADULT)) {
						//mating
						if(actor.hasCapability(Gender.FEMALE)) {
							//actor is the female among the couple
							actor.removeCapability(LifeStage.ADULT);
							actor.addCapability(LifeStage.PREGNANT);
							System.out.println(actor + "at " + map.locationOf(actor)+ " is breed successful and pregnant!") ; 
						}else {
							//partner is the female among the couple
							partner.removeCapability(LifeStage.ADULT);
							partner.addCapability(LifeStage.PREGNANT);
							System.out.println(partner + "at " + map.locationOf(partner)+ " is breed successful and pregnant!") ; 
						}
						
						return this ;
					}
				}
				}
			}
			
		}else {
			//no opposite gender around, check other opposite gender partner in the gamemap
			for(int y : map.getYRange()) {
				for(int x : map.getXRange()) {
					if(map.at(x, y).containsAnActor()) {
						Actor partner = map.getActorAt(map.at(x, y)) ;
						//Location partnerLocation = map.locationOf(partner) ;
						if(sameSpeciesDifferentGender(actor,partner)&&(actor.hasCapability(LifeStage.ADULT)&& partner.hasCapability(LifeStage.ADULT))) {						
							//follow the partner
							FollowBehaviour follow = new FollowBehaviour(partner) ; 
							return follow.getAction(actor, map) ;  
		

						}
					}		
					
				}
				
			}
			
			// if no other actor , just wander
			WanderBehaviour wander = new WanderBehaviour();
			return wander.getAction(actor, map);
				
			
		}
		
		
		return null ; 
		
	}
	
	@Override
	public String menuDescription(Actor actor) {
		return "";
	}

}
