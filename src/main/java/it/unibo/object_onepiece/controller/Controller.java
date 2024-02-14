package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.World;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The controller takes the input given by the GUI, moves the player( if allowed )
 * and the AI in shifts.
 */
public interface Controller {
    /**
     * The states in witch the controller understands the given input.
     */
    enum States {
        /**
         * The input interpred as shoot.
         */
        SHOOTING,
        /**
         * The input interpreted as move.
         */
        MOVING
    }

    enum Buttons {
        /**
         * The experience is used to fix the ship
         */
        FIX
    }

    /**
     * The actual input of the controller.
     * @param position The position of the pressed cell( chess-like GUI )
     * @param world The model on which the Controller acts.
     */
    void action(Position position, World world, States state);
    /**
     * The function through which the implemented buttons gets called.
     * @param button the type of the button called
     * @param world the model of the game 
     */
    void pressGameButton(Buttons button, World world);
}
