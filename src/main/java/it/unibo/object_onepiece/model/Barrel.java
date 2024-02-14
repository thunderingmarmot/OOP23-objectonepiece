package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Implementation of the Barrel interface.
 * @see Barrel
 */
public final class Barrel extends Collidable {

    private static final int DEFAULT_EXPERIENCE_GIVEN = 50;

    private final int experienceGiven;

    private Barrel(final Section section, final Position position, final CardinalDirection direction, final int experienceGiven) {
        super(section, position, direction);
        this.experienceGiven = experienceGiven;
    }

    /**
     * Constructor that creates a default Barrel.
     * @param spawnSection the reference to the Section containing this Barrel
     * @param spawnPosition the position to place this Barrel at
     * @param spawnDirection the direction to place this Barrel in
     */
    protected Barrel(final Section spawnSection, final Position spawnPosition, final CardinalDirection spawnDirection) {
        this(spawnSection, spawnPosition, spawnDirection, DEFAULT_EXPERIENCE_GIVEN);
    }

    /**
     * Defines the behaviour of getting taken by a Player.
     * @param player the Player that is taking this Barrel
     * @see Player
     */
    protected void onPickup(final Player player) {
        player.addExperience(experienceGiven);
    }

    /**
     * A collision with a Barrel means destroying it and picking it up.
     * @param collider the Collider that collided with the Barrel
     * @see Collidable
     */
    @Override
    protected void onCollisionWith(final Collider collider) {
        if (collider instanceof Player player) {
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
