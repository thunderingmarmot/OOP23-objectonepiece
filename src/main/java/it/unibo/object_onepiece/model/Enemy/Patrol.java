package it.unibo.object_onepiece.model.Enemy;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Enemy.Enemy.States;
import it.unibo.object_onepiece.model.Movable.MoveDetails;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

class Patrol implements EnemyState{

    private final int triggerDistance = 3;
    private final NavigationSystem compass;
    private final Enemy ship;
    private final States stato = States.PATROLLING;

    public Patrol(Enemy ship,NavigationSystem compass){
        this.ship = ship;
        this.compass = compass;
    }

    @Override
    public Boolean perform() {
        Direction suggestedDir = compass.move(ship.getPosition());
        MoveDetails result = ship.canMove(suggestedDir);

        if(result != MoveDetails.ROTATED || result != MoveDetails.MOVED_SUCCESSFULLY || 
            result != MoveDetails.SAIL_BROKEN){
            ship.changeState(States.AVOIDING);
            return false;
        } 

        ship.move(suggestedDir);
        checkPlayer();
        return true;
    }

    @Override
    public States getState() {
       return stato;
    }

    private void checkPlayer(){
        if(this.ship.getPosition().distanceFrom(playerPos()) <= triggerDistance){
            this.ship.changeState(States.ATTACKING);
        }
    }

    private Position playerPos(){
        return this.ship.getSection().getPlayer().getPosition();
    }

    private Position nextPos(Direction direction){
        return Position.directionPositions
                .get(direction).apply(ship.getPosition());
    }

}