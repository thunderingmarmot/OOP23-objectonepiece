package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.EnemyImpl.States;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
/**
 * ObstacleAvoidance implements the Avoidance state of Enemy.
 * It gets triggered once an obstacle is detected, it switches to 
 * patrolling state once the primary direction is available
 */
public final class ObstacleAvoidance extends EnemyState {
    private final EnemyImpl ship;
    private CardinalDirection avoidanceDirection;

    /**
     * The costructor of Obstacle avoidance.
     * @param ship
     */
    protected ObstacleAvoidance(final EnemyImpl ship) {
        this.ship = ship;
    }

    @Override
    protected Boolean perform() {
        if (avoidanceDirection == null) {
            final var tempDirection = Utils.randCardinalDirection();
            if (Ship.MOVE_SUCCESS_CONDITIONS.contains(ship.move(tempDirection, 1))) {
                avoidanceDirection = tempDirection;
                return true;
            }
            return false;
        } else {
            if (Ship.MOVE_SUCCESS_CONDITIONS.contains(ship.move(avoidanceDirection, 1))) {
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
