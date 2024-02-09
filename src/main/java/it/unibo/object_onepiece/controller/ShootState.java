package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.controller.ControllerImpl.*;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.controller.Controller.*;
/**
 * The implementation of "Shoot" state of the controller.
 */
public final class ShootState extends InputState {
   
    /**
     * The contructor of ShootState.
     */
    public ShootState() {}
       
    

    @Override
    public Boolean perform(final Position pos,final Player player) {
      /*  var details = ship.getWeapon().shoot(pos);
       return details.hasShooted(); */
       return true;
    }

    @Override
    public States getState() {
        return States.SHOOTING;
    }

}
