package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public class MoveState implements InputState{
    private Player player;

    public MoveState(Player player){
        this.player = player;
    }
    @Override
    public Boolean perform(Position pos) {
       var moveState = player.move(move(pos,player.getPosition()), player.getPosition().distanceFrom(pos));
       if(!moveState.canStep()){/* notify the GUI */}
       return moveState.canStep();
    }

    @Override
    public States getState() {
        return States.MOVING;
    }
    /* Duplicated method in Compass */
    private Direction move(Position objectivePosition,Position currentPosition) {
        var direction = currentPosition.vectorialDirection(objectivePosition);
        return Position.vectorToDirectionMap.get(direction);
    }
    
}
