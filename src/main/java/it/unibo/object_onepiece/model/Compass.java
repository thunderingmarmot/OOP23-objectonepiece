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
     * @param currentPosition is the position of the ship in which the compass is present
     * @param bound is the limit of the map, used to define random objective
     */
    public Compass() {}

    private Map<Position,Supplier<CardinalDirection>> dirMap = Map.of(
        new Position(0, 1), () -> CardinalDirection.EAST,
        new Position(-1, 0), () -> CardinalDirection.NORTH,
        new Position(0, -1), () -> CardinalDirection.WEST,
        new Position(1, 0), () -> CardinalDirection.SOUTH,

        new Position(1, 1), () -> Utils.getRandom().nextBoolean()? CardinalDirection.SOUTH : CardinalDirection.EAST,
        new Position(1, -1), () -> Utils.getRandom().nextBoolean()? CardinalDirection.SOUTH : CardinalDirection.WEST,
        new Position(-1, 1), () -> Utils.getRandom().nextBoolean()? CardinalDirection.NORTH : CardinalDirection.EAST,
        new Position(-1, -1), () -> Utils.getRandom().nextBoolean()? CardinalDirection.NORTH : CardinalDirection.WEST
    );

    @Override
    public CardinalDirection move(final Position objectivePosition, final Position currentPosition) { 
        var vers = currentPosition.versorOf(objectivePosition);
        var deb = dirMap.get(vers);
        if(deb == null){
            System.err.println(vers.toString());
        }
        return dirMap.get(currentPosition.versorOf(objectivePosition)).get();
    }

    

}