package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.controller.Controller.*;
import it.unibo.object_onepiece.controller.ControllerImpl.*;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The implementation of the Moving state of the controller.
 * The input is interpreted as a direction to move the player to.
 */
public final class MoveState extends InputState {
    /**
     * The constructor of MoveState.
     */
    public MoveState() {}

    @Override
    public Boolean perform(final Position pos, final Player player) {
        return player.moveTo(pos);
    }

    @Override
    public States getState() {
        return States.MOVING;
    }

}
