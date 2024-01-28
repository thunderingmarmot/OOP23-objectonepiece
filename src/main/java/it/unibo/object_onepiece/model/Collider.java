package it.unibo.object_onepiece.model;

// A Collider is something that can collide with other Collidables,
// that means that it must extend Movable, as only Movable things can collide

public interface Collider extends Collidable, Movable {
    public void collideWith(Collidable collidable);
}
