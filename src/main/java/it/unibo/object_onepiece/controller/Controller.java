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

    /**
     * The actual input of the controller.
     * @param position The position of the pressed cell( chess-like GUI )
     * @param world The model on which the Controller acts.
     */
    void action(Position position, World world);
    /**
     * The initialization of the world as the generation of Entities and spawn of player.
     * ( is intended that the act of generating sections is maneged by the controller )
     * @param world
     */
    void initialize(World world);
}
