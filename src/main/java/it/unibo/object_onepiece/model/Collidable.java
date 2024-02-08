package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models an Entity which can receive collisions by a Collider.
 * @see Collider
 */
public abstract class Collidable extends Entity {

    protected Collidable(Section s, Position p) {
        super(s, p);
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
