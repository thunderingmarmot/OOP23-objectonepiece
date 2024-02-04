package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;

public class MoveState implements InputState{
    private Player player;

    public MoveState(Player player){
        this.player = player;
    }
    @Override
    public Boolean perform(Position pos) {
        return false;
        //need a method to check player movement using pos
    }

    @Override
    public States getState() {
        return States.MOVING;
    }
    
}
