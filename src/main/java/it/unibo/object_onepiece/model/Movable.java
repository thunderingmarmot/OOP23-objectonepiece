package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;

/**
 * Models a special Entity which can move and therefore change its position.
 * @see Entity
 */
public interface Movable extends Entity {

    /**
     * Defines the possible values of MoveDetails which explains what happened while moving.
     */
    enum MoveDetails {
        /**
         * Did not move, Collidable with HARD Rigidness in the way.
         * @see Collidable
         */
        STATIC_COLLISION,
        /**
         * Did not move, reached the border of the map.
         */
        BORDER_REACHED,
        /**
         * Rotated (direction changed), position unchanged.
         */
        ROTATED,
        /**
         * Moved, but collided with a Collidable with MEDIUM or SOFT Rigidness.
         * @see Collidable
         */
        MOVED_BUT_COLLIDED,
        /**
         * Moved without any problems.
         */
        MOVED_SUCCESSFULLY,
        /**
         * Destination too far for the Ship's current range.
         */
        OUT_OF_SPEED_RANGE,
        /**
         * Sail is broken, moving is impossible.
         */
        SAIL_BROKEN
    };

    /**
     * Defines the return type of the move method.
     * @param canStep a boolean which tells the caller of move if this Movable moved or not
     * @param details a MoveDetails object which explains in detail what happened
     */
    record MoveReturnType(boolean canStep, MoveDetails details) { }; 

    /**
     * Moves this Movable object.
     * @param direction a Direction which specifies the direction of the movement
     * @param steps an int that specifies how many times to move in that direction
     * @return a MoveDetails object that explains in detail what happened
     */
    MoveDetails move(CardinalDirection direction, int steps);

    /**
     * A dry move method, it just verififies if this Movable can move.
     * @param direction a Direction which specifies where to check if a movement is possible
     * @return a MoveReturnType object which tells the caller if moving is possible and the reason
     */
    MoveReturnType canMove(CardinalDirection direction);
}
