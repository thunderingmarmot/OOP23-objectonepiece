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
   
    public static Enemy getDefault(Section spawnSection, Position spawnPosition){
        return new Enemy(spawnSection,
                         spawnPosition,
                         CardinalDirection.NORTH,
                         Weapon.cannon(),
                         Sail.schooner(),
                         Bow.standard());
    }
    private final List<EnemyState> enemyStates;
    private EnemyState currentState;
    
    protected Enemy(final Section section,
                    final Position position,
                    final CardinalDirection direction,
                    final Weapon weapon,
                    final Sail sail,
                    final Bow bow) {
        super(section, position, direction, weapon, sail, bow);
        enemyStates = new ArrayList<>(List.of(
            new Patrol(this, new Compass(this.getPosition(),section.getBounds())),
            new ObstacleAvoidance(this),
            new AttackState(this)
        ));
        currentState = findState(States.PATROLLING);
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
       this.currentState = findState(state);
    }

    private EnemyState findState(States stato){
        return enemyStates.stream().filter(x -> x.getState().equals(States.PATROLLING)).findFirst().get();
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
