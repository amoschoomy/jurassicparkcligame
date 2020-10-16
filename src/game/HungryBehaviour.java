package game;

import edu.monash.fit2099.engine.*;

public class HungryBehaviour extends Action implements Behaviour {
	
	public boolean isFruitAround(Actor actor, GameMap map) {
		Location currentPosition = map.locationOf(actor); 
		int closeFruit = 0 ; 
		for(Exit exit: currentPosition.getExits()) {
			Location destination = exit.getDestination() ; 
			if(destination.canActorEnter(actor)) {
				if(destination.getItems() instanceof Fruit) {
					closeFruit++ ; 
				} 
			}
					
		}
	
		return closeFruit>0 ;
	}
	
	public boolean isGrassAround(Actor actor, GameMap map) {
		Location currentPosition = map.locationOf(actor); 
		int closeGrass = 0 ; 
		for(Exit exit: currentPosition.getExits()) {
			Location destination = exit.getDestination() ; 
			if(destination.canActorEnter(actor)) {
				if(destination.getGround() instanceof Grass) {
					closeGrass++ ; 
				} 
			}
					
		}
	
		return closeGrass>0 ;
	}
	
	public boolean isDinosaurCorpseAround(Actor actor, GameMap map) {
		Location currentPosition = map.locationOf(actor); 
		int closeDino = 0 ; 
		for(Exit exit: currentPosition.getExits()) {
			Location destination = exit.getDestination() ; 
			if(destination.canActorEnter(actor)) {
				for(Item item: destination.getItems()) {
					if(item instanceof Corpse) {
						closeDino++; 
					}
				}
			}
					
		}
	
		return closeDino>0 ;
	}
	

	@Override
	public String execute(Actor actor, GameMap map) {
		getAction(actor,map);
		return actor + "at (" + map.locationOf(actor).x()+"," +map.locationOf(actor).y() + ") is getting hungry!" ;
	}
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location currentPosition = map.locationOf(actor);
		
		//For Stegosaur
		if((actor instanceof Stegosaur) && (isFruitAround(actor,map)|| isGrassAround(actor,map)) ) {
			for(Exit exit: currentPosition.getExits()) {
				Location destination = exit.getDestination() ; 
				if(destination.canActorEnter(actor)) {
					//Hunt for fruit
					for(Item item: destination.getItems()) {
						if(item instanceof Fruit) {
							Fruit fruit = (Fruit) item ; 
							map.moveActor(actor,destination);
							Stegosaur stegosaur = (Stegosaur)actor ; 
							stegosaur.eatFruit(fruit);
              				System.out.println(stegosaur+" eats Fruit");
							destination.removeItem(item);
							return this ; 
						}
					}
					//Hunt for grass
					if(destination.getGround() instanceof Grass) {
						Grass grass = (Grass) destination.getGround() ; 
						map.moveActor(actor,destination);
						Stegosaur stegosaur = (Stegosaur)actor ; 
						stegosaur.grazeGrass(grass);
            			System.out.println(stegosaur+ " grazed Grass");
						destination.setGround(new Dirt());
						return this; 
					}	
				}
			}
		
			
			// For Allosaur	
		}else if((actor instanceof Allosaur) && isDinosaurCorpseAround(actor,map)){
			for(Exit exit: currentPosition.getExits()) {
				Location destination = exit.getDestination() ; 
				if(destination.canActorEnter(actor)) {
					//Hunt for corpse
					for(Item item: destination.getItems()) {
						if(item instanceof Corpse) {
							Corpse corpse = (Corpse) item ; 
							map.moveActor(actor,destination);
							Allosaur allosaur = (Allosaur)actor ; 
							allosaur.eatCorpse(corpse);
              				System.out.println(allosaur + " ate corpse");
							destination.removeItem(item);
							return this ; 
						}
					}
					
				
				}
			}
			
		}else {
			// if no foods around , just wander
			WanderBehaviour wander = new WanderBehaviour();
			return wander.getAction(actor, map);
			
		}
		return null ;  // if all place occupied and can't go 
	 
	}

	@Override
	public String menuDescription(Actor actor) {
		return "";
	}

}
