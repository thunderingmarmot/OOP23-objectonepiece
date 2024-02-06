package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models a special Collidable which works as the healing and saving point for the player.
 * @see Viewable
 * @see Collidable
 */
public interface Island extends Viewable, Collidable {
    /**
     * Creates a default Island.
     * @param spawnSection the reference to the Section containing this Island
     * @param spawnPosition the position to place this Island at
     * @return the newly created Island object
     */
    static Island getDefault(Section spawnSection, Position spawnPosition) {
        return new IslandImpl(spawnSection, spawnPosition);
    }

    /**
     * Saves the current game state.
     */
    void save();

    /**
     * Heals the Player that interacted with the Island.
     * @param player the Player
     */
    void heal(Player player);

    /**
     * @see Collidable
     */
    @Override
    default Rigidness getRigidness() {
        return Rigidness.HARD;
    }

    /**
     * @see Viewable
     */
    @Override
    default Type getViewType() {
        return Type.ISLAND;
    }
}
