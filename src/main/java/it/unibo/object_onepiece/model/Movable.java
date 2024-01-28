package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Direction;

public interface Movable {
    public enum MoveReturnType {
        COLLIDABLE,
        CRASHABLE,
        BORDER,
        MOVED,
        ROTATED
    };

    public MoveReturnType move(Direction direction);
}
