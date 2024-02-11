package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Compass is the implementation of NavigationSystem.
 */
public final class Compass implements NavigationSystem {
    /**
     * The contructor of Compass.
     * @param currentPosition is the position of the ship in which the compass is present
     * @param bound is the limit of the map, used to define random objective
     */
    public Compass() {}

    @Override
    public CardinalDirection move(final Position objectivePosition, final Position currentPosition) {
        return currentPosition.whereTo(objectivePosition);
    }

    

}