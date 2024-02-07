package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Implementation of the Island interface.
 * @see Island
 */
public class IslandImpl extends Entity implements Island {

    /**
     * Constructor for IslandImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Island interface.
     * @param section the reference to the Section containing this Island 
     * @param position the position to place this Island at
     * @see Island
     */
    protected IslandImpl(final Section section, final Position position) {
        super(section, position);
    }

    /**
     * Saves the current game state.
     */
    @Override
    public void save() {
        this.getSection().getWorld().setSavedState();
    }

    /**
     * Heals the Player that interacted with the Island.
     * @param player the Player
     */
    @Override
    public void heal(final Player player) {
        player.getWeapon().setHealth(player.getWeapon().getMaxHealth());
        player.getSail().setHealth(player.getSail().getMaxHealth());
        player.getBow().setHealth(player.getBow().getMaxHealth());
    }

    /**
     * A collision with the Island means interacting with it.
     * @param collider the Collider that collided with this Island
     * @see Collidable
     */
    @Override
    public void onCollisionWith(final Collider collider) {
        if (collider instanceof Player player) {
            heal(player);
            save();
        }
    }
}
