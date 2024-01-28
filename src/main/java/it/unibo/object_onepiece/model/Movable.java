package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public interface Movable {
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

    public record MoveReturnType(boolean hasMoved, MoveDetails details) {};

    public MoveReturnType move(Direction direction, Position nextPos);
}
