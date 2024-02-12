package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

import java.util.List;
import java.util.Map;

import it.unibo.object_onepiece.model.Enemy.EnemyState;

public class AttackState extends EnemyState {
    private Enemy ship;
    private Player player;
    private Integer triggerDistance = 3;
    private Integer distanceFromPlayer = 3;
   
    public AttackState(Enemy ship) {
        this.ship = ship;
        this.player = ship.getSection().getPlayer();
    }

    @Override
    public Boolean perform() {
        distanceFromPlayer = player.getPosition().distanceFrom(ship.getPosition());
        if(distanceFromPlayer > triggerDistance){
            ship.changeState(States.PATROLLING);
            return false;
        }
       if(isInRange(ship.getDirection())){
            if(ship.shoot(player.getPosition()).hasShooted()){
                return true;
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
 
}
