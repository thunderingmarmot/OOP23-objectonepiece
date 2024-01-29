package it.unibo.object_onepiece.model.Enemy;

import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import it.unibo.object_onepiece.model.Enemy.Enemy.States;
import it.unibo.object_onepiece.model.Utils.Direction;

class ObstacleAvoidance implements EnemyState{
    private Enemy ship;
    private int attempt = 0;
    private Direction primaryDirection;

    @Override
    public void perform() {
       if(primaryDirection == null){
            primaryDirection = 
       }
    }

    @Override
    public States getState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    private Direction up(){
        switch (attempt) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.LEFT;
            case 2:
                return Direction.RIGHT;
            case 3:
                return Direction.DOWN;
    
            default:
                throw new IllegalStateException();
        }
    }
    private Direction left(){
        switch (attempt) {
            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.UP;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.DOWN;
    
            default:
                throw new IllegalStateException();
        }
    }
    private Direction right(){
        switch (attempt) {
            case 0:
                return Direction.RIGHT;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.LEFT;
    
            default:
                throw new IllegalStateException();
        }
    }
    private Direction down(){
        switch (attempt) {
            case 0:
                return Direction.DOWN;
            case 1:
                return Direction.LEFT;
            case 2:
                return Direction.RIGHT;
            case 3:
                return Direction.UP;
    
            default:
                throw new IllegalStateException();
        }
    }

}