package it.unibo.object_onepiece.model.enemy;

import it.unibo.object_onepiece.model.Viewable;
import it.unibo.object_onepiece.model.ship.Ship;

/**
 * The enemy interface models a ship controlled by the CPU
 */
public interface Enemy extends Viewable, Ship {
    /**
     * Patrolling state is when the enemy wanders the map in hope to see the player
     * Following state is when the enemy sees the player and wants to close in to attack
     * Attacking is when the enemy is sufficently close to the player and performs the attack
     * ( shooting consists of 1 rotate to face the player at the sides 2 shoot)
     * when the shoot state is on, the two steps will be executed without further conditions
     */
    public enum States {
        PATROLLING,
        AVOIDING,
        ATTACKING
    } 

    public void goNext();
    public States getCurrentState();
    public void changeState(States state);
}
