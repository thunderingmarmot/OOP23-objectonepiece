package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller;
import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.ship.Weapon.ShootDetails;

public class ShootState implements InputState{
    private Player player;

    public ShootState(Player player){
        this.player = player;
    }
    
    @Override
    public Boolean perform(Position pos) {
       var details = player.getWeapon().shoot(pos);
       if(details.hasShooted() == false){ /* we need to send the appropriate message to the GUI */}
       return details.hasShooted();
    }

    @Override
    public States getState() {
        return States.SHOOTING;
    }
  
}
