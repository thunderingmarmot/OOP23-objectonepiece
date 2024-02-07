package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.ship.ShipImpl;

/**
 * The implementation of "Shoot" state of the controller.
 */
public final class ShootState implements InputState {
   
    /**
     * The contructor of ShootState.
     */
    public ShootState() {}
       
    

    @Override
    public Boolean perform(final Position pos,final ShipImpl ship) {
       var details = ship.getWeapon().shoot(pos);
       return details.hasShooted();
    }

    @Override
    public States getState() {
        return States.SHOOTING;
    }

}
