package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.BiPredicate;

public final class Utils {
    private Utils() { }

    public record State(Section section, Position playerPosition, int playerExperience) { }

    public static Map<CardinalDirection, Function<Position, Position>> cardinalDirectionsTranslation = Map.of(
        CardinalDirection.UP, (p) -> new Position(p.row + 1, p.column),
        CardinalDirection.DOWN, (p) -> new Position(p.row - 1, p.column),
        CardinalDirection.RIGHT, (p) -> new Position(p.row, p.column + 1),
        CardinalDirection.LEFT, (p) -> new Position(p.row, p.column - 1)
    );

    public static Map<OrdinalDirection, Function<Position, Position>> ordinalDirectionsTranslation = Map.of(
        OrdinalDirection.UPPERRIGHT, (p) -> new Position(p.row + 1, p.column + 1),
        OrdinalDirection.LOWERLEFT, (p) -> new Position(p.row - 1, p.column - 1),
        OrdinalDirection.UPPERLEFT, (p) -> new Position(p.row + 1, p.column - 1),
        OrdinalDirection.LOWERRIGHT, (p) -> new Position(p.row - 1, p.column + 1)
    );

    public static Map<CardinalDirection, BiPredicate<Position, Position>> positionsInlineConditions = Map.of(
        CardinalDirection.UP, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
        CardinalDirection.DOWN, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
        CardinalDirection.LEFT, (p1, p2) -> p1.column == p2.column && p1.row != p2.row,
        CardinalDirection.RIGHT, (p1, p2) -> p1.column == p2.column && p1.row != p2.row
    );

    public static Map<Position, CardinalDirection> vectorToCardinalDirectionMap = Map.of(
        new Position(-1, -1), CardinalDirection.DOWN,
        new Position(1, -1), CardinalDirection.DOWN,
        new Position(-1, 1), CardinalDirection.UP,
        new Position(1, 1), CardinalDirection.UP,
        new Position(0, 1), CardinalDirection.UP,
        new Position(-1, 0),   CardinalDirection.LEFT,
        new Position(0, -1),   CardinalDirection.DOWN,
        new Position(1, 0),  CardinalDirection.RIGHT
    );

    public static List<BiPredicate<Bound, Position>> insideBoundsConditions = List.of(
        (b, p) -> p.row < b.upLimit,
        (b, p) -> p.row > b.downLimit,
        (b, p) -> p.column < b.rightLimit,
        (b, p) -> p.column > b.leftLimit
    );

    public record Position(int row, int column) {
        public Position moveTowards(final CardinalDirection direction) {
            return positionDirectionTranslate.get(direction).apply(this);
        }

        public Integer distanceFrom(final Position position) {
            return Double.valueOf(
                Math.sqrt(Math.pow(this.row - position.row, 2)
                + Math.pow(this.column - position.column, 2)))
                .intValue();
        }

        public boolean isInlineWith(final Position position, final CardinalDirection direction) {
            return positionInlineConditions.get(direction).test(this, position);
        }

        public Position translate(final Position position) {
            return new Position(this.row + position.row, this.column + position.column);
        }

        public Position vectorialDirection(final Position position) {
            var deltaRow = position.row - this.row;
            var deltaColumn = position.column - this.column;
            return new Position(deltaRow / Math.abs(deltaRow), deltaColumn / Math.abs(deltaColumn));
        }
    }

    public record Bound(int upLimit, int leftLimit, int downLimit, int rightLimit) {
        public boolean isInside(final Position position) {
            return insideConditions.stream().allMatch((condition) -> condition.test(this, position));
        }
    }

    public enum CardinalDirection {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    public enum OrdinalDirection {
        NORTHWEST,
        NORTHEAST,
        SOUTHEAST,
        SOUTHWEST,
    }

    public static CardinalDirection posToDir(final Position objectivePosition, final Position currentPosition) {
        var direction = currentPosition.vectorialDirection(objectivePosition);
        return Position.vectorToDirectionMap.get(direction);
    }
}
