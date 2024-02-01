package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.State;

/**Interface that models the factory of Sections while also keeping track
* of the Section where the player saved the game 
*/
public interface World {
    static final int SECTION_ROWS = 10;
    static final int SECTION_COLUMNS = 10;

    public State getSavedState();
    public void setSavedState();

    public Section getCurrentSection();
    public void generateSection();
}
