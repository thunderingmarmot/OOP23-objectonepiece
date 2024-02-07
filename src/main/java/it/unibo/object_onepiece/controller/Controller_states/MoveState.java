package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.ship.Ship;

/**
 * The implementation of the Moving state of the controller.
 * The input is interpreted as a direction to move the player to.
 */
public final class MoveState implements InputState {
    private Ship player;
    /**
     * The constructor of MoveState.
     * @param player , the ship that rappresents the player
     */
    public MoveState(final Ship player) {
        this.player = player;
    }
    @Override
    public Boolean perform(final Position pos) {
        var moveS = player.canMove(player.getPosition().whereTo(pos));
        if (moveS.canStep()) {
            player.move(player.getPosition().whereTo(pos), player.getPosition().distanceFrom(pos));
            return true;
        }
        return false;
    }

    @Override
    public States getState() {
        return States.MOVING;
    }

}
