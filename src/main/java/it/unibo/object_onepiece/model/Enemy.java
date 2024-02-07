package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import javafx.geometry.Pos;

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
        AVOIDING,
        ATTACKING
    } 

    public void goNext();
    public States getCurrentState();
    public void changeState(States state);
    
    public static EnemyImpl getDefault(Section spawnSection, Position spawnPosition){
        EnemyImpl enemy = new EnemyImpl(spawnSection, spawnPosition, CardinalDirection.NORTH);
        enemy.setWeapon(Weapon.cannon(enemy));
        enemy.setBow(Bow.standard(enemy));
        enemy.setSail(Sail.schooner(enemy));
        return enemy;
    }
}
