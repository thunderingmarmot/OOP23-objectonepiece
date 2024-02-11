package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Ship.MoveDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

import java.util.Map;
import java.util.function.Supplier;

import it.unibo.object_onepiece.model.Enemy.EnemyState;

public class AttackState extends EnemyState {
    private Boolean alligned = false;
    private Enemy ship;
    private CardinalDirection prevDirection;
    private Map<CardinalDirection,Supplier<MoveDetails>> alligner = Map.of(
        CardinalDirection.NORTH, () -> ship.move(CardinalDirection.EAST,1),
        CardinalDirection.EAST, () -> ship.move(CardinalDirection.NORTH,1),
        CardinalDirection.WEST, () -> ship.move(CardinalDirection.SOUTH,1),
        CardinalDirection.SOUTH, () -> ship.move(CardinalDirection.WEST,1)
    );
    public AttackState(Enemy ship) {
        this.ship = ship;
    }

    @Override
    public Boolean perform() {
        if(alligned == false){
            prevDirection = ship.getDirection();
            alligner.get(alligned).get();
            alligned = true;
            return true;
        } else {
            ship.shoot(nextPos(prevDirection));
            ship.changeState(States.PATROLLING);
            return true;
        }
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
