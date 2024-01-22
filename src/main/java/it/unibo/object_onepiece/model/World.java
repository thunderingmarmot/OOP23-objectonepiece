package it.unibo.object_onepiece.model;

// Interface that models the factory of Sections while also keeping track
// of the Section where the player saved the game
public interface World {
    public Section getCurrentSection();
    public Section getSavedSection();

    public Section generateSection();
}
