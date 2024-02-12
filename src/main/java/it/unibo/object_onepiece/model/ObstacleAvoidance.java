package it.unibo.object_onepiece.model;

import java.util.List;

import it.unibo.object_onepiece.model.Enemy.*;
import it.unibo.object_onepiece.model.Ship.MoveDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
/**
 * ObstacleAvoidance implements the Avoidance state of Enemy.
 * It gets triggered once an obstacle is detected, it switches to 
 * patrolling state once the primary direction is available
 */
public final class ObstacleAvoidance extends EnemyState {
    private final Enemy ship;
    private CardinalDirection avoidanceDirection = null;


    public ObstacleAvoidance(Enemy ship){
        this.ship = ship;
    }

   @Override
    protected Boolean perform() {
        if(avoidanceDirection == null){
            var tempDirection = Utils.randCardinalDirection();
            if(Ship.MOVE_SUCCESS_CONDITIONS.contains(ship.move(tempDirection,1))){
                avoidanceDirection = tempDirection;
                return true;
            }
            return false;
        } else {
            if(Ship.MOVE_SUCCESS_CONDITIONS.contains(ship.move(avoidanceDirection,1))){
                avoidanceDirection = null;
                ship.changeState(States.PATROLLING);
                return true;
            }
            avoidanceDirection = null;
            return false;
        }    
    }

    @Override
    protected States getState() {
        return States.AVOIDING;
    }  
}

