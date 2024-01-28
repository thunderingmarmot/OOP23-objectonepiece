package it.unibo.object_onepiece.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.object_onepiece.model.Ship.ShipImpl;
import it.unibo.object_onepiece.model.Ship.Weapon;
import it.unibo.object_onepiece.model.Ship.Sail;
import it.unibo.object_onepiece.model.Ship.Bow;
import it.unibo.object_onepiece.model.Utils.*;

public class EnemyImpl extends ShipImpl implements Enemy {
    private final List<EnemyState> enemyStates;
    private EnemyState currentState;
    
    public EnemyImpl(Section section, Position position, Direction direction, Weapon weapon, Sail sail, Bow bow) {
        super(section, position, direction, weapon, sail, bow);
        
        enemyStates = new ArrayList<>(List.of(
            new Patrol(this, new Compass())
        ));

        currentState = findState(States.PATROLLING);
    }

    @Override
    public void goNext() {
      
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
