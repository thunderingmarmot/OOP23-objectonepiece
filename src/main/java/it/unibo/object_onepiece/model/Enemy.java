package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * This class rappresents The enemy entity.
 * Since it exends Ship it can shoot and move according to 
 * the algorithm
 */
public final class Enemy extends Ship {

    /**
     * The different states in which the Enemy acts.
     * ( State pattern )
     */
    public enum States {
        /**
         * The Enemy wanders the map, in search for the player.
         */
        PATROLLING,
        /**
         * The Enemy has collided with an obstacle and is finding an alterantive route.
         */
        AVOIDING,
        /**
         * 
         */
        ATTACKING
    } 

    static final List<MoveDetails> ACTION_SUCCESS_CONDITIONS = List.of(
        MoveDetails.MOVED_SUCCESSFULLY,
        MoveDetails.MOVED_BUT_COLLIDED,
        MoveDetails.ROTATED,
        MoveDetails.MEDIUM_COLLISION
    );

    private static final int DEFAULT_TRIGGER_DISTANCE = 5;
    private final int triggerDistance;
    private EnemyState currentState;
    private final List<EnemyState> enemyStates;
    /**
     * Returns a Default version of Enemy.
     * @param spawnSection the section in which enemy gets spawned
     * @param spawnPosition where the Enemy gets spawned
     * @return
    */
    protected Enemy(final Section spawnSection, final Position spawnPosition) {
        this(spawnSection,
            spawnPosition,
            CardinalDirection.NORTH,
            Weapon.cannon(),
            Sail.sloop(),
            Bow.standard(),
            Keel.standard(),
            DEFAULT_TRIGGER_DISTANCE);
    }

    private Enemy(final Section section,
                final Position position,
                final CardinalDirection direction,
                final Weapon weapon,
                final Sail sail,
                final Bow bow,
                final Keel keel,
                final int triggerDistance) {
        super(section, position, direction, weapon, sail, bow, keel);
        this.triggerDistance = triggerDistance;

        enemyStates = new ArrayList<>(List.of(
            new Patrol(this, new Compass()),
            new ObstacleAvoidance(this),
            new AttackState(this, new Compass())
        ));

        currentState = findState(States.PATROLLING);
    }

    protected Enemy(final Enemy origin) {
        super(origin);
        this.triggerDistance = origin.triggerDistance;
        this.enemyStates = origin.enemyStates;
        this.currentState = origin.currentState;
    }

    @Override
    protected Enemy copy() {
        return new Enemy(this);
    }

    /**
     * 
     * @return the distance at which the Enemy change from Patrol to Attack.
    */
    protected int getTriggerDistance() {
        return this.triggerDistance;
    }

    /**
     * @return the section in which is present.
     */
    @Override
    protected Section getSection() {
        return super.getSection();
    }

    /**
     * Needs to be called when is the Enemy turn.
     */
    public void goNext() {
        Boolean result;
        do {
            result = !currentState.perform();
        } while (result);
    }

    /**
     * 
     * @return returns the current state
     */
    protected States getCurrentState() {
        return currentState.getState();
    }

    /**
     * Is called by the Enemy or enemy states to change the current state.
     * @param state
     */
    protected void changeState(final States state) {
         this.currentState = findState(state);
    }

    private EnemyState findState(final States stato) {
        return enemyStates.stream().filter(x -> x.getState().equals(stato)).findFirst().get();
    }
}

