package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Direction;

public interface Movable extends Entity {
    public enum MoveDetails {
        STATIC_COLLISION,
        BORDER_REACHED,
        ROTATED,
        MOVED_BUT_COLLIDED,
        MOVED_SUCCESSFULLY,
        OUT_OF_SPEED_RANGE,
        SAIL_BROKEN
    };

    public record MoveReturnType(boolean canStep, MoveDetails details){}; 

    public MoveDetails move(Direction direction, int steps);

    public MoveReturnType canMove(Direction direction);
}
