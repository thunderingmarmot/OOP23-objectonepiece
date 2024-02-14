package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.object_onepiece.model.Utils.*;

public final class Enemy extends Ship {

    public enum States {
        PATROLLING,
        AVOIDING,
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
   
    public static Enemy getDefault(Section spawnSection, Position spawnPosition){
        return new Enemy(spawnSection,
                         spawnPosition,
                         CardinalDirection.NORTH,
                         Weapon.cannon(),
                         Sail.sloop(),
                         Bow.standard(),
                         Keel.standard(),
                         DEFAULT_TRIGGER_DISTANCE);
    }
    private final List<EnemyState> enemyStates;
    private EnemyState currentState;
    
    protected Enemy(final Section section,
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

    protected int getTriggerDistance() {
        return this.triggerDistance;
    }

    protected Section getSection() {
        return super.getSection();
    }

    public void goNext() {
        while (!currentState.perform());
    }

    protected States getCurrentState() {
        return currentState.getState();
    }
    
    protected void changeState(States state) {
        System.err.println("stato cambiato, " + state.toString());
         this.currentState = findState(state);
    }

    private EnemyState findState(States stato){
        return enemyStates.stream().filter(x -> x.getState().equals(stato)).findFirst().get();
    }

    protected static abstract class EnemyState {
        /**
         * @return true if the action was executed
         * false if not ( is given that the state has been changed and another attempt will be performed )
         */
        protected abstract Boolean perform();
    
        protected abstract States getState();
    }
}
