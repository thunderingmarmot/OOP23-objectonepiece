package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.Utils.Position;

import java.util.Map;

import it.unibo.object_onepiece.controller.Controller_states.InputState;
import it.unibo.object_onepiece.controller.Controller_states.MoveState;
import it.unibo.object_onepiece.controller.Controller_states.ShootState;
import it.unibo.object_onepiece.model.Enemy;
import it.unibo.object_onepiece.model.PlayerImpl;
import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.World;

/**
 * The implementation of the Controller Interface.
 */
public final class ControllerImpl implements Controller {
    private Position pPosition;
    private Section currentSec;
    private PlayerImpl player;
    private InputState currentState;
    private Boolean toggle = false;

    private Map<States, InputState> states = Map.of(
        States.SHOOTING, new ShootState(),
        States.MOVING, new MoveState());

    @Override
    public void action(final Position position, final World world) {
        pPosition = world.getCurrentSection().getPlayer().getPosition();
        currentSec = world.getCurrentSection();
        player = currentSec.getPlayer();

        toggleMode(position, pPosition);

        if (currentState.perform(position,player)) {
            currentSec.getEntities()
                .stream()
                .filter(x -> x instanceof Enemy)
                .toList()
                .forEach(e -> ((Enemy) e).goNext());
        }
    }

    @Override
    public void initialize(final World world) {
       world.instantiateSection();
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

}
