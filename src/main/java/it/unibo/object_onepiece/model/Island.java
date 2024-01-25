package it.unibo.object_onepiece.model;

/**
 * The healing and saving point for the player
 */
public interface Island extends Entity, Interactable {
    public void save();
    public void heal(Player player);
}
