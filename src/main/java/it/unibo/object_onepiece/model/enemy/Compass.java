package it.unibo.object_onepiece.model.enemy;

import java.util.Random;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

class Compass implements NavigationSystem {
    
    final Random rand = new Random(); 
    Position objective;
    
    public Compass(){
        defineRandomObjective();
    }

    @Override
    public Direction move(Position objectivePosition,Position currentPosition) {
        return Utils.posToDir(objectivePosition,currentPosition);
    }

    public Direction move(Position currentPosition){
        if(objectiveReached(objective)){
            defineRandomObjective();
        } 
        return this.move(objective, currentPosition);
    }

    private void defineRandomObjective(){
        final int maxDistance = 5;
        final int minDistance = 2;

        int x = minDistance + rand.nextInt(maxDistance - minDistance);
        int y = minDistance + rand.nextInt(maxDistance - minDistance);

        objective = objective.translate(new Position(x, y));
    }

    private Boolean objectiveReached(Position position){
        if(position.equals(objective)){
            return true;
        }else{ return false; } 
    }  

}