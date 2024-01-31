package it.unibo.object_onepiece.model;

/**
 * The barrels are collectable entities for the user
 */
public interface Barrel extends Viewable, Collidable {
    public void take(Player player);
}
