package it.unibo.object_onepiece.controller.Controller_states;

import it.unibo.object_onepiece.controller.Controller;
import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Utils.Position;

public interface InputState {
    public Boolean perform(Position pos);
    public States getState();
}
