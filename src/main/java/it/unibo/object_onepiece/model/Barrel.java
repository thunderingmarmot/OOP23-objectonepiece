package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models a special Collidable that gets taken when collided with.
 * @see Viewable
 * @see Collidable
 */
public interface Barrel extends Viewable, Collidable {
    /**
     * Creates a default Barrel.
     * @param spawnSection the reference to the Section containing this Barrel
     * @param spawnPosition the position to place this Barrel at
     * @return the newly created Barrel object
     */
    static Barrel getDefault(Section spawnSection, Position spawnPosition, int experienceGiven) {
        return new BarrelImpl(spawnSection, spawnPosition, experienceGiven);
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

    /**
     * @see Viewable
     */
    @Override
    default Type getViewType() {
        return Type.BARREL;
    }
}
