package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Direction;

public interface Movable {
    public enum MoveDetails {
        RIGID_COLLISION,
        BORDER_REACHED,
        ROTATED_FIRST,
        MOVED_BUT_COLLIDED,
        MOVED_SUCCESSFULLY,
    };

    public record MoveReturnType(boolean hasMoved, MoveDetails details) {};

    public MoveReturnType move(Direction direction);
}
