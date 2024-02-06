package it.unibo.object_onepiece.model.enemy.enemy_state;

import java.util.function.Supplier;

import it.unibo.object_onepiece.model.Movable.MoveDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.enemy.Enemy;
import it.unibo.object_onepiece.model.enemy.Enemy.States;

public class ObstacleAvoidance  implements EnemyState {
    private Enemy ship;
    private int attempt = 0;
    private CardinalDirection primaryDirection;
    private CardinalDirection avoidanceDirection;
    @Override
    public Boolean perform() {

       if(primaryDirection == null){//this happens when there's the transition from another state to this state
            primaryDirection = ship.getDirection();
       }//we save the direction given by the compass

        if(avoidanceDirection == null){
            switch (primaryDirection) {
                case UP:
                    chooseDirection(() -> up());
                    break;
                case LEFT:
                    chooseDirection(() -> left());
                    break;
                case RIGHT:
                    chooseDirection(() -> right());
                    break;
                case DOWN:
                    chooseDirection(() -> down());
                    break;
    
                default:
                    break;
            }
            ship.move(primaryDirection,1);
            return true;

        } else {
            if(canMove(ship.canMove(primaryDirection).details())){
                ship.move(primaryDirection,1);
                primaryDirection = null;
                ship.changeState(States.PATROLLING);
                return true;
            }
            if(canMove(ship.canMove(avoidanceDirection).details())){
                ship.move(primaryDirection,1);
                return true;
            }
            return false;
       }
    }

    @Override
    public States getState() {
       return States.AVOIDING;
    }

    private CardinalDirection up(){
        switch (attempt) {
            case 0:
                attempt++;
                return CardinalDirection.UP;
            case 1:
                attempt++;
                return CardinalDirection.LEFT;
            case 2:
                attempt++;
                return CardinalDirection.RIGHT;
            case 3:
                attempt++;
                return CardinalDirection.DOWN;
    
            default:
                throw new IllegalStateException();
        }
        
    }
    private CardinalDirection left(){
        switch (attempt) {
            case 0:
                attempt++;
                return CardinalDirection.LEFT;
            case 1:
                attempt++;
                return CardinalDirection.UP;
            case 2:
                attempt++;
                return CardinalDirection.DOWN;
            case 3:
                attempt++;
                return CardinalDirection.DOWN;
    
            default:
                throw new IllegalStateException();
        }
    }
    private CardinalDirection right(){
        switch (attempt) {
            case 0:
            attempt++;
                return CardinalDirection.RIGHT;
            case 1:
            attempt++;
                return CardinalDirection.DOWN;
            case 2:
            attempt++;
                return CardinalDirection.UP;
            case 3:
            attempt++;
                return CardinalDirection.LEFT;
    
            default:
                throw new IllegalStateException();
        }
    }
    private CardinalDirection down(){
        switch (attempt) {
            case 0:
            attempt++;
                return CardinalDirection.DOWN;
            case 1:
            attempt++;
                return CardinalDirection.LEFT;
            case 2:
            attempt++;
                return CardinalDirection.RIGHT;
            case 3:
            attempt++;
                return CardinalDirection.UP;
    
            default:
                throw new IllegalStateException();
        }
    }

    private void chooseDirection(Supplier<CardinalDirection> supplier){
        while (attempt < 5) {
            avoidanceDirection = supplier.get();
            MoveDetails result = ship.canMove(avoidanceDirection).details();
            if(canMove(result)){
                attempt = 0;
                break;
            }
        }
    }

    private Boolean canMove(MoveDetails result){
        return (result == MoveDetails.MOVED_BUT_COLLIDED || result == MoveDetails.MOVED_SUCCESSFULLY ||
        result == MoveDetails.ROTATED || result == MoveDetails.SAIL_BROKEN);
        
    }

}

