package it.unibo.object_onepiece.controller;
import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Ship;
import it.unibo.object_onepiece.model.Utils.Position;

/**The interface that models the statesof the input of the controller.
 */
public abstract class InputState {
    /**
     * @param pos the input accepted by the controller
     * 
     * @return whenether it was possible to perform the action or not
     */
    protected abstract Boolean perform(Position pos,Ship ship);
    /**
     * @return The type of state that is implemented
     */
    protected abstract States getState();
}
