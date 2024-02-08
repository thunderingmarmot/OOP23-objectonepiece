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
        Enemy enemy = new Enemy(spawnSection, spawnPosition, CardinalDirection.NORTH);
        enemy.setWeapon(Weapon.cannon(enemy));
        enemy.setBow(Bow.standard(enemy));
        enemy.setSail(Sail.schooner(enemy));
        return enemy;
    }
    private final List<EnemyState> enemyStates;
    private EnemyState currentState;
    
    protected Enemy(Section section, Position position, CardinalDirection direction) {
        super(section, position, direction);
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

   
    public States getCurrentState() {
        return currentState.getState();
    }

    
    public void changeState(States state) {
       this.currentState = findState(state);
    }

    private EnemyState findState(States stato){
        return enemyStates.stream().filter(x -> x.getState().equals(States.PATROLLING)).findFirst().get();
    }
}
