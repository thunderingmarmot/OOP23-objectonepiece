package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models an Entity which can receive collisions by a Collider.
 * @see Entity
 * @see Collider
 */
public abstract class Collidable extends Entity {

    /**
     * Same constructor as Entity.
     * @param origin the Collidable to copy from
     * @see Entity
     */
    protected Collidable(final Collidable origin) {
        super(origin);
    }

    /**
     * Same constructor as Entity.
     * @param  spawnSection   the Section this Collidable is in
     * @param  spawnPosition  the Position this Collidable is at
     * @param  spawnDirection the Direction this Collidable is at
     * @see    Entity
     */
    protected Collidable(final Section spawnSection, final Position spawnPosition, final CardinalDirection spawnDirection) {
        super(spawnSection, spawnPosition, spawnDirection);
    }

    /**
     * Defines the possible values of Rigidness.
     */
    enum Rigidness {
        /**
         * Collidable gets instantly destroyed by ramming and can be passed on.
         */
        SOFT, 
        /**
         * Collidable can be passed on only after being destroyed by ramming.
         */
        MEDIUM, 
        /**
         * Collidable cannot be passed on and cannot be destroyed by ramming.
         */
        HARD, 
    }

    /**
     * Getter for the Rigidness value of this Collidable.
     * @return the Rigidness value
     */
    protected abstract Rigidness getRigidness();

    /**
     * Defines the behaviour when a Collider collides with this Collidable.
     * @param collider the Collider which collided
     */
    protected abstract void onCollisionWith(Collider collider);
}
