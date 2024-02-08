package it.unibo.object_onepiece.model;

import java.util.function.Supplier;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Ship.MoveDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;

/**
 * ObstacleAvoidance implements the Avoidance state of Enemy.
 * It gets triggered once an obstacle is detected, it switches to 
 * patrolling state once the primary direction is available
 */
public final class ObstacleAvoidance extends EnemyState {
    private Enemy ship;
    private int attempt = 0;
    private CardinalDirection primaryDirection;
    private CardinalDirection avoidanceDirection;
    private final int direction = 5; 

    /**
     * 
     * @param ship the ship on witch the ObstacleAvoidance is linked
     */
    public ObstacleAvoidance(final Enemy ship) {
        this.ship = ship;
    }

    /**
     * Perform of ObstacleAvoidance generete an alternative direction in order
     * to avoid an obstacle.
     * A priority level is used to choose the direction to take,
     * if we want to go up and there is an obstacle up, the first priority is to go up, 
     * the second ( and usually available ) priority is left or right, after a direction is choosed
     * is kept until the primary priority is satisfied or another obstacle is encountered
     */
    @Override
    public Boolean perform() {
       if (primaryDirection == null) { //this happens when there's the transition from another state to this state
            primaryDirection = ship.getDirection();
       } //we save the direction given by the compass

        if (avoidanceDirection == null) {
            switch (primaryDirection) {
                case NORTH:
                    chooseDirection(() -> up());
                    break;
                case WEST:
                    chooseDirection(() -> left());
                    break;
                case EAST:
                    chooseDirection(() -> right());
                    break;
                case SOUTH:
                    chooseDirection(() -> down());
                    break;

                default:
                    break;
            }
            ship.move(primaryDirection, 1);
            return true;

        } else {
            if (canMove(ship.checkMove(primaryDirection).details())) {
                ship.move(primaryDirection, 1);
                primaryDirection = null;
                ship.changeState(States.PATROLLING);
                return true;
            }
            if (canMove(ship.checkMove(avoidanceDirection).details())) {
                ship.move(primaryDirection, 1);
                return true;
            }
            return false;
       }
    }

    @Override
    public States getState() {
       return States.AVOIDING;
    }

    private CardinalDirection up() {
        switch (attempt) {
            case 0:
                attempt++;
                return CardinalDirection.NORTH;
            case 1:
                attempt++;
                return CardinalDirection.WEST;
            case 2:
                attempt++;
                return CardinalDirection.EAST;
            case 3:
                attempt++;
                return CardinalDirection.SOUTH;

            default:
                throw new IllegalStateException();
        }

    }
    private CardinalDirection left() {
        switch (attempt) {
            case 0:
                attempt++;
                return CardinalDirection.WEST;
            case 1:
                attempt++;
                return CardinalDirection.NORTH;
            case 2:
                attempt++;
                return CardinalDirection.SOUTH;
            case 3:
                attempt++;
                return CardinalDirection.SOUTH;

            default:
                throw new IllegalStateException();
        }
    }
    private CardinalDirection right() {
        switch (attempt) {
            case 0:
            attempt++;
                return CardinalDirection.EAST;
            case 1:
            attempt++;
                return CardinalDirection.SOUTH;
            case 2:
            attempt++;
                return CardinalDirection.NORTH;
            case 3:
            attempt++;
                return CardinalDirection.WEST;

            default:
                throw new IllegalStateException();
        }
    }
    private CardinalDirection down() {
        switch (attempt) {
            case 0:
            attempt++;
                return CardinalDirection.SOUTH;
            case 1:
            attempt++;
                return CardinalDirection.WEST;
            case 2:
            attempt++;
                return CardinalDirection.EAST;
            case 3:
            attempt++;
                return CardinalDirection.NORTH;

            default:
                throw new IllegalStateException();
        }
    }

    private void chooseDirection(final Supplier<CardinalDirection> supplier) {
        while (attempt < direction) {
            avoidanceDirection = supplier.get();
            MoveDetails result = ship.checkMove(avoidanceDirection).details();
            if (canMove(result)) {
                attempt = 0;
                break;
            }
        }
    }

    private Boolean canMove(final MoveDetails result) {
        return (result == MoveDetails.MOVED_BUT_COLLIDED || result == MoveDetails.MOVED_SUCCESSFULLY 
        || result == MoveDetails.ROTATED || result == MoveDetails.SAIL_BROKEN); 
    }

}

