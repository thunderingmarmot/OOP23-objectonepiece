package it.unibo.object_onepiece.model;

public interface Collider extends Collidable, Movable {
    public void collideWith(Collidable collidable);
}
