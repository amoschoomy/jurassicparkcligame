package game;

import edu.monash.fit2099.engine.*;

public class ThirstBehaviour extends Action implements Behaviour {

    private boolean isWaterAround(Actor actor, GameMap map){
        Location currentPosition = map.locationOf(actor);
        int closeWater = 0;
        for (Exit exit : currentPosition.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround() instanceof Water) {
                    closeWater++;
                }
            }

        return closeWater > 0;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        getAction(actor, map);
        return actor
                + " at ("
                + map.locationOf(actor).x()
                + ","
                + map.locationOf(actor).y()
                + ") is getting thirsty!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location currentPosition = map.locationOf(actor);
        for (Exit exit : currentPosition.getExits()) {
            Location destination = exit.getDestination();
            if (destination.getGround() instanceof Water && actor instanceof Allosaur){
                ((Allosaur) actor).drinkWater();
                System.out.println(actor.toString()+" drinks water from pool");
                return this;
            }
            if (destination.getGround() instanceof Water && actor instanceof Stegosaur){
                ((Stegosaur) actor).drinkWater();
                System.out.println(actor.toString()+" drinks water from pool");
                return this;
            }

            //Placeholder for other two dinasour
//            if (destination.getGround() instanceof Water && actor instanceof Allosaur){
//                ((Allosaur) actor).drinkWater();
//                System.out.println(actor.toString()+" drinks water from pool");
//                return this;
//            }
//            if (destination.getGround() instanceof Water && actor instanceof Allosaur){
//                ((Allosaur) actor).drinkWater();
//                System.out.println(actor.toString()+" drinks water from pool");
//                return this;}
            else{
                WanderBehaviour wander = new WanderBehaviour();
                return wander.getAction(actor, map);
            }
        }

    return new WanderBehaviour().getAction(actor, map);}}
