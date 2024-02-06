package it.unibo.object_onepiece.model.enemy;

import java.util.Random;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

class Compass implements NavigationSystem {
    
    private final Random rand = new Random(); 
    private Position objective;
    private final Bound bound;


    public Compass(Position currentPosition,Bound bound){
        this.bound = bound;
        defineRandomObjective(currentPosition);
        
    }

    @Override
    public CardinalDirection move(Position objectivePosition,Position currentPosition) {
        return Utils.posToDir(objectivePosition,currentPosition);
    }

    public CardinalDirection move(Position currentPosition){
        if(objectiveReached(objective)){
            defineRandomObjective(currentPosition);
        } 
        return this.move(objective, currentPosition);
    }

    private void defineRandomObjective(Position currentPosition){
        final int maxDistance = 5;

        do{
            int x = rand.nextInt(maxDistance);
            int y = rand.nextInt(maxDistance);
    
            objective = currentPosition.translate(new Position(x, y));
        }while(!bound.isInside(objective));
       
    }

    private Boolean objectiveReached(Position position){
        if(position.equals(objective)){
            return true;
        }else{ return false; } 
    }  

}