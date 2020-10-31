package game;

import edu.monash.fit2099.engine.*;

import java.util.HashMap;

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
    private HashMap<String,Integer> findWater(Actor actor,GameMap map){
        int x = map.getXRange().min();
        int y = map.getYRange().min();
        HashMap<String, Integer> coordinates = new HashMap<String, Integer>(2);
        for (int i = map.getXRange().max(); i > x; i--) {
            for (int j = map.getYRange().max(); j > y; j--) {
                if (map.at(i,j).getGround().getDisplayChar()=='~') {
                    coordinates.put("x", i);
                    coordinates.put("y", j);
                    return coordinates;
                }
            }

    } if (!coordinates.containsKey("x")) {
            coordinates.put("status", -1);
            return coordinates;
    }return  coordinates;}

    @Override
    public String execute(Actor actor, GameMap map) {
        getAction(actor, map);
        System.out.println(actor.toString()+" drinks water from pool");
        return actor.toString()+" drinks water from pool";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location currentPosition = map.locationOf(actor);
        System.out.println( actor
                + " at ("
                + map.locationOf(actor).x()
                + ","
                + map.locationOf(actor).y()
                + ") is getting thirsty!");
        if (currentPosition!=null){
            if (isWaterAround(actor, map) && actor instanceof Allosaur){
                ((Allosaur) actor).drinkWater();
                return this;
            }
            if (isWaterAround(actor, map) && actor instanceof Stegosaur){
                ((Stegosaur) actor).drinkWater();
                return this;
            }
            if (isWaterAround(actor, map) && actor instanceof Agilisaurus){
                ((Agilisaurus) actor).drinkWater();
                return this;
            }
            if (isWaterAround(actor, map) && actor instanceof Archaeopteryx){
                ((Archaeopteryx) actor).drinkWater();
                return this;}

            if (!findWater(actor, map).containsKey("status")&&actor.hasCapability(FlyAbility.FLY)){
                int x=findWater(actor, map).get("x");
                int y=findWater(actor, map).get("y");
                for (int i = x; i > map.getXRange().min(); i--)
                    for(int j =y; j > map.getYRange().min(); j--) {
                        if(map.at(i,j).getGround().getDisplayChar()=='~'){
                            return new MoveActorAction(map.at(i,j),"to get water");
                        }
                    }

            }


            if(!findWater(actor, map).containsKey("status")){
                int x=findWater(actor, map).get("x");
                int y=findWater(actor, map).get("y");
        int currentDistance = distance(map.locationOf(actor), map.at(x,y));
                for (Exit exit : map.locationOf(actor).getExits()) {
                    Location destination = exit.getDestination();
                    if (destination.canActorEnter(actor)) {
                        int newDistance = distance(destination, map.at(x,y));
                        if (newDistance < currentDistance) {
                            return new MoveActorAction(destination, exit.getName());
                        }
                    }
            }}
            else{
                WanderBehaviour wander = new WanderBehaviour();
                return wander.getAction(actor, map);
            }
        }

    return new WanderBehaviour().getAction(actor, map);}
    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}