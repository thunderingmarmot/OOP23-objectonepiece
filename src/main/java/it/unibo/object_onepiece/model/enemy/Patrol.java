package it.unibo.object_onepiece.model.enemy;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Movable.MoveDetails;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.enemy.Enemy.States;

public class Patrol implements EnemyState{

    private final int triggerDistance = 3;
    private final NavigationSystem compass;
    private final EnemyImpl ship;
    private final States stato = States.PATROLLING;

    public Patrol(EnemyImpl ship,NavigationSystem compass){
        this.ship = ship;
        this.compass = compass;
    }

    @Override
    public Boolean perform() {
        CardinalDirection suggestedDir = compass.move(ship.getPosition());
        MoveDetails result = ship.canMove(suggestedDir).details();

        if(result != MoveDetails.ROTATED || result != MoveDetails.MOVED_SUCCESSFULLY || 
            result != MoveDetails.SAIL_BROKEN){
            ship.changeState(States.AVOIDING);
            return false;
        } 

        ship.move(suggestedDir,1);
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

    private Position nextPos(CardinalDirection direction){
        return Utils.getCardinalDirectionsTranslationMap()
                .get(direction).apply(ship.getPosition());
    }

}