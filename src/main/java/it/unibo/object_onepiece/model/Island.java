package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The healing and saving point for the player
 */
public interface Island extends Viewable, Collidable {
    public static Island getDefault(Section spawnSection, Position spawnPosition) {
        return new IslandImpl(spawnSection, spawnPosition);
    }

    public void save();
    public void heal(Player player);

    @Override
    public default Rigidness getRigidness() {
        return Rigidness.HARD;
    }

    @Override
    public default Type getViewType() {
        return Type.ISLAND;
    }
}
