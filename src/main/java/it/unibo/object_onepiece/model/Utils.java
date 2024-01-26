package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Utils {
    public static record State(Section section, Position playerPosition, int playerHealth, int playerExperience) {}

    public static record Position(int x, int y) {
        public static Map<Direction, Function<Position, Position>> aroundPosition = Map.of(
            Direction.UP, (pos) -> new Position(pos.x() + 1, pos.y()),
            Direction.DOWN, (pos) -> new Position(pos.x() - 1, pos.y()),
            Direction.RIGHT, (pos) -> new Position(pos.x(), pos.y() + 1),
            Direction.LEFT, (pos) -> new Position(pos.x(), pos.y() - 1)
        );

        public static List<Function<Position, Position>> diagonalPosition = List.of(
            (pos) -> new Position(pos.x() + 1, pos.y() + 1),
            (pos) -> new Position(pos.x() - 1, pos.y() - 1),
            (pos) -> new Position(pos.x() + 1, pos.y() - 1),
            (pos) -> new Position(pos.x() - 1, pos.y() + 1)
        );

        public Position move(Direction d) {
            return Position.aroundPosition.get(d).apply(this);
        }

        public Integer distanceFrom(final Position position){
            return Math.abs((this.x - position.x) + (this.y - position.y));
        }

        public boolean isInLine(Integer eq1, Integer eq2, Integer diff1, Integer diff2) {
            if(eq1.equals(eq2) && !diff1.equals(diff2)) {
                return true;
            }
            return false;
        }
    };

    public static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };
}
