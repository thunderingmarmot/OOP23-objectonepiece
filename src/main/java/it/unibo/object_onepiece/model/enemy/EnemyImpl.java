package it.unibo.object_onepiece.model.enemy;

import java.util.ArrayList;
import java.util.List;

import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.Utils.*;
import it.unibo.object_onepiece.model.enemy.enemy_state.AttackState;
import it.unibo.object_onepiece.model.enemy.enemy_state.EnemyState;
import it.unibo.object_onepiece.model.enemy.enemy_state.ObstacleAvoidance;
import it.unibo.object_onepiece.model.enemy.enemy_state.Patrol;
import it.unibo.object_onepiece.model.ship.Bow;
import it.unibo.object_onepiece.model.ship.Sail;
import it.unibo.object_onepiece.model.ship.ShipImpl;
import it.unibo.object_onepiece.model.ship.Weapon;

public class EnemyImpl extends ShipImpl implements Enemy {
    private final List<EnemyState> enemyStates;
    private EnemyState currentState;
    
    protected EnemyImpl(Section section, Position position, CardinalDirection direction, Weapon weapon, Sail sail, Bow bow) {
        super(section, position, direction, weapon, sail, bow);
        enemyStates = new ArrayList<>(List.of(
            new Patrol(this, new Compass(this.getPosition(),section.getBounds())),
            new ObstacleAvoidance(),
            new AttackState()
        ));
        currentState = findState(States.PATROLLING);
    }

    @Override
    public void goNext() {
        while (!currentState.perform());
    }

    @Override
    public States getCurrentState() {
        return currentState.getState();
    }

    @Override
    public void changeState(States state) {
       this.currentState = findState(state);
    }

    private EnemyState findState(States stato){
        return enemyStates.stream().filter(x -> x.getState().equals(States.PATROLLING)).findFirst().get();
    }
}
