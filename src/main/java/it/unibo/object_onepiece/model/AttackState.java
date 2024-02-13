package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Utils.*;

import it.unibo.object_onepiece.model.Enemy.EnemyState;

public final class AttackState extends EnemyState {
    private Enemy ship;
    private Player player;
    private Integer triggerDistance = 3;
    private Integer distanceFromPlayer = 3;
    private Position objective = null;
    private NavigationSystem navigationSystem;
   
    protected AttackState(Enemy ship,NavigationSystem navigationSystem) {
        this.ship = ship;
        this.navigationSystem = navigationSystem;
    }

    @Override
    public Boolean perform() {
        player = ship.getSection().getPlayer();
        distanceFromPlayer = player.getPosition().distanceFrom(ship.getPosition());
        if(distanceFromPlayer > triggerDistance){
            ship.changeState(States.PATROLLING);
            return false;
        }
        if(ship.shoot(player.getPosition()).hasShooted()){
            return true;
        } else {
            if(objective == null || objectiveReached(this.ship.getPosition())){
                circularTargetPlayer();
            }

            var suggestedDir = navigationSystem.move(objective, this.ship.getPosition());

            if(!Enemy.ACTION_SUCCESS_CONDITIONS.contains(ship.move(suggestedDir,1))){
                ship.changeState(States.AVOIDING);
                return false;
            } 
        }
       return true;
    }

    @Override
    public States getState() {
        return States.ATTACKING;
    }

    /**
     * This algorithm randomises a point around the
     * player for the enemy to reach
     * 
     */
    private void circularTargetPlayer(){
        var rand = Utils.getRandom();
        var bound = this.ship.getSection().getBounds();
        var playerPos = player.getPosition();
        var tsection = this.ship.getSection();
        int radius = 2;

        do {
            int x = - radius + rand.nextInt((radius * 2) + 1);
            int y = Double.valueOf(Math.sqrt(Math.pow(radius,2) - Math.pow(x,2))).intValue();//the pitagorean formula for one side of the triangle

            objective = playerPos.sum( new Position(x, y * randSign()) );
        } while ( !bound.isInside(objective) ||  tsection.getEntityAt(objective).isPresent());
        
        System.err.println("random objective:" + objective.toString());
    }

    private int randSign(){
        return Utils.getRandom().nextBoolean()? 1 : -1;
    }

    private Boolean objectiveReached(final Position currentPosition) {
        return currentPosition.equals(objective);
    }
}
