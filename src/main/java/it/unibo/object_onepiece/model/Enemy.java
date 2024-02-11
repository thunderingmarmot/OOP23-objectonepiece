package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                         Sail.sloop(),
                         Bow.standard(),
                         Keel.standard());
    }
    private final List<EnemyState> enemyStates;
    private EnemyState currentState;
    
    protected Enemy(final Section section,
                    final Position position,
                    final CardinalDirection direction,
                    final Weapon weapon,
                    final Sail sail,
                    final Bow bow,
                    final Keel keel) {
        super(section, position, direction, weapon, sail, bow, keel);

        enemyStates = new ArrayList<>(List.of(
            new Patrol(this, new Compass()),
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
