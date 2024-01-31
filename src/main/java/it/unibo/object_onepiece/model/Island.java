package it.unibo.object_onepiece.model;

/**
 * The healing and saving point for the player
 */
public interface Island extends Viewable, Collidable {
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
