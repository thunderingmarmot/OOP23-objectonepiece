package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.State;

/**Interface that models the factory of Sections while also keeping track
* of the Section where the player saved the game 
*/
public interface World {
    public State getSavedState();
    public void setSavedState();

    public Section getCurrentSection();
    public void generateSection();
}
