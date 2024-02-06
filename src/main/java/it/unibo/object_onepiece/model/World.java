package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.State;

/**Interface that models the factory of Sections while also keeping track
* of the Section where the player saved the game.
*/
public interface World {
    /**
     * Grid rows
     */
    static final int SECTION_ROWS = 10;
    /**
     * Grid columns
     */
    static final int SECTION_COLUMNS = 10;

    /**
     * 
     * @return State last saved section
     */
    public State getSavedState();
    /**
     * Save current section
     */
    public void setSavedState();
    /**
     * 
     * @return Section current section where player resides.
     */
    public Section getCurrentSection();
    /**
     * Sets World's section to a new section (used before we generate a secion).
     */
    public void instantiateSection();
}
