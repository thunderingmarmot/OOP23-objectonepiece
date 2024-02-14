package it.unibo.object_onepiece.model;

import java.util.function.Supplier;

import java.util.Map;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Compass is the implementation of NavigationSystem.
 */
public final class Compass implements NavigationSystem {
    /**
     * The contructor of Compass.
     */
    private static final Map<Position, Supplier<CardinalDirection>> DIR_MAP = Map.of(
        new Position(0, 1), () -> CardinalDirection.EAST,
        new Position(-1, 0), () -> CardinalDirection.NORTH,
        new Position(0, -1), () -> CardinalDirection.WEST,
        new Position(1, 0), () -> CardinalDirection.SOUTH,

        new Position(1, 1), () -> Utils.getRandom().nextBoolean() ? CardinalDirection.SOUTH : CardinalDirection.EAST,
        new Position(1, -1), () -> Utils.getRandom().nextBoolean() ? CardinalDirection.SOUTH : CardinalDirection.WEST,
        new Position(-1, 1), () -> Utils.getRandom().nextBoolean() ? CardinalDirection.NORTH : CardinalDirection.EAST,
        new Position(-1, -1), () -> Utils.getRandom().nextBoolean() ? CardinalDirection.NORTH : CardinalDirection.WEST
    );

    @Override
    public CardinalDirection move(final Position objectivePosition, final Position currentPosition) { 
        return DIR_MAP.get(currentPosition.versorOf(objectivePosition)).get();
    }
}
