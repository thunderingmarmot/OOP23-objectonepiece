package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

// A Collider is something that can collide with other Collidables,
// that means that it must extend Movable, as only Movable things can collide

/**
 * Models a Collidable that is also a Movable.
 * @see Collidable
 * @see Movable
 */
public abstract class Collider extends Collidable {

    protected Collider(Section s, Position p) {
        super(s, p);
    }

    /**
     * Defines the behaviour when this Collider collides with a Collidable.
     * @param collidable the Collidable this Collider collided with
     */
    protected abstract void collideWith(Collidable collidable);
}
