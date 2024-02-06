package it.unibo.object_onepiece.model;

// A Collider is something that can collide with other Collidables,
// that means that it must extend Movable, as only Movable things can collide

/**
 * Models a Collidable that is also a Movable.
 * @see Collidable
 * @see Movable
 */
public interface Collider extends Collidable, Movable {
    /**
     * Defines the behaviour when this Collider collides with a Collidable.
     * @param collidable the Collidable this Collider collided with
     */
    void collideWith(Collidable collidable);
}
