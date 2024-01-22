package it.unibo.object_onepiece.model;

public interface Ship extends Entity {
    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    };

    public void move(Direction direction);
    public void rotate(Direction direction);

    public boolean shoot(Position position);
    public void takeDamage(int damage);

    public int getHealth();
    public Direction getDirection();
}