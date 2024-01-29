package it.unibo.object_onepiece.model.Enemy;

import it.unibo.object_onepiece.model.Ship.Ship;

/**
 * The enemy interface models a ship controlled by the CPU
 */
public interface Enemy extends Ship {
    /**
     * Patrolling state is when the enemy wanders the map in hope to see the player
     * Following state is when the enemy sees the player and wants to close in to attack
     * Attacking is when the enemy is sufficently close to the player and performs the attack
     * ( shooting consists of 1 rotate to face the player at the sides 2 shoot)
     * when the shoot state is on, the two steps will be executed without further conditions
     */
    public enum States {
        PATROLLING,
        FOLLOWING,
        ATTACKING
    } 

    public void goNext();
    public States getCurrentState();
    public void changeState(States state);
}
