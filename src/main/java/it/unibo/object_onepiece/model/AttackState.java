package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Ship.MoveDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

import java.util.Map;
import java.util.function.Supplier;

import it.unibo.object_onepiece.model.Enemy.EnemyState;

public class AttackState extends EnemyState {
    private Enemy ship;
   
    public AttackState(Enemy ship) {
        this.ship = ship;
    }

    @Override
    public Boolean perform() {
       
    }


    @Override
    public States getState() {
        return States.ATTACKING;
    }

    private Position nextPos(final CardinalDirection direction) {
        return Utils.getCardinalDirectionsTranslationMap()
                .get(direction).apply(ship.getPosition());
    }
 
}
