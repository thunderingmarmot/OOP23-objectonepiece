package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models a special Collidable that gets taken when collided with.
 * @see Viewable
 * @see Collidable
 */
public interface Barrel extends Collidable {
    public static final int DEFAULT_EXPERIENCE_GIVEN = 50;

    /**
     * Creates a default Barrel.
     * @param spawnSection the reference to the Section containing this Barrel
     * @param spawnPosition the position to place this Barrel at
     * @param experienceGiven the experience value this Barrel gives the Player when taken
     * @return the newly created Barrel object
     */
    static Barrel getDefault(Section spawnSection, Position spawnPosition) {
        return new BarrelImpl(spawnSection, spawnPosition, DEFAULT_EXPERIENCE_GIVEN);
    }

    /**
     * Defines the behaviour of getting taken by a Player.
     * @param player the Player that is taking this Barrel
     * @see Player
     */
    void take(Player player);

    /**
     * @see Collidable
     */
    @Override
    default Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
