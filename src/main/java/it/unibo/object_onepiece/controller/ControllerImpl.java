package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.Utils.Position;

import java.util.Map;

import it.unibo.object_onepiece.model.Enemy;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.World;

/**
 * The implementation of the Controller Interface.
 */
public final class ControllerImpl implements Controller {

    private Boolean toggle = false;
    private InputState currentState;
    private Map<States, InputState> states = Map.of(
        States.SHOOTING, new ShootState(),
        States.MOVING, new MoveState());

    @Override
    public void action(final Position position, final World world) {
        var player = world.getPlayer();

        toggleMode(position, new Position(0, 0));

        if (currentState.perform(position,player)) {
            System.err.println("SI è mosso in teoria");
           /*  world.getEnemies()
                .forEach(e -> ((Enemy) e).goNext()); */
        }   
    }

    private void toggleMode(final Position position, final Position player) {
        if (player.equals(position)) {
            toggle = !toggle;
        }

        if (toggle) {
            currentState = states.get(States.SHOOTING);
        } else { 
            currentState = states.get(States.MOVING);
        }
    }

    protected static abstract class InputState {
        /**
         * @param pos the input accepted by the controller
         * 
         * @return whenether it was possible to perform the action or not
         */
        protected abstract Boolean perform(Position pos,Player player);
        /**
         * @return The type of state that is implemented
         */
        protected abstract States getState();
    }
}
