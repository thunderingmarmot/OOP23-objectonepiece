package it.unibo.object_onepiece.model.Enemy.EnemyState;

import it.unibo.object_onepiece.model.Enemy.Enemy;
import it.unibo.object_onepiece.model.Enemy.Enemy.States;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class AttackState implements EnemyState {
    Boolean alligned = false;
    Enemy ship;
    Direction prevDirection;

    @Override
    public Boolean perform() {
        if(alligned == false){
            prevDirection = ship.getDirection();

            switch (prevDirection) {
                case UP:
                    ship.move(Direction.RIGHT,1);
                    break;
                case LEFT:
                    ship.move(Direction.UP,1);
                    break;
                case RIGHT:
                    ship.move(Direction.DOWN,1);
                    break;
                case DOWN:
                    ship.move(Direction.LEFT,1);
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

    private Position nextPos(Direction direction){
        return Position.directionPositions
                .get(direction).apply(ship.getPosition());
    }
    
}
