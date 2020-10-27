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
//        Location currentPosition = map.locationOf(actor);
//        for (Exit exit : currentPosition.getExits()) {
//            Location destination = exit.getDestination();
//            if (destination.getGround() instanceof Water){
//
//            }
////            if (destination.canActorEnter(actor)) {
////                // Hunt for fruit
////                for (Item item : destination.getItems()) {
////                    if (item instanceof Fruit) {
////                        Fruit fruit = (Fruit) item;
////                        map.moveActor(actor, destination);
////                        Stegosaur stegosaur = (Stegosaur) actor;
////                        stegosaur.eatFruit(fruit);
////                        System.out.println(stegosaur + " eats Fruit");
////                        destination.removeItem(item);
////                        return this;
////                    }
////                }
////    }

    return null;}}
