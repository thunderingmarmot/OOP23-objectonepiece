package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.controller.ControllerImpl.InputState;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;
/**
 * The implementation of "Shoot" state of the controller.
 */
public final class ShootState extends InputState {
    /**
     * The contructor of ShootState.
     */
    @Override
    public Boolean perform(final Position pos, final Player player) {
        return player.shootAt(pos);   
    }

    @Override
    public States getState() {
        return States.SHOOTING;
    }
}
