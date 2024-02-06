package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.enemy.Enemy;

import java.util.Map;

import it.unibo.object_onepiece.controller.Controller_states.InputState;
import it.unibo.object_onepiece.controller.Controller_states.ShootState;
import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.World;

public class ControllerImpl implements Controller{
    Position pPosition;
    Section currentSec;
    Player player;
    InputState currentState;
    Boolean toggle = false;
    Map<States,InputState> states = Map.of(States.SHOOTING,new ShootState(player));

    @Override
    public void action(Position position, World world) {
        pPosition = world.getCurrentSection().getPlayer().getPosition();
        currentSec = world.getCurrentSection();
        player = currentSec.getPlayer();
        
        toggleMode(position, pPosition);

        if(currentState.perform(position)){
            currentSec.getEntities()
                .stream()
                .filter(x -> x instanceof Enemy)
                .toList()
                .forEach(e  ->((Enemy)e).goNext());
        }
    }

    @Override
    public void initialize(World world) {
       world.instantiateSection();
    }

    private void toggleMode(Position position,Position player){
        if(player.equals(position)){
            toggle = !toggle;
        }
        if(toggle){currentState = states.get(States.SHOOTING);} else { states.get(States.MOVING);}
    }
    
}
