package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
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
    public void perform() {
        ship.move(compass.Move(ship.getPosition()));
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

}