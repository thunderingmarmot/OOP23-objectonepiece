package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;

public class ShootState implements InputState{
    private Player player;

    public ShootState(Player player){
        this.player = player;
    }
    
    @Override
    public Boolean perform(Position pos) {
       var details = player.getWeapon().shoot(pos);
       return details.hasShooted();
    }

    @Override
    public States getState() {
        return States.SHOOTING;
    }
  
}
