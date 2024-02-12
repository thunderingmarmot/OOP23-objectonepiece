package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Implementation of the Island interface.
 * @see Island
 */
public final class Island extends Collidable {

    /**
     * Creates a default Island.
     * @param spawnSection the reference to the Section containing this Island
     * @param spawnPosition the position to place this Island at
     * @return the newly created Island object
     */
    protected static Island getDefault(final Section spawnSection, final Position spawnPosition, final CardinalDirection spawnDirection) {
        return new Island(spawnSection, spawnPosition, spawnDirection);
    }

    /**
     * Constructor for IslandImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Island interface.
     * @param section the reference to the Section containing this Island 
     * @param position the position to place this Island at
     * @see Island
     */
    protected Island(final Section section, final Position position, final CardinalDirection direction) {
        super(section, position, direction);
    }

    /**
     * Saves the current game state.
     */
    protected void save() {
        this.getSection().getWorld().setSavedState();
    }

    /**
     * Heals the Player that interacted with the Island.
     * @param player the Player
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
