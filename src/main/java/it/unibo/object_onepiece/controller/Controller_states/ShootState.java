package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The implementation of "Shoot" state of the controller.
 */
public final class ShootState implements InputState {
    private Player player;
    /**
     * The contructor of ShootState.
     * @param player is required to perform the actual "shooting"
     */
    public ShootState(final Player player) {
        this.player = player;
    }

    @Override
    public Boolean perform(final Position pos) {
       var details = player.getWeapon().shoot(pos);
       return details.hasShooted();
    }

    @Override
    public States getState() {
        return States.SHOOTING;
    }

}
