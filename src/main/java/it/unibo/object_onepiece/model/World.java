package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.State;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

/**
 * Represents World of the game, with the current section and the saved ones.
 */
public interface World {
    /**
     * Grid rows.
     */
    int SECTION_ROWS = 10;
    /**
     * Grid columns.
     */
    int SECTION_COLUMNS = 10;

    /**
     * 
     * @return State last saved section.
     */
    State getSavedState();
    /**
     * Save current section.
     */
    void setSavedState();
    /**
     * 
     * @return Section current section where player resides.
     */
    Section getCurrentSection();
    /**
     * Sets World's section to a new section (used before we generate a secion).
     */
    void instantiateSection();

    Event<Argument<Section>> getSectionInstantiatedEvent();
}
