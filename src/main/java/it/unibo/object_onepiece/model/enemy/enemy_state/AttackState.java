package it.unibo.object_onepiece.model.enemy.enemy_state;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.enemy.Enemy;
import it.unibo.object_onepiece.model.enemy.Enemy.States;

public class AttackState implements EnemyState {
    Boolean alligned = false;
    Enemy ship;
    CardinalDirection prevDirection;

    @Override
    public Boolean perform() {
        if(alligned == false){
            prevDirection = ship.getDirection();

            switch (prevDirection) {
                case UP:
                    ship.move(CardinalDirection.RIGHT,1);
                    break;
                case LEFT:
                    ship.move(CardinalDirection.UP,1);
                    break;
                case RIGHT:
                    ship.move(CardinalDirection.DOWN,1);
                    break;
                case DOWN:
                    ship.move(CardinalDirection.LEFT,1);
                    break;
                default:
                    break;
            }
            return true;
        } else{
            ship.getWeapon().shoot(nextPos(prevDirection));
            ship.changeState(States.PATROLLING);
            return true;
        }
    }


    @Override
    public States getState() {
        return States.ATTACKING;
    }

    private Position nextPos(CardinalDirection direction){
        return Position.directionPositions
                .get(direction).apply(ship.getPosition());
    }
    
}
