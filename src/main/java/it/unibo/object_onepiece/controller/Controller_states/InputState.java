package it.unibo.object_onepiece.controller.Controller_states;
import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.ship.Ship;

/**The interface that models the statesof the input of the controller.
 */
public interface InputState {
    /**
     * @param pos the input accepted by the controller
     * 
     * @return whenether it was possible to perform the action or not
     */
    Boolean perform(Position pos,Ship ship);
    /**
     * @return The type of state that is implemented
     */
    States getState();
}
