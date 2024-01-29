package it.unibo.object_onepiece.model.Enemy;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Enemy.Enemy.States;
import it.unibo.object_onepiece.model.Movable.MoveDetails;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

class Patrol implements EnemyState{

    private final int triggerDistance = 3;
    private Boolean obstacleAvoiding = false;
    private final NavigationSystem compass;
    private final Enemy ship;
    private final States stato = States.PATROLLING;
    private Direction direction;

    public Patrol(Enemy ship,NavigationSystem compass){
        this.ship = ship;
        this.compass = compass;
    }

    @Override
    public void perform() {

        if(obstacleAvoiding == false){
            direction = compass.Move(ship.getPosition());
        }

        MoveDetails result = ship.move(compass.Move(ship.getPosition()),
            Position.directionPositions.get(direction).apply(ship.getPosition())).details();
       

        switch (result) {
            case MOVED_BUT_COLLIDED:
                obstacleAvoiding = true;

                break;
        
            default:
                break;
        }
        checkState();
    }

    @Override
    public States getState() {
       return stato;
    }

    private void checkState(){
        if(this.ship.getPosition().distanceFrom(playerPos()) <= triggerDistance){
            this.ship.changeState(States.FOLLOWING);
        }
    }

    private Position playerPos(){
        return this.ship.getSection().getPlayer().getPosition();
    }

    private Direction NextDirection();

}