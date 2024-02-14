package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models a special Collidable that can also initiate a collision.
 * @see Collidable
 */
public abstract class Collider extends Collidable {

    /**
     * Same constructor as Collidable.
     * @param origin the Collider to copy from
     * @see Collidable
     */
    protected Collider(final Collider origin) {
        super(origin);
    }

    /**
     * Same constructor as Collidable.
     * @param spawnSection the Section this Collider is in
     * @param spawnPosition the Position this Collider is at
     * @param spawnDirection the Direction this Collider is in
     * @see Collidable
     */
    protected Collider(final Section spawnSection, final Position spawnPosition, final CardinalDirection spawnDirection) {
        super(spawnSection, spawnPosition, spawnDirection);
    }

    /**
     * Defines the behaviour when this Collider collides with a Collidable.
     * @param collidable the Collidable this Collider collided with
     * @see Collidable
     */
    protected abstract void collideWith(Collidable collidable);
}
