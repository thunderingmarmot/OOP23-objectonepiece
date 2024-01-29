package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Direction;

public interface Movable extends Entity {
    public enum MoveDetails {
        STATIC_COLLISION,
        BORDER_REACHED,
        ROTATED,
        ROTATED_AND_MOVED,
        MOVED_BUT_COLLIDED,
        MOVED_SUCCESSFULLY,
        OUT_OF_SPEED_RANGE,
        SAIL_BROKEN
    };

    public void move(Direction direction);

    public MoveDetails canMove(Direction direction);
}
