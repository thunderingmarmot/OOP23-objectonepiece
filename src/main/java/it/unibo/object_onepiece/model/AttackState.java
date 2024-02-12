package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Utils.*;

import java.util.List;
import java.util.Map;

import it.unibo.object_onepiece.model.Enemy.EnemyState;

public class AttackState extends EnemyState {
    private Enemy ship;
    private Player player;
    private Integer triggerDistance = 3;
    private Integer distanceFromPlayer = 3;
    private Position objective = null;
   
    public AttackState(Enemy ship) {
        this.ship = ship;
    }

    @Override
    public Boolean perform() {
        player = ship.getSection().getPlayer();
        distanceFromPlayer = player.getPosition().distanceFrom(ship.getPosition());
        if(distanceFromPlayer > triggerDistance){
            ship.changeState(States.PATROLLING);
            return false;
        }
       if(isInRange(ship.getDirection())){
            if(ship.shoot(player.getPosition()).hasShooted()){
                return true;
            } else {
                if(objective == null || objectiveReached(this.ship.getPosition())){
                    circularTargetPlayer();
                }
                

            }

       }
       return true;
    }


    @Override
    public States getState() {
        return States.ATTACKING;
    }

    private Boolean isInRange(CardinalDirection shipDirection){
        if(distanceFromPlayer < ship.getWeapon().getRange()){
            for (var direction : sidesOf(shipDirection)) {
                if(ship.getPosition().isInlineWith(player.getPosition(), direction)){
                    return true;
                }
            }
        }
        return false; 
    }

    private List<CardinalDirection> sidesOf(CardinalDirection direction){
        var side1 = List.of(CardinalDirection.EAST,CardinalDirection.WEST);
        var side2 = List.of(CardinalDirection.NORTH,CardinalDirection.SOUTH);
        return Map.of(
            CardinalDirection.NORTH,side1,
            CardinalDirection.SOUTH,side1,
            CardinalDirection.EAST,side2,
            CardinalDirection.WEST,side2
        ).get(direction);
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
        return Map.of(0,1,1,-1).get(Utils.getRandom().nextInt(2));
    }
     private Boolean objectiveReached(final Position currentPosition) {
        return currentPosition.equals(objective)? true:false;
    }
}
