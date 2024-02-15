package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Implementation of the Island interface.
 * @see Island
 */
public final class Island extends Collidable {

    /**
     * Constructor that creates a default Island.
     * @param spawnSection the reference to the Section containing this Island
     * @param spawnPosition the position to place this Island at
     * @param spawnDirection the direction to place this Island in
     */
    protected Island(final Section spawnSection, final Position spawnPosition, final CardinalDirection spawnDirection) {
        super(spawnSection, spawnPosition, spawnDirection);
    }

    protected Island(final Island origin) {
        super(origin);
    }

    @Override
    protected Island copy() {
        return new Island(this);
    }

    /**
     * Saves the current game state.
     * This method doesn't *actually* save the game state,
     * it instead calls a method in World that does all the work.
     */
    protected void save() {
        this.getSection().getWorld().setSavedState();
    }

    /**
     * Heals the Player that interacted with the Island.
     * @param player the Player to heal.
     * This method just calls the appropriate method in Player,
     * so the healing mechanic is defined there. 
     * @see
     */
    protected void heal(final Player player) {
        player.heal();
    }

    /**
     * A collision with the Island means interacting with it.
     * @param collider the Collider that collided with this Island
     * @see Collidable
     */
    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider instanceof Player player) {
            heal(player);
            save();
        }
    }

    /**
     * Defines the Rigidness of the Island.
     * @return the Rigidness value
     * @see Collidable
     */
    @Override
    protected Rigidness getRigidness() {
        return Rigidness.HARD;
    }
}
