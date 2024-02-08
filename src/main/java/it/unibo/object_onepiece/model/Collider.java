package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models a special Collidable that can also initiate a collision.
 * @see Collidable
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
