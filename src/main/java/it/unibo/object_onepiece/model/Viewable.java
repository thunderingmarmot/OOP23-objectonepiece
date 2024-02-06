package it.unibo.object_onepiece.model;

import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.*;
import it.unibo.object_onepiece.model.ship.Ship;

public interface Viewable extends Entity {
    public static enum Type {
        PLAYER,
        ENEMY,
        BARREL,
        ISLAND,
    }

    public Type getViewType();

    public default Optional<CardinalDirection> getViewDirection() {
        return this instanceof Ship ship ? Optional.of(ship.getDirection()) : Optional.empty();
    };

    public default Position getViewPosition() {
        return this.getPosition();
    };
}
