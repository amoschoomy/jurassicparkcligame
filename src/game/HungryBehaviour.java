package game;

import edu.monash.fit2099.engine.*;

public class HungryBehaviour extends Action implements Behaviour {
	private Actor target;

	public HungryBehaviour(Actor subject) {
		this.target = subject;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		return actor + "at " + map.locationOf(actor)+ " is getting hungry!" ;
	}
	
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(actor instanceof Stegosaur) {
			//if actor is a stegosaur 
			//for (int i = 0 ; i < Application.map.size() ; i++);
		}else {
			//if actor is an allosaur
		}
	return null ; 
	}

	@Override
	public String menuDescription(Actor actor) {
		return "";
	}

}
