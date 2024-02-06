package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Utils.Position;

public class MoveState implements InputState{
    private Player player;

    public MoveState(Player player){
        this.player = player;
    }
    @Override
    public Boolean perform(Position pos) {
        var moveS = player.canMove(Utils.posToDir(pos,player.getPosition()));
        if(moveS.canStep()){
            player.move(Utils.posToDir(pos,player.getPosition()), player.getPosition().distanceFrom(pos));
            return true;
        }
        return false;
    }

    @Override
    public States getState() {
        return States.MOVING;
    }
    
}
