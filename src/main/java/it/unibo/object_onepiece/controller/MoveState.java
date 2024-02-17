package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.controller.ControllerImpl.InputState;
import it.unibo.object_onepiece.model.PlayerImpl;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The implementation of the Moving state of the controller.
 * The input is interpreted as a direction to move the player to.
 */
public final class MoveState extends InputState {
    /**
     * The constructor of MoveState.
     */
    @Override
    public Boolean perform(final Position pos, final PlayerImpl player) {
        return player.moveTo(pos);
    }

    @Override
    public States getState() {
        return States.MOVING;
    }

}
