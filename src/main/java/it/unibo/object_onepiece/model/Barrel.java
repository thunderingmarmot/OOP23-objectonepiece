package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * A Barrel, a Collidable that when collided with can be picked up.
 * @see Barrel
 */
public final class Barrel extends Collidable {

    private static final int DEFAULT_EXPERIENCE_GIVEN = 50;

    private final int experienceGiven;

    /**
     * Constructor that creates a default Barrel.
     * @param spawnSection the reference to the Section containing this Barrel
     * @param spawnPosition the position to place this Barrel at
     * @param spawnDirection the direction to place this Barrel in
     */
    protected Barrel(final Section spawnSection, final Position spawnPosition, final CardinalDirection spawnDirection) {
        super(spawnSection, spawnPosition, spawnDirection);
        this.experienceGiven = DEFAULT_EXPERIENCE_GIVEN;
    }

    /**
     * Copy constructor that creates a copy of a Barrel.
     * @param origin the Barrel to copy from
     */
    protected Barrel(final Barrel origin) {
        super(origin);
        this.experienceGiven = origin.experienceGiven;
    }

    @Override
    protected Barrel copy() {
        return new Barrel(this);
    }

    /**
     * Getter for the experience value given to the Player on pickup.
     * @return the experience value
     */
    protected int getExperienceGiven() {
        return this.experienceGiven;
    }

    /**
     * Defines the behaviour of getting taken by a Player.
     * @param player the Player that is taking this Barrel
     * @see PlayerImpl
     */
    protected void onPickup(final PlayerImpl player) {
        player.addExperience(experienceGiven);
    }

    /**
     * A collision with a Barrel means destroying it and picking it up.
     * @param collider the Collider that collided with the Barrel
     * @see Collidable
     */
    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider instanceof PlayerImpl player) {
            onPickup(player);
        }
        this.remove();
    }

    /**
     * Defines the Rigidness of the Island.
     * @return the Rigidness value
     * @see Collidable
     */
    @Override
    protected Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
