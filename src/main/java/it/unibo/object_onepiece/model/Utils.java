package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.BiPredicate;

/**
 * An Utility class that contains useful common enums, records and maps.
 */
public final class Utils {
    private Utils() { }

    /**
     * A record that keeps track of the main data to save.
     * @param section the reference to the current Section when the State was saved
     * @param playerPosition the Position where the Player was when the State was saved
     * @param playerExperience the experience value of the Player when the State was saved
     */
    public record State(Section section, Position playerPosition, int playerExperience) { }

    private static Map<CardinalDirection, Function<Position, Position>> cardinalDirectionsTranslations = Map.of(
        CardinalDirection.NORTH, (p) -> new Position(p.row + 1, p.column),
        CardinalDirection.SOUTH, (p) -> new Position(p.row - 1, p.column),
        CardinalDirection.EAST, (p) -> new Position(p.row, p.column + 1),
        CardinalDirection.WEST, (p) -> new Position(p.row, p.column - 1)
    );

    /**
     * Getter for the map that translates a Position towards a CardinalDirection.
     * @return the described map
     */
    public static Map<CardinalDirection, Function<Position, Position>> getCardinalDirectionsTranslationMap() {
        return cardinalDirectionsTranslations;
    }

    private static Map<OrdinalDirection, Function<Position, Position>> ordinalDirectionsTranslations = Map.of(
        OrdinalDirection.NORTHEAST, (p) -> new Position(p.row + 1, p.column + 1),
        OrdinalDirection.SOUTHWEST, (p) -> new Position(p.row - 1, p.column - 1),
        OrdinalDirection.NORTHWEST, (p) -> new Position(p.row + 1, p.column - 1),
        OrdinalDirection.SOUTHEAST, (p) -> new Position(p.row - 1, p.column + 1)
    );

    /**
     * Getter for the map that translates a Position towards an OrdinalDirection.
     * @return the described map
     */
    public static Map<OrdinalDirection, Function<Position, Position>> getOrdinalDirectionsTranslationMap() {
        return ordinalDirectionsTranslations;
    }

    private static Map<CardinalDirection, BiPredicate<Position, Position>> positionsInlineConditions = Map.of(
        CardinalDirection.NORTH, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
        CardinalDirection.SOUTH, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
        CardinalDirection.WEST, (p1, p2) -> p1.column == p2.column && p1.row != p2.row,
        CardinalDirection.EAST, (p1, p2) -> p1.column == p2.column && p1.row != p2.row
    );

    /**
     * Getter for the map that associates to a CardinalDirection a check to see if those two positions are inline.
     * @return the described map
     */
    public static Map<CardinalDirection, BiPredicate<Position, Position>> getPositionsInlineConditionsMap() {
        return positionsInlineConditions;
    }

    private static Map<Position, CardinalDirection> distanceVectorToCardinalDirections = Map.of(
        new Position(-1, -1), CardinalDirection.SOUTH,
        new Position(1, -1), CardinalDirection.SOUTH,
        new Position(-1, 1), CardinalDirection.NORTH,
        new Position(1, 1), CardinalDirection.NORTH,
        new Position(0, 1), CardinalDirection.NORTH,
        new Position(-1, 0), CardinalDirection.EAST,
        new Position(0, -1), CardinalDirection.SOUTH,
        new Position(1, 0), CardinalDirection.WEST
    );

    /**
     * Getter for the map that associates to a distance vector the corresponding CardinalDirection.
     * @return the described map
     */
    public static Map<Position, CardinalDirection> getDistanceVectorToCardinalDirectionMap() {
        return distanceVectorToCardinalDirections;
    }

    private static List<BiPredicate<Bound, Position>> insideBoundsConditions = List.of(
        (b, p) -> p.row < b.rows,
        (b, p) -> p.row > 0,
        (b, p) -> p.column < b.columns,
        (b, p) -> p.column > 0
    );

    /**
     * Getter for the list of conditions that checks wether a Position is inside a Bound.
     * @return the described list
     */
    public static List<BiPredicate<Bound, Position>> getInsideBoundsConditionsList() {
        return insideBoundsConditions;
    }

    /**
     * A record to keep track of an Entity's Position.
     * @param row the row where the Entity is
     * @param column the column where the Entity is
     */
    public record Position(int row, int column) {
        /**
         * Moves this Position towards a CardinalDirection.
         * @param direction the CardinalDirection to move towards
         * @return the moved Position
         */
        public Position moveTowards(final CardinalDirection direction) {
            return cardinalDirectionsTranslations.get(direction).apply(this);
        }

        /**
         * Calculates the distance from this Position to the one gave as argument.
         * @param position the Position to calculate the distance from
         * @return the distance as an Integer
         */
        public Integer distanceFrom(final Position position) {
            return Double.valueOf(
                Math.sqrt(Math.pow(this.row - position.row, 2)
                + Math.pow(this.column - position.column, 2)))
                .intValue();
        }

        /**
         * Calculates the distance vector from this Position to the one gave as argument.
         * @param position the Position to calculate the distance vector from
         * @return the distance vector as a Position
         */
        public Position distanceVectorFrom(final Position position) {
            var deltaRow = position.row - this.row;
            var deltaColumn = position.column - this.column;
            return new Position(deltaRow / Math.abs(deltaRow), deltaColumn / Math.abs(deltaColumn));
        }

        /**
         * Checks wether this Position and the one gave as argument are inline.
         * @param position the other Position
         * @param direction the Direction to determined the line
         * @return wether the two Positions are inline as a boolean
         */
        public boolean isInlineWith(final Position position, final CardinalDirection direction) {
            return positionsInlineConditions.get(direction).test(this, position);
        }

        /**
         * Sums this Position to the one gave as argument.
         * @param position the Position to sum to this one
         * @return the translated Position
         */
        public Position translate(final Position position) {
            return new Position(this.row + position.row, this.column + position.column);
        }

        /**
         * Calculates the CardinalDirection of the Position gave as argument in relation to this Position.
         * @param position the "destination" Position
         * @return the CardinalDirection where the destination is
         */
        public CardinalDirection whereTo(final Position position) {
            var distanceVector = this.distanceVectorFrom(position);
            return distanceVectorToCardinalDirections.get(distanceVector);
        }
    }

    /**
     * A record to keep track of the Section's Bounds, it is taken for granted that the origin is 0,0.
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public record Bound(int rows, int columns) {
        /**
         * Checks if a Position is inside the Bound.
         * @param position the Position to check
         * @return wether the Position is inside or not as a boolean
         */
        public boolean isInside(final Position position) {
            return insideBoundsConditions.stream().allMatch((condition) -> condition.test(this, position));
        }
    }

    /**
     * Defines the Cardinal directions, like a compass.
     */
    public enum CardinalDirection {
        /**
         * The North cardinal direction.
         */
        NORTH,
        /**
         * The East cardinal direction.
         */
        EAST,
        /**
         * The South cardinal direction.
         */
        SOUTH,
        /**
         * The West cardinal direction.
         */
        WEST,
    }

    /**
     * Defines the Ordinal directions, like a compass.
     */
    public enum OrdinalDirection {
        /**
         * The North-west ordinal direction.
         */
        NORTHWEST,
        /**
         * The North-east ordinal direction.
         */
        NORTHEAST,
        /**
         * The South-east ordinal direction.
         */
        SOUTHEAST,
        /**
         * The South-west ordinal direction.
         */
        SOUTHWEST,
    }
}
