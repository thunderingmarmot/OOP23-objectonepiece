package it.unibo.object_onepiece.model;

/**
 * The barrels are collectable entities for the user
 */
public interface Barrel extends Entity, Interactable<Player> {
    public void take(Player player);
}
