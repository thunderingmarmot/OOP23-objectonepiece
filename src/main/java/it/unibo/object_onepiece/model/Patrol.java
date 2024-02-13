package it.unibo.object_onepiece.model;

import java.util.Random;

import it.unibo.object_onepiece.model.Enemy.*;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Position;

public class Patrol extends EnemyState{
    
    private final NavigationSystem compass;
    private final Enemy ship;
    private Bound bound;

    private final int triggerDistance = 3;
    private final States stato = States.PATROLLING;
    private Position objective = null;
    private Random rand = Utils.getRandom();
    

    public Patrol(Enemy ship, NavigationSystem compass){
        this.ship = ship;
        this.compass = compass;
        this.bound = ship.getSection().getBounds();
    }

    @Override
    public Boolean perform() {
        if(objective == null || objectiveReached(ship.getPosition())){
            defineRandomObjective(ship.getPosition());
        } 
        
        var suggestedDir = compass.move(objective, this.ship.getPosition());

        if(!Ship.MOVE_SUCCESS_CONDITIONS.contains(ship.move(suggestedDir,1))){
            ship.changeState(States.AVOIDING);
            return false;
        } 

        checkPlayer();
        return true;
    }

    @Override
    public States getState() {
       return stato;
    }

    private boolean checkPlayer(){
        if(this.ship.getPosition().distanceFrom(playerPos()) <= triggerDistance){
            this.ship.changeState(States.ATTACKING);
            return true;
        }
        return false;
    }

    private Position playerPos(){
        return this.ship.getSection().getPlayer().getPosition();
    }

    private void defineRandomObjective(final Position currentPosition){
        do {
            int x = rand.nextInt(bound.columns());
            int y = rand.nextInt(bound.rows());

            objective = new Position(x, y);
        } while ( !bound.isInside(objective) );
        
        System.err.println("random objective:" + objective.toString());
    }

    private Boolean objectiveReached(final Position currentPosition) {
        return currentPosition.equals(objective)? true:false;
    }

}