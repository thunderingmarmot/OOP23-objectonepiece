package it.unibo.object_onepiece.model;

public final class Utils {
    public static record Position(int x, int y) {
        public Position moveUP() {
            return new Position(x, y + 1);
        }

        public Position moveDOWN() {
            return new Position(x, y - 1);
        }

        public Position moveLEFT() {
            return new Position(x - 1, y);
        }

        public Position moveRIGHT() {
            return new Position(x + 1, y);
        }
    };

    public static enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };
}
