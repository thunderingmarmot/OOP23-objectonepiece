package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.BiPredicate;

public final class Utils {
    public static record State(Section section, Position playerPosition, int playerExperience) {}
    public static record Position(int row, int column) {
        public static Map<Direction, Function<Position, Position>> directionPositions = Map.of(
            Direction.UP, (p) -> new Position(p.row + 1, p.column),
            Direction.DOWN, (p) -> new Position(p.row - 1, p.column),
            Direction.RIGHT, (p) -> new Position(p.row, p.column + 1),
            Direction.LEFT, (p) -> new Position(p.row, p.column - 1)
        );

        public static Map<Diagonal, Function<Position, Position>> diagonalPositions = Map.of(
            Diagonal.UPPERRIGHT, (p) -> new Position(p.row + 1, p.column + 1),
            Diagonal.LOWERLEFT, (p) -> new Position(p.row - 1, p.column - 1),
            Diagonal.UPPERLEFT, (p) -> new Position(p.row + 1, p.column - 1),
            Diagonal.LOWERRIGHT, (p) -> new Position(p.row - 1, p.column + 1)
        );

        public static Map<Direction, BiPredicate<Position, Position>> inlineConditions = Map.of(
            Direction.UP, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
            Direction.DOWN, (p1, p2) -> p1.row == p2.row && p1.column != p2.column,
            Direction.LEFT, (p1, p2) -> p1.column == p2.column && p1.row != p2.row,
            Direction.RIGHT, (p1, p2) -> p1.column == p2.column && p1.row != p2.row
        );

        public static Map<Position,Direction> vectorToDirectionMap = Map.of(
            new Position(-1, -1), Direction.DOWN,
            new Position(1, -1), Direction.DOWN,
            new Position(-1, 1), Direction.UP,
            new Position(1, 1), Direction.UP,
            
            new Position(0, 1), Direction.UP,    
            new Position(-1,0),   Direction.LEFT,  
            new Position(0,-1),   Direction.DOWN,  
            new Position(1,0),  Direction.RIGHT
        );

        public Position moveTowards(final Direction direction) {
            return Position.directionPositions.get(direction).apply(this);
        }

        public Integer distanceFrom(final Position position){
            return Double.valueOf(
                Math.sqrt(Math.pow(this.row - position.row,2) + 
                Math.pow(this.column - position.column,2)))
                .intValue();
        }

        public boolean isInlineWith(final Position position, final Direction direction) {
            return Position.inlineConditions.get(direction).test(this, position);
        }

        public Position translate(final Position position){
            return new Position(this.row + position.row, this.column + position.column);
        }

        public Position vectorialDirection(final Position position){
            var deltaRow = position.row - this.row;
            var deltaColumn = position.column - this.column;
            return new Position(deltaRow/Math.abs(deltaRow), deltaColumn/Math.abs(deltaColumn));
        }
    };

    public static record Bound(int upLimit, int leftLimit, int downLimit, int rightLimit) {

        public static List<BiPredicate<Bound, Position>> insideConditions = List.of(
            (b, p) -> p.row < b.upLimit,
            (b, p) -> p.row > b.downLimit,
            (b, p) -> p.column < b.rightLimit,
            (b, p) -> p.column > b.leftLimit
        );

        public boolean isInside(final Position position) {
            return insideConditions.stream().allMatch((condition) -> condition.test(this, position));
        }
    }

    public static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
    };
    
    public static enum Diagonal {
        UPPERLEFT,
        UPPERRIGHT,
        LOWERRIGHT,
        LOWERLEFT,
    };

    public static Direction posToDir(Position objectivePosition,Position currentPosition) {
        var direction = currentPosition.vectorialDirection(objectivePosition);
        return Position.vectorToDirectionMap.get(direction);
    }


}
