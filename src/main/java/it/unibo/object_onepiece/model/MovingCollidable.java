package it.unibo.object_onepiece.model;

public interface MovingCollidable extends Collidable, Movable {
    public void collideWith(Collidable collidable);
}
