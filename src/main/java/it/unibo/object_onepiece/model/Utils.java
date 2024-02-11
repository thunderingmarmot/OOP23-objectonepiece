package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import java.util.function.BiPredicate;

/**
 * An Utility class that contains useful common enums, records and maps.
 */
public final class Utils {
    private static Map<CardinalDirection, Function<Position, Position>> cardinalDirectionsTranslations = Map.of(
        CardinalDirection.NORTH, (p) -> new Position(p.row - 1, p.column),
        CardinalDirection.SOUTH, (p) -> new Position(p.row + 1, p.column),
        CardinalDirection.EAST, (p) -> new Position(p.row, p.column + 1),
        CardinalDirection.WEST, (p) -> new Position(p.row, p.column - 1)
    );

    private static Map<OrdinalDirection, Function<Position, Position>> ordinalDirectionsTranslations = Map.of(
        OrdinalDirection.NORTHEAST, (p) -> new Position(p.row - 1, p.column + 1),
        OrdinalDirection.SOUTHWEST, (p) -> new Position(p.row + 1, p.column - 1),
        OrdinalDirection.NORTHWEST, (p) -> new Position(p.row - 1, p.column - 1),
        OrdinalDirection.SOUTHEAST, (p) -> new Position(p.row + 1, p.column + 1)
    );

    private static Map<CardinalDirection, BiPredicate<Position, Position>> positionsInlineConditions = Map.of(
        CardinalDirection.NORTH, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
        CardinalDirection.SOUTH, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
        CardinalDirection.WEST, (p1, p2) -> p1.column == p2.column && p1.row != p2.row,
        CardinalDirection.EAST, (p1, p2) -> p1.column == p2.column && p1.row != p2.row
    );

    private static Map<Position, CardinalDirection> versorToCardinalDirections = Map.of(
        new Position(0, 1), CardinalDirection.EAST,
        new Position(-1, 0), CardinalDirection.NORTH,
        new Position(0, -1), CardinalDirection.WEST,
        new Position(1, 0), CardinalDirection.SOUTH,

        new Position(1, 1), CardinalDirection.SOUTH,
        new Position(1, -1), CardinalDirection.SOUTH,
        new Position(-1, 1), CardinalDirection.NORTH,
        new Position(-1, -1), CardinalDirection.NORTH
        
    );

    private static List<BiPredicate<Bound, Position>> insideBoundsConditions = List.of(
        (b, p) -> p.row < b.rows - 1,
        (b, p) -> p.row > 0,
        (b, p) -> p.column < b.columns - 1,
        (b, p) -> p.column > 0
    );

    private Utils() { }

    /**
     * A class that keeps track of the main data to save.
     */
    protected static class State {
        private final Section section;
        private final Position playerPosition;
        private final int playerExperience;

        /**
         * Creates a State of the game with the main information to save.
         * @param section the Section the Player was in when the save occurred
         * @param playerPosition the Position of the Player when the save occurred
         * @param playerExperience the experience of the Player when the save occurred
         */
        protected State(final Section section, final Position playerPosition, final int playerExperience) {
            this.section = section;
            this.playerPosition = playerPosition;
            this.playerExperience = playerExperience;
        }

        /**
         * Getter for the saved Section.
         * @return the Section the Player was in when the save occurred
         */
        protected Section getSection() {
            return this.section;
        }

        /**
         * Getter for the Player position.
         * @return the Position of the Player when the save occurred
         */
        protected Position getPlayerPosition() {
            return this.playerPosition;
        }

        /**
         * Getter for the Player experience.
         * @return the experience of the Player when the save occurred
         */
        protected int getPlayerExperience() {
            return this.playerExperience;
        }
    }

    /**
     * Getter for the map that translates a Position towards a CardinalDirection.
     * @return the described map
     */
    public static Map<CardinalDirection, Function<Position, Position>> getCardinalDirectionsTranslationMap() {
        return cardinalDirectionsTranslations;
    }

    /**
     * Getter for the map that translates a Position towards an OrdinalDirection.
     * @return the described map
     */
    public static Map<OrdinalDirection, Function<Position, Position>> getOrdinalDirectionsTranslationMap() {
        return ordinalDirectionsTranslations;
    }

    /**
     * Getter for the map that associates to a CardinalDirection a check to see if those two positions are inline.
     * @return the described map
     */
    public static Map<CardinalDirection, BiPredicate<Position, Position>> getPositionsInlineConditionsMap() {
        return positionsInlineConditions;
    }

    /**
     * Getter for the map that associates to a distance vector the corresponding CardinalDirection.
     * @return the described map
     */
    public static Map<Position, CardinalDirection> getVersorToCardinalDirectionMap() {
        return versorToCardinalDirections;
    }

    /**
     * Getter for the list of conditions that checks wether a Position is inside a Bound.
     * @return the described list
     */
    public static List<BiPredicate<Bound, Position>> getInsideBoundsConditionsList() {
        return insideBoundsConditions;
    }

    /**
     * This method calculate if two entities have opposite direction.
     * For example: 
     * if the first entity direction is NORTH or SOUTH,
     * the second entity to be in opposite direction should have EAST ore WEST.
     * 
     * @param  e1 the first entity to control
     * @param  e2 the second entity to control
     * @return    the result of the control.
     * @see       Entity
     */
    public static boolean isEntityInOppositeDirection(final Entity e1, final Entity e2) {
        final Set<CardinalDirection> verticalDirection = Set.of(CardinalDirection.NORTH, CardinalDirection.SOUTH);
        final Set<CardinalDirection> horizontalDirection = Set.of(CardinalDirection.EAST, CardinalDirection.WEST);

        return verticalDirection.contains(e1.getDirection()) && horizontalDirection.contains(e2.getDirection())
            || horizontalDirection.contains(e1.getDirection()) && verticalDirection.contains(e1.getDirection());
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
            return Objects.isNull(direction) ? this : cardinalDirectionsTranslations.get(direction).apply(this);
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
        public Position versorOf(final Position position) {
            final var deltaRow = position.row - this.row;
            final var deltaColumn = position.column - this.column;

            return new Position(deltaRow != 0 ? deltaRow / Math.abs(deltaRow) : 0,
                                deltaColumn != 0 ? deltaColumn / Math.abs(deltaColumn) : 0);
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
            final var versor = this.versorOf(position);
            return versorToCardinalDirections.get(versor);
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
