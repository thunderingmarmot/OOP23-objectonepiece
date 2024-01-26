package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.BiPredicate;

public final class Utils {
    public static record State(Section section, Position playerPosition, int playerHealth, int playerExperience) {}

    public static record Position(int x, int y) {
        public static Map<Direction, Function<Position, Position>> directionPositions = Map.of(
            Direction.UP, (p) -> new Position(p.x + 1, p.y),
            Direction.DOWN, (p) -> new Position(p.x - 1, p.y),
            Direction.RIGHT, (p) -> new Position(p.x, p.y + 1),
            Direction.LEFT, (p) -> new Position(p.x, p.y - 1)
        );

        public static Map<Diagonal, Function<Position, Position>> diagonalPositions = Map.of(
            Diagonal.UPPERRIGHT, (p) -> new Position(p.x + 1, p.y + 1),
            Diagonal.LOWERLEFT, (p) -> new Position(p.x - 1, p.y - 1),
            Diagonal.UPPERLEFT, (p) -> new Position(p.x + 1, p.y - 1),
            Diagonal.LOWERRIGHT, (p) -> new Position(p.x - 1, p.y + 1)
        );

        public static Map<Direction, BiPredicate<Position, Position>> inlineConditions = Map.of(
            Direction.UP, (p1, p2) -> p1.x == p2.x && p1.y != p2.y,
            Direction.DOWN, (p1, p2) -> p1.x == p2.x && p1.y != p2.y,
            Direction.LEFT, (p1, p2) -> p1.y == p2.y && p1.x != p2.x,
            Direction.RIGHT, (p1, p2) -> p1.y == p2.y && p1.x != p2.x
        );

        public Position moveTowards(Direction direction) {
            return Position.directionPositions.get(direction).apply(this);
        }

        public Integer distanceFrom(final Position position){
            return Math.abs((this.x - position.x) + (this.y - position.y));
        }

        public boolean isInlineWith(Position position, Direction direction) {
            return Position.inlineConditions.get(direction).test(this, position);
        }

        public Position translate(final Position position){
            return new Position(this.x + position.x, this.y + position.y);
        }
    };

    public static record Bound(int upLimit, int leftLimit, int downLimit, int rightLimit) {

        public static List<BiPredicate<Bound, Position>> insideConditions = List.of(
            (b, p) -> p.x < b.upLimit,
            (b, p) -> p.x > b.downLimit,
            (b, p) -> p.y < b.rightLimit,
            (b, p) -> p.y > b.leftLimit
        );

        public boolean isInside(Position position) {
            return insideConditions.stream().allMatch((condition) -> condition.test(this, position));
        }
    }

    public static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
    };

    public static enum MoveReturnTypes {
        COLLIDABLE,
        BORDER,
        SUCCESS,
        ROTATION
    };
    
    public static enum Diagonal {
        UPPERLEFT,
        UPPERRIGHT,
        LOWERRIGHT,
        LOWERLEFT,
    };
}
